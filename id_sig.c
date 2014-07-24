/*
 * id_sig.c
 *
 * This is the source file where the signal handling functions
 * in idriver module are implemented.The sleep function
 * and Cntrl^C handling functions are also implemented here.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */



#include "id_common.h"


void Signal_exitsignal();
void Signal_sigio_signal();
void Handle_exithandler();
void Handle_sigiohandler();

void da_wait(int nsecs,int nmsecs);

struct timeval da_timeout;

extern pthread_t th_read,th_nw;

void Signal_exitsignal()
{
struct sigaction sig_exit;

   sig_exit.sa_handler =(void *)Handle_exithandler;
   sigemptyset(&sig_exit.sa_mask);
   sig_exit.sa_flags = SA_RESTART;
   sigaction(SIGINT,&sig_exit,0);
}

void Signal_sigio_signal()
{
struct sigaction sig_io;

   sig_io.sa_handler =(void *)Handle_sigiohandler;
   sigemptyset(&sig_io.sa_mask);
   sig_io.sa_flags = SA_RESTART;
   sigaction(SIGIO,&sig_io,0);
}

void Handle_exithandler()
{
    ex_term=TRUE;
}

void Handle_sigiohandler()
{
   	sprintf(msg_to_log,"SIGIO Signal Caught");
   	log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
}


void da_wait(int nsecs,int nmsecs)
{
	da_timeout.tv_sec=nsecs;
	da_timeout.tv_usec=nmsecs;
	select(0,NULL,NULL,NULL,&da_timeout);
}

