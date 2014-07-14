#include "id_common.h"

int new_log_file;
int log_mode;
int flog;
mode_t umask_access_mode;
char msg_to_log[MSG_SIZE];
pthread_mutex_t LMutex;

void open_log();
void log_to_file(char * log_msg,int log_count);

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

void log_to_file(char * log_msg,int log_count)
{
time_t curtime;
char * formatted_time;
char curr_hour[6],mid_night[6]="00:00";
char * format = "::";

        pthread_mutex_lock(&LMutex);
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

