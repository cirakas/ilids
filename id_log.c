/*
 * id_log.c
 *
 * This is the source file where the idriver logging
 * functions are implemented.All the error messages
 * as well as the communications happening through
 * the port will be logged to a file.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

#include "id_common.h"

#define LNSIZE 200


int lfile=-1;
char *fmtdate="idriver_log_%Y-%m-%d %n%H:%M:%S :: ";
time_t t;
struct tm *tmp;
struct stat st;
int log_mode=S_IRUSR | S_IWUSR | S_IRGRP | S_IWGRP | S_IROTH | S_IWOTH;
char outstr[LNSIZE];
char lname[LNSIZE];

pthread_mutex_t LMutex;



/**@brief  This function logs messages to the log file

           Function: log_to_file

           Purpose:  Writes the log messages to the log file.

           Returns:  None
*/


void log_to_file(char * log_msg,int log_count,int log_level)
{


                   pthread_mutex_lock(&LMutex);

                   if((log_level!=DEBUG_LEVEL_DEFAULT) && (log_level>current_log_level))
                   {
                        pthread_mutex_unlock(&LMutex);
                        return ;
                   }

                   t = time(NULL);
                   tmp = localtime(&t);
                   if (tmp == NULL)
                   {
                       perror("localtime");
                       exit(EXIT_FAILURE);
                   }

                   memset(outstr,0x0,LNSIZE);
                   if (strftime(outstr, sizeof(outstr), fmtdate, tmp) == 0)
                   {
                       fprintf(stderr, "strftime returned 0");
                       exit(EXIT_FAILURE);
                   }

                   memset(lname,0x0,LNSIZE);
                   lname[22]='\0';
                   strncpy(lname,outstr,22);

                   if((stat(lname,&st)==-1)||(lfile==-1))
                   {
                       if(lfile!=-1)
                       {
                           close(lfile);
                       }
                       lfile=open(lname,O_CREAT|O_RDWR|O_APPEND|O_NONBLOCK,log_mode);//int open(const char *pathname, int flags, mode_t mode);
                   }

                   write(lfile,&outstr[23],strlen(&outstr[23]));
                   write(lfile,log_msg,log_count);
                   pthread_mutex_unlock(&LMutex);
}
