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

int new_log_file;
int log_mode;
int flog;
mode_t umask_access_mode;
char msg_to_log[MSG_SIZE];
pthread_mutex_t LMutex;

void open_log();
void log_to_file(char * log_msg,int log_count,int log_level);

/**@brief  This function opens/creates the log file for logging

           Function: open_log

           Purpose:  Opens the log file for logging.

           Returns:  None
*/
void open_log()
{
char log_name[26];
char append_date[11];
FILE  * current_date;
int mod_ret=0;


        current_date = popen("date -I","r");
        fgets(append_date,11,current_date);
        pclose(current_date);
        if(strlen(append_date) < 3)
        {
                current_date = popen("date -I","r");
                fgets(append_date,11,current_date);
                pclose(current_date);
        }
        else
        {
                log_mode = S_IREAD | S_IWRITE | S_IRGRP | S_IROTH;
                umask_access_mode = S_IREAD | S_IWRITE | S_IRGRP | S_IROTH;
                umask(umask_access_mode);

                sprintf(log_name,"idriver_log_%s",append_date);
                flog=open(log_name,O_CREAT | O_RDWR | O_APPEND);
                if(flog < 0)
                {
                       printf("\nError creating log file: %s",strerror(errno));
                }
                else
                {
                        mod_ret = fchmod(flog,log_mode);
                        if(mod_ret < 0)
                        {
                               printf("\nerror setting mode for logfile %s\n",strerror(errno));
                        }

                        new_log_file = 1;
                }

        }


}

/**@brief  This function logs messages to the log file

           Function: log_to_file

           Purpose:  Writes the log messages to the log file.

           Returns:  None
*/

void log_to_file(char * log_msg,int log_count,int log_level)
{
time_t curtime;
char * formatted_time;
char curr_hour[6],mid_night[6]="00:00";
char * format = "::";

        pthread_mutex_lock(&LMutex);

        if((log_level!=DEBUG_LEVEL_DEFAULT) && (log_level>current_log_level))
        {
            pthread_mutex_unlock(&LMutex);
            return ;
        }

        curtime = time(NULL);
        formatted_time = ctime((const time_t *)&curtime);
        memcpy(curr_hour,&formatted_time[11],5);
        curr_hour[5]=0;

        write(flog,"\n",1);
        write(flog,formatted_time,strlen(formatted_time) -1);
        write(flog,format,strlen(format));
        write(flog,log_msg,log_count);


        if((strcmp(curr_hour,mid_night)==0)&&(!new_log_file))
        {
                close(flog);
                open_log();

        }
        if((strcmp(curr_hour,mid_night) != 0)&&(new_log_file))
        {
                new_log_file =0;
        }
        pthread_mutex_unlock(&LMutex);

}

