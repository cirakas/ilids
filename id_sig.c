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

/**@brief  This function installs the SIGINT signal handler

           Function: Signal_exitsignal

           Purpose:  This function will install the handler for SIGINT signal.This
                     signal is  generated when Cntrl^C is pressed.The signal is
                     caught and the handler function is called for exiting the
                     module.

           Returns:  None
*/


void Signal_exitsignal()
{
struct sigaction sig_exit;

   sig_exit.sa_handler =(void *)Handle_exithandler;
   sigemptyset(&sig_exit.sa_mask);
   sig_exit.sa_flags = SA_RESTART;
   sigaction(SIGINT,&sig_exit,0);
}

/**@brief  This function installs the SIGIO signal handler

           Function: Signal_sigio_signal

           Purpose:  This function will install the handler for SIGIO signal.This
                     signal is  generated when some error happens in serial
                     communication.The signal is caught and handled by this
                     function.

           Returns:  None
*/

void Signal_sigio_signal()
{
struct sigaction sig_io;

   sig_io.sa_handler =(void *)Handle_sigiohandler;
   sigemptyset(&sig_io.sa_mask);
   sig_io.sa_flags = SA_RESTART;
   sigaction(SIGIO,&sig_io,0);
}

/**@brief  This function is the handler for SIGINT signals.

           Function: Handle_exithandler

           Purpose:  This function is the handler for SIGINT(Cntrl^C) signal.

           Returns:  None
*/


void Handle_exithandler()
{
    ex_term=TRUE;
}

/**@brief  This function is the handler for SIGIO signals.

           Function: Handle_sigiohandler

           Purpose:  This function is the handler for SIGIO signal.

           Returns:  None
*/


void Handle_sigiohandler()
{
   	sprintf(msg_to_log,"SIGIO Signal Caught");
   	log_to_file(msg_to_log,strlen(msg_to_log));
}

/**@brief  This function implements microsecond precision sleep

           Function: da_wait

           Purpose:  This function implements microsecond level sleep
                     function using select system call.

           Returns:  None

-----------------------------------------------------------------------------------*/



void da_wait(int nsecs,int nmsecs)
{
	da_timeout.tv_sec=nsecs;
	da_timeout.tv_usec=nmsecs;
	select(0,NULL,NULL,NULL,&da_timeout);
}

