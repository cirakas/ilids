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
  int i=0;
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
	pthread_mutex_init(&LMutex, NULL);

   	sprintf(msg_to_log,"**************************************");
   	log_to_file(msg_to_log,strlen(msg_to_log));
   	sprintf(msg_to_log,"DATA ACCESS MODULE STARTED");
   	log_to_file(msg_to_log,strlen(msg_to_log));
    ex_term=FALSE;
    no_of_clients=MAXCLIENTS;
    random_mode=FALSE;
    emulator_mode=FALSE;
    rand_time=5;
    rand_count=0;
    gl_count=0;
    bytes_read=0;


    init_slave_params();//This needs to be called before readconf to properly use selective update of db based on config file.

   	if(!Read_Conf())
   	{
   	    sprintf(msg_to_log,"Error in Config File,Exiting");
        log_to_file(msg_to_log,strlen(msg_to_log));
   	    exit(EXIT_FAILURE);
   	}


	if(!db_start())
	{
	    sprintf(msg_to_log,"Error Initializing MySQL Database,Exiting");
        log_to_file(msg_to_log,strlen(msg_to_log));
        exit(EXIT_FAILURE);
	}

    intitialize_poll_packet();
	Signal_exitsignal();
	Signal_sigio_signal();


	pthread_attr_init(&TH_ATTR);
	pthread_attr_setdetachstate(&TH_ATTR, PTHREAD_CREATE_DETACHED);

	if(emulator_mode)//enable network emulation
	{
        pthread_create(&th_nw, &TH_ATTR, nwcom,NULL);
	}
	else
	{
	    initcom();
	    pthread_create(&th_read, &TH_ATTR, readcom,NULL);
	}

	atexit(Handle_exithandler);



    //if(daemon(1,0)==0) //NW COM not happening when enabling this mode
    //{
    //    sprintf(msg_to_log,"Entering Daemon Mode");
    //    log_to_file(msg_to_log,strlen(msg_to_log));
    //}

	while(!ex_term)
   	{
   	    send_pkt(POLL_PKT);
		da_wait(0,p_int*1000);
	}


	if(emulator_mode)
	{
	    pthread_cancel(th_nw);
	}
	else
	{
	    pthread_cancel(th_read);
	}
	db_close();


   	sprintf(msg_to_log,"DATA ACCESS MODULE TERMINATED");
   	log_to_file(msg_to_log,strlen(msg_to_log));

   	exit(EXIT_SUCCESS);

}

