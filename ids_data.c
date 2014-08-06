#include "ids_common.h"
#include "ids_data.h"



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
    int i=0,j=0,count=0,retw=0;
    BYTE out_buf[MAXSIZE];

    slave_id=inbuf[0];

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
                            printf("\nstart addr is %d\n",start_addr);
                            printf("\nnregs is %d\n",no_of_regs);

                            for(i=0;i<MAX_PARAMS;i++)
                            {
                                j=0;
                                count=0;
                                if(param_list[i].p_addr==start_addr)
                                {
                                    out_buf[j++]=slave_id;
                                    out_buf[j++]=cmd;
                                    out_buf[j++]=no_of_regs*2;
                                    while(j<no_of_regs)
                                    {
                                        make_val(&out_buf[j],param_list[i].p_val);
                                        i++;
                                        j++;
                                        count=j;
                                    }
                                    out_buf[j++]=HiByte(Add_CRC(out_buf,count));
                                    out_buf[j]=LoByte(Add_CRC(out_buf,count));
                                    printf("\nout_buf\n");
                                    for(i=0;i<j;i++)
                                    {
                                        printf("%X ",out_buf[i]);
                                    }
                                    printf("\n");

                                    if(clientfd>0)
                                    {
                                        if((retw=write(clientfd,out_buf,j)) <= 0)
                                        {
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
                                            wcount += sprintf(&msg_to_log[wcount]," TO DEVICE");
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
    int i=0;

    if(Check_CRC(inbuf,inlen))
    {
        /*printf("\nRead Valid Data from Master\n");

        for(i=0;i<inlen;i++)
        {
            printf("%X ",inbuf[i]);
        }
        printf("\n");*/
        prepare_slave_data(inbuf,inlen);
    }
    else
    {
        printf("\nMaster Data : CRC Failed\n");
        for(i=0;i<inlen;i++)
        {
            printf("%X ",inbuf[i]);
        }
        printf("\n");
    }
}

