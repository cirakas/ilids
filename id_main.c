/*! \mainpage iDriver

 iDriver is the data collection module in the iLids system.
 The module collects data from the energy meter device
 over an rs485 wired link.The values are obtained using
 polling mechanism. Multiple  devices can be connected over
 this link.The program updates the values obtained from the
 meter to a central mysql database,from where other ilids
 modules can access the data.
 *
 */

 /*
 * id_main.c
 *
 * This is the main initialization function for idriver module in ilids
 * project.Initialisation of program parameters,creation of threads,
 * and installation of signal handlers are done here.The arguments passed
 * to idriver are also processed here.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

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
extern pthread_mutex_t LMutex;

/**@brief  This is the main function of the idriver module

           Function: main

           Purpose:  main function of the idriver module.

           Returns:  int
*/

int main(int argc,char *argv[])
{
  int i=0,j=0;
  FILE * cmd;
  char ret[16];

    for(i=strlen(argv[0]);i>0;i--)
    {
        if(argv[0][i] == '/')
        {
            break;
        }
    }


    sprintf(scommand,"ps -A | grep %s| wc -l 2>/dev/null",(char *)&argv[0][i+1]);
    if((cmd=popen((const char *)scommand,"r"))!=NULL)
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
	pthread_mutex_init(&LMutex, NULL);
   	open_log();

   	sprintf(msg_to_log,"**************************************");
   	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
   	sprintf(msg_to_log,"DATA ACCESS MODULE STARTED");
   	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
    sprintf(msg_to_log,"Entering Daemon Mode");
   	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

    //daemon(1,0);

    p_int=POLL_INTERVAL;
    rd_timeout=RTIMEOUT;
    slave_id=DEFAULT_SLID;
    cport=DEFAULT_PORT;
    current_log_level=DEBUG_LEVEL_3;
    ex_term=FALSE;
    no_of_clients=MAXCLIENTS;
    random_mode=FALSE;
    emulator_mode=FALSE;
    rand_time=5;
    rand_count=0;
    gl_count=0;
    bytes_read=0;



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

                case 'e':
                emulator_mode=TRUE;
                break;

                case 'i':
                random_mode=TRUE;
                rand_time=atoi(&(argv[i][j+3]));
                printf("\nRandom Simulation Mode Enabled\n");
                sprintf(msg_to_log,"Random Simulation Mode Enabled : %d",rand_time);
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                break;

                case 'l':
                current_log_level=atoi(&(argv[i][j+3]));
                sprintf(msg_to_log,"Setting Log Level to %d",current_log_level);
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                break;

                default:printf("\nInvalid Arguments,Using Default Values\n");
                sprintf(msg_to_log,"Invalid Arguments,Using Default Values");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                p_int=POLL_INTERVAL;
                rd_timeout=RTIMEOUT;
                slave_id=DEFAULT_SLID;
                cport=DEFAULT_PORT;
                current_log_level=DEBUG_LEVEL_3;
                break;
            }

        }
    }


    if(argc==1)
    {
        sprintf(msg_to_log,"No arguments Supplied, Using default values");
        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
    }

    sprintf(msg_to_log,"COMPORT is %s, Poll Interval is %d ms, Read Timeout is %d ms, Slave Id is %d",cport,p_int,rd_timeout,slave_id);
    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

	initcom();

    for(i=0;i<MAXSLAVE;i++)
    {
        sdev[i].active=FALSE;
        sdev[i].status=TRUE;
    }


    intitialize_poll_packet();

	Signal_exitsignal();
	Signal_sigio_signal();

	if(!db_start())
	{
	    sprintf(msg_to_log,"Error Initializing MySQL Database,Exiting");
        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
        exit(EXIT_FAILURE);
	}
	pthread_attr_init(&TH_ATTR);
	pthread_attr_setdetachstate(&TH_ATTR, PTHREAD_CREATE_DETACHED);
	pthread_create(&th_read, &TH_ATTR, readcom,NULL);
	pthread_create(&th_nw, &TH_ATTR, nwcom,NULL);

	atexit(Handle_exithandler);



	while(!ex_term)
   	{
   	    send_pkt(POLL_PKT);
		da_wait(0,p_int*1000);

	}


	pthread_cancel(th_read);
	pthread_cancel(th_nw);
	db_close();


   	sprintf(msg_to_log,"DATA ACCESS MODULE TERMINATED");
   	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

   	exit(EXIT_SUCCESS);

}

