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
struct termios old_port_attrib,new_port_attrib;


/**@brief This function initializes the communication port.

           Function: initcom

           Purpose:  Opens and Initializes the serial port.

           Returns:  None
*/

void initcom()
{

   fport = open(cport,O_RDWR | O_NOCTTY | O_NONBLOCK);
   if (fport <0)
   {
       	sprintf(msg_to_log,"Error Opening COMPORT %s:%s",cport,strerror(errno));
       	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
       	printf("\nError Opening COMPORT %s:%s,  Exiting...\n",cport,strerror(errno));
       	sprintf(msg_to_log,"DATA ACCESS MODULE TERMINATED");
        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
       	exit(EXIT_FAILURE);
   }
   else
   {

       	sprintf(msg_to_log,"COMPORT Initialised : %s",cport);
       	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);

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

/**@brief  This function resets the communication port to its initial state.

           Function: closecom

           Purpose:  Closes and resets the serial port.

           Returns:  None
*/

void closecom(void)
{
        if(emulator_mode)
        {
            return;
        }

        tcflush(fport, TCIOFLUSH);
        tcsetattr(fport,TCSANOW,&old_port_attrib);
    	close(fport);
        pthread_mutex_destroy(&IOMutex);
}

/**@brief This function reads data from the communication port.

           Function: readcom

           Purpose:  Reads data from the serial port in a seperate thread

           Returns:  None
*/

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
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
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
                    log_to_file(msg_to_log,rcount,DEBUG_LEVEL_3);

                }
                else
                {
                        sprintf(msg_to_log,"Error Reading COMPORT %s",strerror(errno));
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                }

            }
        }
        return FALSE;
}

/**@brief This function prints the given input number in binary form.

           Function: ShowBits

           Purpose:  Takes  an integer and prints the bitwise representation
                     in 1's and 0s upto the number of bytes required

           Returns:  None
*/

void ShowBits(int y,int no_of_bytes)
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


/**@brief  This function writes data to the communication port.

           Function: writecom

           Purpose:  Writes data to the serial port.

           Returns:  None
*/

void  writecom(BYTE * msg, int ncount)
{

int bytes_write=0,bytes_write_nw=0,i=0,wcount=0,count=0;


		memset((void *)write_buf,0x0,WRITE_BUF_SIZE);
		memcpy((void *)write_buf,msg,ncount);
		write_buf[ncount]=HiByte(Add_CRC(msg,ncount));
		write_buf[ncount+1]=LoByte(Add_CRC(msg,ncount));

		if (gl_count>2)
		{
			if(Check_CRC(gl_buf,gl_count))
			{
			    vlist[gl_buf[0]-1].active=TRUE;
			    vlist[gl_buf[0]-1].chk_count=0;
                vlist[gl_buf[0]-1].reset_chk_count=0;
			    switch_params(gl_buf,gl_count);
			    //sprintf(msg_to_log,"CRC VALID FOR PREVIOUS READ PACKET");
                //log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
			}
			else
			{
			    sprintf(msg_to_log,"CRC FAILED FOR PREVIOUS READ PACKET");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_1);
			}
			memset(gl_buf,0x0,BUF_SIZE);
			gl_count=0;
		}

		count=ncount+2;
		reverse_b((BYTE *)&scondition,(BYTE *)&msg[2],2);
        pthread_mutex_lock(&IOMutex);
        if(!emulator_mode)
        {
            bytes_write = write (fport, write_buf, count);
        }
        else
        {
            for(i=0;i<no_of_clients;i++)
            {
                if(dclients[i].sockfd != -1)
                {
                    bytes_write_nw=0;
                    if((bytes_write_nw=write(dclients[i].sockfd,write_buf,count)) <= 0)
                    {
                                if(errno == EAGAIN || errno == EWOULDBLOCK || errno == EINTR)
                                {
                                     continue;
                                }
                                sprintf(msg_to_log,"Client %s is Disconnected : %s",dclients[i].name,strerror(errno));
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                                FD_CLR(dclients[i].sockfd,&socket_set);
                                close(dclients[i].sockfd);
                                memset(&dclients[i].client_addr,0x0,sizeof(client_addr));
                                memset(&dclients[i].inbuf,0x0,MAXSIZE);
                                memset(&dclients[i].name,0x0,MAXSIZE);
                                dclients[i].sockfd=-1;
                                dclients[i].send=FALSE;

                    }
                }
            }
        }

		pthread_mutex_unlock(&IOMutex);

		if(bytes_write>0)
		{
			wcount=sprintf(msg_to_log,"WRITTEN DATA ");
	  		for(i=0;i<bytes_write;i++)
			{
				wcount+=sprintf(&msg_to_log[wcount]," %02X",write_buf[i]);
			}
			wcount=wcount+sprintf(&msg_to_log[wcount]," TO SERIAL PORT %s",cport);
			log_to_file(msg_to_log,wcount,DEBUG_LEVEL_3);

		}
		else
		{
		    if(!emulator_mode)
		    {
       		 	sprintf(msg_to_log,"Error Writing to Serial Port : %s",strerror(errno));
       		 	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
		    }
		}
}

