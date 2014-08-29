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
char temp_buf[256];

int OpenConfiguration();
int remove_blnk_lines(char *line);
int GetAddressList();
int GetDevIDs();
int GetPortName();
int Read_Conf();


int OpenConfiguration()
{
    if((fconf=fopen("idriver.conf",(const char *)"r"))!=NULL)
    {
        return TRUE;
    }
    return FALSE;

}

int GetMode()
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
                if(strcmp(temp_buf,"[EMULATOR_MODE]")==0)
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
                        printf("\nemulator mode is %s",portname);
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
            if(GetDevIds())
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

            if(GetAddressList())
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

            if(GetPortName())
            {
                printf("\nCOMPORT is %s\n",portname);
            }
            GetMode();
            fclose(fconf);

      }
      return FALSE;
}
