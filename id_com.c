/*
 * id_com.c
 *
 * This is the source file where the communication
 * port is initialized and setup.It also contains
 * functions for reading and writing to the port.
 * The port reading is implemented as a seperate thread.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */



#include "id_common.h"


void initcom();
void *readcom();
void  writecom(BYTE * msg, int count);
void closecom(void);

int fport=-1;

static pthread_mutex_t IOMutex;

volatile BYTE read_buf[READ_BUF_SIZE];
BYTE write_buf[WRITE_BUF_SIZE];
BYTE gl_buf[BUF_SIZE];
int gl_count=0;
int bytes_read=0;
struct termios old_port_attrib,new_port_attrib;


void ShowBits(BYTE y,int no_of_bytes)
{
	int i=0;
	unsigned int j=0x1;
	int nsize=0;


	if(no_of_bytes>4)
	{
		no_of_bytes=4;
	}

	nsize=no_of_bytes*8;
	printf("\n");
	for(i=0;i<nsize;i++)
	{
		if(y & j<<(nsize-(i+1)))
		{
			printf("1");
		}
		else
		{
			printf("0");
		}
		if((i%4)==3)
		{
			printf(" ");
		}
	}
}

void initcom()
{
   fport = open(cport,O_RDWR | O_NOCTTY | O_NONBLOCK);
   if (fport <0)
   {
       	sprintf(msg_to_log,"Error Opening COMPORT %s:%s",cport,strerror(errno));
       	log_to_file(msg_to_log,strlen(msg_to_log));
       	printf("\nError Opening COMPORT %s:%s,  Exiting...\n",cport,strerror(errno));
       	sprintf(msg_to_log,"DATA ACCESS MODULE TERMINATED");
        log_to_file(msg_to_log,strlen(msg_to_log));
       	exit(EXIT_FAILURE);
   }
   else
   {

       	sprintf(msg_to_log,"COMPORT Initialised : %s",cport);
       	log_to_file(msg_to_log,strlen(msg_to_log));

       	fcntl(fport, F_SETFL, 0);
       	fcntl(fport, F_SETOWN, getpid());
       	fcntl(fport, O_NONBLOCK,0);
       	tcgetattr(fport,&old_port_attrib);
       	bzero(&new_port_attrib, sizeof(new_port_attrib));
       	new_port_attrib.c_cflag = BAUDRATE |  CS8 | CLOCAL | CREAD;

       	new_port_attrib.c_oflag = 0;
        tcflush(fport, TCIOFLUSH);
       	tcsetattr(fport,TCSANOW,&new_port_attrib);

        pthread_mutex_init(&IOMutex, NULL);
        atexit(closecom);

    }

}

void closecom(void)
{

        tcflush(fport, TCIOFLUSH);
        tcsetattr(fport,TCSANOW,&old_port_attrib);
    	close(fport);
        pthread_mutex_destroy(&IOMutex);
}

void * readcom()
{
	int i,rcount=0,n=0;
	fd_set input,tmp_set;
	struct timeval s_timeout;



	pthread_setcancelstate (PTHREAD_CANCEL_ENABLE,NULL);
	pthread_setcanceltype (PTHREAD_CANCEL_ASYNCHRONOUS,NULL);

    FD_ZERO(&tmp_set);
	FD_SET(fport, &tmp_set);
	s_timeout.tv_sec = 0;
	s_timeout.tv_usec = rd_timeout * 1000;


	while(!ex_term)
	{
            s_timeout.tv_sec = 0;
            s_timeout.tv_usec = rd_timeout * 1000;
            input=tmp_set;
        	if((n = select(fport + 1, &input, NULL, NULL, &s_timeout))==-1)
        	{
                if(errno != EINTR)
                {
                    sprintf(msg_to_log,"Read Select Error %s",strerror(errno));
                    log_to_file(msg_to_log,strlen(msg_to_log));
                }
                continue;
        	}

        	if(n==0)
        	{
                continue;
        	}


        	if (FD_ISSET(fport,&input))
            {
                memset((void *)read_buf,0x0,READ_BUF_SIZE);
                pthread_mutex_lock(&IOMutex);
                bytes_read = read(fport,(void *)read_buf,READ_BUF_SIZE);
                memcpy(&gl_buf[gl_count],(void *)read_buf,bytes_read);
                gl_count+=bytes_read;
                pthread_mutex_unlock(&IOMutex);

                if(bytes_read > 0)
                {
                    rcount=sprintf(msg_to_log,"READ DATA ");
                    for(i=0;i<bytes_read;i++)
                    {
                        rcount += sprintf(&msg_to_log[rcount]," %02X",read_buf[i]);
                    }
                    rcount += sprintf(&msg_to_log[rcount]," FROM COMPORT %s",cport);
                    log_to_file(msg_to_log,rcount);

                }
                else
                {
                        sprintf(msg_to_log,"Error Reading COMPORT %s",strerror(errno));
                        log_to_file(msg_to_log,strlen(msg_to_log));
                }

            }
        }
        return FALSE;
}

void  writecom(BYTE * msg, int ncount)
{

int bytes_write=0,i=0,wcount=0,count=0;


		memset((void *)write_buf,0x0,WRITE_BUF_SIZE);
		memcpy((void *)write_buf,msg,ncount);
		write_buf[ncount]=HiByte(Add_CRC(msg,ncount));
		write_buf[ncount+1]=LoByte(Add_CRC(msg,ncount));

		if (gl_count>2)
		{
			if(Check_CRC(gl_buf,gl_count))
			{
			    sdev[gl_buf[0]-1].active=TRUE;
			    switch_params(gl_buf,gl_count);
			}
			else
			{
			    sprintf(msg_to_log,"CRC FAILED FOR PREVIOUS READ PACKET");
                log_to_file(msg_to_log,strlen(msg_to_log));
			}
			memset(gl_buf,0x0,BUF_SIZE);
			gl_count=0;
		}

		count=ncount+2;
		reverse_b((BYTE *)&scondition,(BYTE *)&msg[2],2);
        pthread_mutex_lock(&IOMutex);
        bytes_write = write (fport, write_buf, count);
		pthread_mutex_unlock(&IOMutex);

		if(bytes_write>0)
		{
			wcount=sprintf(msg_to_log,"WRITTEN DATA ");
	  		for(i=0;i<bytes_write;i++)
			{
				wcount+=sprintf(&msg_to_log[wcount]," %02X",write_buf[i]);
			}
			wcount=wcount+sprintf(&msg_to_log[wcount]," TO SERIAL PORT %s",cport);
			log_to_file(msg_to_log,wcount);

		}
		else
		{
       		 	sprintf(msg_to_log,"Error Writing to Serial Port : %s",strerror(errno));
       		 	log_to_file(msg_to_log,strlen(msg_to_log));
		}
}

