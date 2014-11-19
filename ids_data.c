#include "ids_common.h"
#include "ids_data.h"

#define QUERRY_MAXSIZE 256

void init_slave_params();

WORD LoWord(unsigned int val)
{
	return ((val<<16)>>16);
}

WORD HiWord(unsigned int val)
{
	return val>>16;
}


WORD Add_CRC(BYTE buf[], int len)
{
	WORD crc = 0xFFFF;
	int i=0,pos=0;


  	for(pos = 0; pos < len; pos++)
	{
    		crc ^= (WORD )buf[pos];

    		for (i = 8; i != 0; i--)
            {
      			if ((crc & 0x0001) != 0)
                {
        			crc >>= 1;
        			crc ^= 0xA001;
      			}
      			else
        			crc >>= 1;
    		}
  	}
  	return ((WORD)(crc<<8)|(WORD)(crc>>8));
}


BYTE LoByte(WORD val)
{
	return ((val<<8)>>8);
}



BYTE HiByte(WORD val)
{
	return val>>8;
}


int Check_CRC(BYTE * pktdata, int pktcount)
{

            if(pktdata[pktcount-2]==HiByte(Add_CRC(pktdata,pktcount-2)))
            {
                if(pktdata[pktcount-1]==LoByte(Add_CRC(pktdata,pktcount-2)))
                {
                    return TRUE;
                }
            }
            return FALSE;
}


int compare_float(float f1, float f2,float precision)
{

  if(((f1 - precision) < f2) && ((f1 + precision) > f2))
   {
    return TRUE;
   }
  else
   {
    return FALSE;
   }
 }


void init_slave_params()
{
    int j=0,k=0;
    char mquerry_msg[QUERRY_MAXSIZE];
    MYSQL_RES *res;
    MYSQL_ROW row;
    MYSQL *conn;
    char *server = "127.0.0.1";
    //char *server = "192.168.1.4";
    char *user = "comserver";
    char *password = "compass";
    char *database = "ilids_nov";

        conn = mysql_init(NULL);

        if (!mysql_real_connect(conn, server,user, password, database, 0, NULL, 0))
        //if (!mysql_real_connect(conn, saddr,user, password, database, 0, NULL, 0))
        {
            fprintf(stderr, "%s\n", mysql_error(conn));
            return;
        }

        k=0;
        for(j=0;j<MAX_PARAMS;j++)
        {
            if(k==48)
            {
                k=512;
            }
            if(k==592)
            {
                k=1280;
            }
            memset(mquerry_msg,0x0,QUERRY_MAXSIZE);
            sprintf(mquerry_msg,"select data from data where time between \"2014-10-29 00:00:00\" and \"2014-10-29 23:59:00\" and device_id=%d and address_map=%d ORDER by address_map ASC LIMIT 1",7,k);//take initial data from kims data for oct 28,2014 as sample data
            if(!mysql_query(conn,mquerry_msg))
            {
                res = mysql_use_result(conn);
                if((row = mysql_fetch_row(res)) != NULL)
                {
                    param_list[j].p_val=strtof(row[0],NULL);//atof(row[0]);
                    //param_list[j].offset=(float )(.01 * param_list[j].p_val);
                }
                else
                {
                    param_list[j].p_val=0.0;
                }
                printf("\n%d  -> %.2f",param_list[j].p_addr,param_list[j].p_val);
                mysql_free_result(res);
            }
            k+=2;

        }
        mysql_close(conn);

}


void reverse_b(BYTE *t_addr,BYTE *s_addr,int bcount)
{
    int i=0;

    for(i=0;i<bcount;i++)
    {
        t_addr[i]=s_addr[bcount-i-1];
    }
}

void make_val(BYTE * inval,int val)
{
    inval[0]=HiByte(HiWord(val));
    inval[1]=LoByte(HiWord(val));
    inval[2]=HiByte(LoWord(val));
    inval[3]=LoByte(LoWord(val));
}

void prepare_slave_data(BYTE *inbuf,int inlen)
{
    int slave_id=0,cmd=0,k=0,wcount=0;
    WORD start_addr=0,no_of_regs=0;
    int no_of_params=0;
    int i=0,j=0,count=0,retw=0;
    int val=0;
    float fval=0.0,foff=0.0;

    slave_id=inbuf[0];//get slave id from master data

    srand((unsigned int)time(NULL));//generate new random seed

    for(i=0;i<no_of_devices;i++)
    {
        if(dev_id[i]==slave_id)
        {
                printf("\n");
                for(i=0;i<inlen;i++)
                {
                    printf("%X ",inbuf[i]);
                }
                printf("\n");

                cmd=inbuf[1];

                switch(cmd)
                {
                    case Read_Output_Register:

                            reverse_b((BYTE *)&start_addr,(BYTE *)&inbuf[2],2);
                            reverse_b((BYTE *)&no_of_regs,(BYTE *)&inbuf[4],2);
                            //no of regs means no of words since each reg is 1 word ie:2 bytes, so in response msg,contents of each register will be encoded as 2 bytes.
                            printf("\nstart addr is %d\n",start_addr);
                            printf("\nnregs is %d\n",no_of_regs);
                            //return;
                            //no of params =40
                            //no of regs to read 80
                            //no of bytes 160
                            for(i=0;i<MAX_PARAMS;i++)
                            {
                                j=0;
                                count=0;
                                if(param_list[i].p_addr==start_addr)
                                {
                                    //below 3 lines fills the header of slave response
                                    out_buf[j++]=slave_id;
                                    out_buf[j++]=cmd;
                                    out_buf[j++]=no_of_regs*2;//this is confusing but seen to be correct,this field indicates the no of bytes,hence no of regs*2,master sends it this way
                                    no_of_params=no_of_regs/2;//this is confusing,but correct by checking master-slave comm,this indicates actual no of params,since each param is 2 bytes(or 1 word),so =no_of_regs,master sends it this way

                                    //below loop fills the data starting from startaddr upto the required number of params for the slave
                                    while(count<(no_of_params))
                                    {

                                        printf("\nparamval=%f,",param_list[i].p_val);
                                        if(!compare_float(param_list[i].p_val,0,0.1))//if param.val != 0
                                        {
                                            fval=(float )(param_list[i].p_val / (float )param_list[i].mf);
                                            val=floor(fval);
                                            foff=(float)rand()/(float)(RAND_MAX/param_list[i].offset);//generate a random number between 0 and param_list[i].offset
                                            //float x = (float)rand()/(float)(RAND_MAX/a);
                                            param_list[i].p_val=param_list[i].p_val+foff;
                                        }
                                        else
                                        {
                                            val=0;
                                        }

                                        printf("val=%d, paramval=%f, addrmap is %d",val,param_list[i].p_val,param_list[i].p_addr);

                                        //val=val + (0.01 * val);
                                        //param_list[i].p_val =(float )((param_list[i].p_val) + (((float)rand()/(float)(RAND_MAX)) * (2*param_list[i].offset)));
                                        //val=val+2;
                                        make_val(&out_buf[j],val);
                                        //param_list[i].p_val=val;
                                        //printf("\naddr=%d,val=%f,i=%d,j=%d,count=%d,outbuf[%d] is %X,outbuf[%d] is %X,outbuf[%d] is %X,outbuf[%d] is %X",param_list[i].p_addr ,param_list[i].p_val,i,j,count,j,out_buf[j],j+1,out_buf[j+1],j+2,out_buf[j+2],j+3,out_buf[j+3]);
                                        i++;
                                        j=j+4;
                                        count++;
                                    }

                                    out_buf[j++]=HiByte(Add_CRC(out_buf,(no_of_regs*2)+3));
                                    out_buf[j]=LoByte(Add_CRC(out_buf,(no_of_regs*2)+3));
                                    /*printf("\ncount is %d,i is %d,j is %d\n",count,i,j);
                                    for(k=0;k<=j;k++)
                                    {
                                        printf("%X ",out_buf[k]);
                                    }
                                    printf("\n");*/
                                    //return;

                                    if(clientfd>0)
                                    {
                                        if((retw=write(clientfd,out_buf,j+1)) <= 0)
                                        {
                                            //sprintf(msg_to_log,"NW Error %s,Disconnecting",strerror(errno));
                                            //log_to_file(msg_to_log,strlen(msg_to_log));
                                            perror("Write");
                                            printf("\nClient closing connection\n");
                                            FD_CLR(clientfd,&socket_set);
                                            close(clientfd);
                                            clientfd=-1;
                                        }
                                        else
                                        {
                                            wcount=sprintf(msg_to_log,"WRITTEN DATA ");
                                            for(k=0;k<retw;k++)
                                            {
                                                wcount += sprintf(&msg_to_log[wcount]," %02X",out_buf[k]);
                                            }
                                            wcount += sprintf(&msg_to_log[wcount]," TO MASTER");
                                            log_to_file(msg_to_log,wcount);
                                        }
                                    }
                                    break;
                                }

                            }
                            break;

                    case Preset_Single_Register:
                            break;

                    case Preset_Multiple_Registers:
                            break;

                    default : break;
                }
                break;

            }
        }

}

void process_master_data(BYTE *inbuf,int inlen)
{
    int k=0,wcount=0;

    if(Check_CRC(inbuf,inlen))
    {
            prepare_slave_data(inbuf,inlen);
    }
    else
    {
        wcount=sprintf(msg_to_log,"CRC FAILED READ DATA ");
        for(k=0;k<inlen;k++)
        {
            wcount += sprintf(&msg_to_log[wcount]," %02X",inbuf[k]);
        }
        wcount += sprintf(&msg_to_log[wcount]," FROM MASTER");
        log_to_file(msg_to_log,wcount);
    }
}

