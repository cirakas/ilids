/*
 * id_conf.c
 *
 * This is the source file where the configuration file
 * for idriver is read and parameters are stored.
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

#include "id_common.h"

typedef struct
{
  char ename[20];
  int elist[60];
  int no_of_elem;
}HWCONF;

HWCONF addr_config[10];
HWCONF dev_config[10];
int no_of_addr;
int no_of_dev;
FILE * fconf=NULL;
char portname[256];
char em_mode[256];
char bd_rate[256];
char poll_int[256];
int  baud=0;
char temp_buf[256];

int OpenConfiguration();
int remove_blnk_lines(char *line);
int GetAddressList();
int GetDevIDs();
int GetPortName();
int GetMode();
int GetBaudRate();
int GetPollInterval();
int Read_Conf();


int OpenConfiguration()
{
    if((fconf=fopen("idriver.conf",(const char *)"r"))!=NULL)
    {
        return TRUE;
    }
    return FALSE;

}

int GetPollInterval()
{
        memset(poll_int,0,256);
        fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[POLL_INTERVAL]")==0)
                {
                    memset(poll_int,0,256);
                    fgets((char *)&poll_int[0],256,fconf);
                    bd_rate[strlen(poll_int)-1]=0x0;
                    p_int=strtol((char *)&poll_int[0],NULL,10);
                    sprintf(msg_to_log,"POLL INTERVAL is %d",p_int);
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                    return TRUE;
                }
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetBaudRate()
{
        memset(bd_rate,0,256);
        fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[BAUDRATE]")==0)
                {
                    memset(bd_rate,0,256);
                    fgets((char *)&bd_rate[0],256,fconf);
                    bd_rate[strlen(bd_rate)-1]=0x0;
                    baud=strtol((char *)&bd_rate[0],NULL,10);
                    sprintf(msg_to_log,"BAUDRATE is %d",baud);
                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);

                    return TRUE;
                }
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetMode()
{


        memset(em_mode,0,256);
        fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[EMULATOR_MODE]")==0)
                {
                    memset(em_mode,0,256);
                    fgets((char *)&em_mode[0],256,fconf);
                    em_mode[strlen(em_mode)-1]=0x0;
                    if(strcasecmp(em_mode,"YES")==0)
                    {
                        emulator_mode=TRUE;
                        return TRUE;
                    }

                }
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetPortName()
{

  char ch;

  memset(portname,0,256);
  fseek(fconf,0,SEEK_SET);

        while(!feof(fconf))
        {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                temp_buf[strlen(temp_buf)-1]=0x0;
                if(strcmp(temp_buf,"[COMPORT]")==0)
                {
                    memset(portname,0,256);
                    while(1)
                    {
                        ch=fgetc(fconf);
                        if((ch=='[')||(ch=='/')||feof(fconf))
                        {
                            if(feof(fconf))
                                clearerr(fconf);
                            break;
                        }
                    }
                    if(ch=='/')
                    {
                        portname[0]='/';
                        fgets((char *)&portname[1],256,fconf);
                        portname[strlen(portname)-1]=0x0;
                        cport=portname;
                        sprintf(msg_to_log,"COMPORT is %s",cport);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                        return TRUE;
                    }
                    else
                    {
                        return FALSE;
                    }
                }
            }
            else if(!feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;
}

int remove_blnk_lines(char *line)
{
  int line_len;

  line_len = strlen((char *)line);
  line[line_len-1]=0x0;
  line_len--;

  if(line_len < 2)
  {
     return 0;
  }
  return line_len;
}


int GetAddressList()
{
  int i=0,j=0,k=0,l=0;



    fseek(fconf,0,SEEK_SET);

    while(!feof(fconf))
    {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                if(!remove_blnk_lines(temp_buf)) continue;

                if(strcmp(temp_buf,"[ADDRESSLIST]")==0)
                {
                    i=0;
                    while(!feof(fconf))
                    {
                        if(fgets(temp_buf,256,fconf)!=NULL)
                        {
                            for(j=0;j<strlen(temp_buf);j++)
                            {
                                if(temp_buf[j]=='[')
                                {
                                    no_of_addr=i;
                                    return TRUE;
                                }
                            }
                            if( ! remove_blnk_lines(temp_buf) ) continue;

                            memcpy(addr_config[i].ename,temp_buf,strlen(temp_buf));
                            if(fgets(temp_buf,256,fconf)!=NULL)
                            {
                                temp_buf[strlen(temp_buf)-1]=',';
                                for(j=0,k=0,l=0;j<strlen(temp_buf);j++)
                                {
                                    if(temp_buf[j]==',')
                                    {
                                        addr_config[i].elist[k]= strtol((char *)&temp_buf[l],NULL,10);
                                        l=j+1;
                                        k++;

                                    }
                                }
                            }
                            addr_config[i].no_of_elem=k;
                            i++;
                        }
                    }
                }
            }
            else if(feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;
}

int GetDevIds()
{
    int i=0,j=0,k=0,l=0;



    fseek(fconf,0,SEEK_SET);

    while(!feof(fconf))
    {
            memset(temp_buf,0,256);
            if(fgets(temp_buf,256,fconf)!=NULL)
            {
                if(!remove_blnk_lines(temp_buf)) continue;

                if(strcmp(temp_buf,"[DEVICEIDS]")==0)
                {
                    i=0;
                    while(!feof(fconf))
                    {
                        if(fgets(temp_buf,256,fconf)!=NULL)
                        {
                            for(j=0;j<strlen(temp_buf);j++)
                            {
                                if(temp_buf[j]=='[')
                                {
                                    no_of_dev=i;
                                    return TRUE;
                                }
                            }
                            if( ! remove_blnk_lines(temp_buf) ) continue;

                            memcpy(dev_config[i].ename,temp_buf,strlen(temp_buf));
                            if(fgets(temp_buf,256,fconf)!=NULL)
                            {
                                temp_buf[strlen(temp_buf)-1]=',';
                                for(j=0,k=0,l=0;j<strlen(temp_buf);j++)
                                {
                                    if(temp_buf[j]==',')
                                    {
                                        dev_config[i].elist[k]= strtol((char *)&temp_buf[l],NULL,16);
                                        l=j+1;
                                        k++;
                                    }
                                }
                            }
                            dev_config[i].no_of_elem=k;
                            i++;
                        }
                    }
                }
            }
            else if(feof(fconf))
            {
                return FALSE;
            }
        }
        clearerr(fconf);
        return FALSE;

}

int Read_Conf()
{

  int i=0,j=0;


    if(OpenConfiguration())
    {
            if(!GetPortName())
            {
                sprintf(msg_to_log,"COMPORT is not defined in Config file,Exiting");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                return FALSE;
            }

            if(!GetBaudRate())
            {
                sprintf(msg_to_log,"BAUDRATE is not defined in Config file,Exiting");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                return FALSE;
            }

            if(!GetPollInterval())
            {
                p_int=POLL_INTERVAL;
                sprintf(msg_to_log,"POLL INTERVAL is not defined in Config file,Using Default Value %d milliseconds",p_int);
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
            }

            if(GetMode())
            {
                sprintf(msg_to_log,"EMULATOR MODE STARTING");
                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
            }

            if(GetDevIds())//This may not be needed.
            {
                for(i=0;i<no_of_dev;i++)
                {
                    printf("\nMeter %s Device Ids are : ",dev_config[i].ename);
                    for(j=0;j<dev_config[i].no_of_elem;j++)
                    {
                        printf(" %X",dev_config[i].elist[j]);
                    }
                    printf("\n");
                }

            }

            if(GetAddressList())//This needs to be included later in main program
            {
                for(i=0;i<no_of_addr;i++)
                {
                    printf("\nMeter %s AddressList is : ",addr_config[i].ename);
                    for(j=0;j<addr_config[i].no_of_elem;j++)
                    {
                        printf(" %d",addr_config[i].elist[j]);
                    }
                    printf("\n");
                }
            }

            fclose(fconf);

      }
      return TRUE;
}
