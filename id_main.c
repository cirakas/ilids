#include "id_common.h"


extern void * readcom();
extern void * nwcom();
extern void initcom();
extern void Signal_exitsignal();
extern void Signal_sigio_signal();

extern int log_mode;
extern mode_t umask_access_mode;

pthread_attr_t TH_ATTR;
pthread_t th_read;
pthread_t th_nw;

int main(int argc,char *argv[])
{
  int i=0,j=0;
  char verify[64];
  FILE * cmd;
  char ret[16];

    for(i=strlen(argv[0]);i>0;i--)
    {
        if(argv[0][i] == '/')
        {
            break;
        }
    }


    sprintf(verify,"ps -A | grep %s| wc -l 2>/dev/null",(char *)&argv[0][i+1]);
    if((cmd=popen((const char *)verify,"r"))!=NULL)
    {

        if(fgets(ret,16,cmd)!=NULL)
        {
            ret[strlen(ret)-1]=0x0;
            if(atoi(ret)>=2)
            {
                printf("%s already Running\n",(char *)&argv[0][i+1]);
                _exit(EXIT_SUCCESS);
            }
        }
        pclose(cmd);
    }

    log_mode = S_IREAD | S_IWRITE | S_IRGRP | S_IROTH;
	umask_access_mode = S_IREAD | S_IWRITE | S_IRGRP | S_IROTH;
	umask(umask_access_mode);
   	open_log();

   	sprintf(msg_to_log,"**************************************");
   	log_to_file(msg_to_log,strlen(msg_to_log));
   	sprintf(msg_to_log,"DATA ACCESS MODULE STARTED");
   	log_to_file(msg_to_log,strlen(msg_to_log));

    sprintf(msg_to_log,"Entering Daemon Mode");
   	log_to_file(msg_to_log,strlen(msg_to_log));

    daemon(1,0);//This will cause the program to run as a system daemon without any terminal.Output should be checked through log file.

    p_int=POLL_INTERVAL;
    rd_timeout=RTIMEOUT;
    slave_id=DEFAULT_SLID;
    cport=DEFAULT_PORT;
    ex_term=FALSE;
    random_mode=FALSE;
    rand_time=5;
    rand_count=0;


    for(i=1;i<argc;i++)
    {
        if(argv[i][j]=='-')
        {
            switch(argv[i][j+1])
            {
                case 'p':
                p_int=atoi(&(argv[i][j+3]));
                break;

                case 'r':
                rd_timeout=atoi(&(argv[i][j+3]));
                break;

                case 's':
                slave_id=atoi(&(argv[i][j+3]));
                break;

                case 'c':
                cport=&(argv[i][j+3]);
                break;

                case 'i':
                random_mode=TRUE;
                rand_time=atoi(&(argv[i][j+3]));
                printf("\nRandom Simulation Mode Enabled\n");
                sprintf(msg_to_log,"Random Simulation Mode Enabled : %d",rand_time);
                log_to_file(msg_to_log,strlen(msg_to_log));

                break;

                default:printf("\nInvalid Arguments,Using Default Values\n");
                sprintf(msg_to_log,"Invalid Arguments,Using Default Values");
                log_to_file(msg_to_log,strlen(msg_to_log));
                p_int=POLL_INTERVAL;
                rd_timeout=RTIMEOUT;
                slave_id=DEFAULT_SLID;
                cport=DEFAULT_PORT;
                break;
            }

        }
    }


    if(argc==1)
    {
        //printf("\nNo arguments Supplied, Using default values\n");
        sprintf(msg_to_log,"No arguments Supplied, Using default values");
        log_to_file(msg_to_log,strlen(msg_to_log));
    }
    //printf("\nCOMPORT is %s, Poll Interval is %d ms, Read Timeout is %d ms, Slave Id is %d\n",cport,p_int,rd_timeout,slave_id);
    sprintf(msg_to_log,"COMPORT is %s, Poll Interval is %d ms, Read Timeout is %d ms, Slave Id is %d",cport,p_int,rd_timeout,slave_id);
    log_to_file(msg_to_log,strlen(msg_to_log));

	initcom();

	sdev[slave_id-1].dev_id=TRUE;//temporary way of initialising slave device.

    intitialize_poll_packet();

	Signal_exitsignal(); //for catching Cntrl-C
	Signal_sigio_signal();//for catching sigio signals

	if(!db_start())
	{
	    //printf("\nError Initializing MySQL Database,Exiting\n");
	    sprintf(msg_to_log,"Error Initializing MySQL Database,Exiting");
        log_to_file(msg_to_log,strlen(msg_to_log));
        exit(EXIT_FAILURE);
	}
	pthread_attr_init(&TH_ATTR);
	pthread_attr_setdetachstate(&TH_ATTR, PTHREAD_CREATE_DETACHED);
	pthread_create(&th_read, &TH_ATTR, readcom,NULL);
	pthread_create(&th_nw, &TH_ATTR, nwcom,NULL);

	atexit(Handle_exithandler);

   	printf("\nDATA ACCESS MODULE STARTED\n");

	while(!ex_term)
   	{
   	    send_pkt(POLL_PKT);
		da_wait(0,p_int*1000);

	}


	pthread_cancel(th_read);
	pthread_cancel(th_nw);
	db_close();


   	sprintf(msg_to_log,"DATA ACCESS MODULE TERMINATED");
   	log_to_file(msg_to_log,strlen(msg_to_log));

   	exit(EXIT_SUCCESS);

}

