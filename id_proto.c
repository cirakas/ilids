/*
 * id_proto.c
 *
 * This is the source file where the commands from
 * the slave devices are processed and send for updation
 * in the database.
 *
 * V. SREEJITH : sree777@gmail.com : July,2014
 *
 * This program is a part of the iLIDS project
 *
 */

#include "id_common.h"


WORD Add_CRC(BYTE buf[], int len);
int Check_CRC(BYTE * pktdata, int pktcount);
BYTE LoByte(WORD val);
BYTE HiByte(WORD val);
WORD LoWord(unsigned int val);
WORD HiWord(unsigned int val);

void switch_params(BYTE * pktdata, int pktcount);

void Process_ParamsA(BYTE * pktdata, int pktcount);
void Process_ParamsB(BYTE * pktdata, int pktcount);
void Process_ParamsC(BYTE * pktdata, int pktcount);
void Process_ParamsRand(BYTE * pktdata, int pktcount);


char querry_msg[QUERRY_MAXSIZE];





/**@brief  This function Adds CRC to the outgoing message

           Function: Add_CRC

           Purpose:  Adds the CRC bytes to the end of each message to be send
                     to the slave device.

           Returns:  CRC WORD
*/

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

/**@brief  This function Returns the Lower Byte of the WORD

           Function: LoByte

           Purpose:  Returns the Lower Byte from the input WORD val.

           Returns:  Lower Byte
*/

BYTE LoByte(WORD val)
{
	return ((val<<8)>>8);
}

/**@brief  This function Returns the Higher Byte of the WORD

           Function: HiByte

           Purpose:  Returns the Higher Byte from the input WORD val.

           Returns:  Higher Byte
*/

BYTE HiByte(WORD val)
{
	return val>>8;
}

/**@brief  This function Returns the Lower Word of the integer

           Function: LoWord

           Purpose:  Returns the Lower Word from the input integer val.

           Returns:  Lower Word
*/

WORD LoWord(unsigned int val)
{
	return ((val<<16)>>16);
}

/**@brief  This function Returns the Higher Word of the integer

           Function: HiWord

           Purpose:  Returns the Higher Word from the input integer val.

           Returns:  Higher Word
*/

WORD HiWord(unsigned int val)
{
	return val>>16;
}

/**@brief  This function Compares two floating point numbers

           Function: compare_float

           Purpose:  Compares two floating point numbers according to the given
                     precision value and returns True or False.

           Returns:  True or False
*/

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


/**@brief  This function reverses the bytes in an integer.

           Function: reverse_b

           Purpose:  Reverses bcount number of bytes in the input value and
                     stores in the target value.This is for correcting the
                     endianness issue.

           Returns:  None
*/

void reverse_b(BYTE *t_addr,BYTE *s_addr,int bcount)
{
    int i=0;

    for(i=0;i<bcount;i++)
    {
        t_addr[i]=s_addr[bcount-i-1];
    }
}

/**@brief  This function switches calls to the different data processing functions based on the address offset.

           Function: switch_params

           Purpose:  Based on the starting offset address of the params,this
                     procedure will call the different functions needed for
                     the processing of each set of params.If random mode is
                     enabled,it will call the function for generating random
                     values.


           Returns:  None
*/


void switch_params(BYTE * pktdata, int pktcount)
{

        switch(scondition)
        {
            case 0:
            if(random_mode)
            {
                rand_count+=3;
                if(rand_count > rand_time)
                {
                    rand_count=0;
                    Process_ParamsRand(pktdata,pktcount);
                }
            }
            else
            {
                Process_ParamsA(pktdata,pktcount);
            }

            break;

            case 512:
            if(!random_mode)
            {
                Process_ParamsB(pktdata,pktcount);
            }
            break;

            case 1280:
            if(!random_mode)
            {
                Process_ParamsC(pktdata,pktcount);
            }
            break;
            default:break;
        }

}

/**@brief  This function Checks the CRC Bytes in the input message.

           Function: Check_CRC

           Purpose:  This function will check the CRC bytes in the incoming
                     packet and returns TRUE if they match.

           Returns:  TRUE or FALSE;
*/


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

/**@brief  This function generates random values for testing.

           Function: Process_ParamsRand

           Purpose:  This function will generate random values for the first set of
                     params and updates in the database for testing purposes.


           Returns:  None
*/

void Process_ParamsRand(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0,tcval=0;
NVALUE neg_val;

                    db_id1=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);

                        if(pktdata[i]==0xFF)
                        {
                            neg_val.bval[0]=pktdata[i+3];
                            neg_val.bval[1]=pktdata[i+2];
                            neg_val.wval=neg_val.wval-1;
                            neg_val.wval=~neg_val.wval;

                            cval=(float )(params1rand[db_id1].mf * neg_val.wval * -1);
                            sprintf(msg_to_log,"Neg Value is %.2f",cval);
                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);

                        }
                        else
                        {
                            reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                            cval=(float )(params1rand[db_id1].mf * mval);
                        }

                        tcval=0.0;
                        if(!compare_float(params1rand[db_id1].val,0.0,params1rand[db_id1].offset))
                        {
                            tcval=(params1rand[db_id1].val-RANGEVAL) + (drand48() * ((params1rand[db_id1].val+RANGEVAL) - (params1rand[db_id1].val-RANGEVAL)));
                            if((params1rand[db_id1].addr_off==30)||(params1rand[db_id1].addr_off==32)||(params1rand[db_id1].addr_off==34))
                            {
                                tcval=drand48();
                            }
                        }
                        sprintf(msg_to_log,"%s -> %.2f",params1rand[db_id1].pname,tcval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        if(!compare_float(tcval,params1rand[db_id1].val,params1rand[db_id1].offset))
                        {
                            memset(querry_msg,0x0,QUERRY_MAXSIZE);
                            sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],tcval,params1rand[db_id1].addr_off,!compare_float(tcval,params1rand[db_id1].val,params1rand[db_id1].offset));
                            params1rand[db_id1].val=tcval;
                            onEINTR:
                            if (mysql_query(conn,querry_msg))
                            {
                                   if(errno == EINTR)
                                   {
                                        goto onEINTR;
                                   }
                                   sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                   log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                            }
                        }


                        i+=4;
                        db_id1++;
                        if(db_id1==MAXPARAMS_A)
                        {
                            break;
                        }


                    }


}

/**@brief  This function processes the first set of params from the meter.

           Function: Process_ParamsA

           Purpose:  This function will process the first set of params coming from
                     the meter starting from address offset 0.This values will be
                     updated in the database.

           Returns:  None

*/


void Process_ParamsA(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0.0;
NVALUE neg_val;

                    db_id1=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        //sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        //log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);

                        if(pktdata[i]==0xFF)//negative value condition,not sure this will be reached
                        {
                            neg_val.bval[0]=pktdata[i+3];
                            neg_val.bval[1]=pktdata[i+2];
                            neg_val.wval=neg_val.wval-1;
                            neg_val.wval=~neg_val.wval;

                            cval=(float )(vlist[pktdata[0]].param_valueA[db_id1].mf * neg_val.wval * -1);
                            sprintf(msg_to_log,"Neg Value is %.2f",cval);
                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        }
                        else
                        {
                            reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                            cval=(float )(vlist[pktdata[0]].param_valueA[db_id1].mf * mval);
                        }
                        sprintf(msg_to_log,"%s -> %.2f",vlist[pktdata[0]].param_valueA[db_id1].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        //Note Nov22,2014: Below all offset are currently set to 1 except powerfactor whose offset is set to 0.1.Later this should be adjusted while initialising for each type of parameters.
                        /*if(params1[db_id1].offset==1)//This is commented now for ParamA type params also.Each type of params can be varied differently by setting the offset accordingly while initialising the structure.//Setting of offset value based on present value.Here 1 specified so as to not apply this condition to powerfactor(whose value will be less than 1).This whole line Not commented here because paramsA seems reasonable for 1% change.For paramsB and C,commented this to currect cumulative energy field where 1% change from prev val,gives only very very few values.
                        {
                            if(!compare_float(cval,0,1))//if only present val not 0
                            {
                                params1[db_id1].offset=(float )(0.01 * cval);
                                sprintf(msg_to_log,"Offset is %.2f",params1[db_id1].offset);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
                        }*/

                        if(!compare_float(cval,vlist[pktdata[0]].param_valueA[db_id1].val,vlist[pktdata[0]].param_valueA[db_id1].offset))
                        {
                            vlist[pktdata[0]].param_valueA[db_id1].val=cval;//vlist set to present value
                            //for(k=0;k<no_of_cmds;k++)//commented to temporarily disable the updation of db based on params given in config file,but instead update all params from meter
                            {
                                //if((cmd_config[k].devid==pktdata[0])&&(cmd_config[k].start_addr==params1[db_id1].addr_off))//commented to temporarily disable the updation of db based on params given in config file,but instead update all params from meter
                                {
                                    memset(querry_msg,0x0,QUERRY_MAXSIZE);
                                    sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],cval,vlist[pktdata[0]].param_valueA[db_id1].addr_off,!compare_float(cval,vlist[pktdata[0]].param_valueA[db_id1].val,vlist[pktdata[0]].param_valueA[db_id1].offset));
                                    sprintf(msg_to_log,"%s",querry_msg);
                                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);

                                    onEINTR:
                                    if (mysql_query(conn,querry_msg))
                                    {
                                           if(errno == EINTR)
                                           {
                                                goto onEINTR;
                                           }
                                           sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                           log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                    }
                                }
                            }
                        }

                        i+=4;//2 words,so 4 bytes
                        db_id1++;
                        if(db_id1==MAXPARAMS_A)
                        {
                            break;
                        }


                    }


}

/**@brief  This function processes the second set of params from the meter.

           Function: Process_ParamsB

           Purpose:  This function will process the second set of params coming from
                     the meter starting from address offset 512.This values will be
                     updated in the database.

           Returns:  None
*/

void Process_ParamsB(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0;


                    db_id2=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        //sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        //log_to_file(msg_to_log,strlen(msg_to_log),3);
                        reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                        cval=(float )(vlist[pktdata[0]].param_valueB[db_id2].mf * mval);
                        sprintf(msg_to_log,"%s -> %.2f",vlist[pktdata[0]].param_valueB[db_id2].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        /*if(params2[db_id2].offset==1) //setting of offset commented To currect cumulative energy field where 1% change from prev val,gives only very very few values.
                        {
                            if(!compare_float(cval,0,1))
                            {
                                params2[db_id2].offset=(float )(0.01 * cval);
                                sprintf(msg_to_log,"Offset is %.2f",params2[db_id2].offset);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
                        }*/

                        if(!compare_float(cval,vlist[pktdata[0]].param_valueB[db_id2].val,vlist[pktdata[0]].param_valueB[db_id2].offset))//This may need modifn as vlist contains vals from a previous kims db as set in init_slave_params, done for emulator testing.This should work okay also without modifn.
                        {
                            vlist[pktdata[0]].param_valueB[db_id2].val=cval;//vlist set to present value
                            //for(k=0;k<no_of_cmds;k++)//commented to temporarily disable the updation of db based on params given in config file,but instead update all params from meter
                            {
                                //if((cmd_config[k].devid==pktdata[0])&&(cmd_config[k].start_addr==params2[db_id2].addr_off))//commented to temporarily disable the updation of db based on params given in config file,but instead update all params from meter
                                {

                                    memset(querry_msg,0x0,QUERRY_MAXSIZE);
                                    sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],cval,vlist[pktdata[0]].param_valueB[db_id2].addr_off,!compare_float(cval,vlist[pktdata[0]].param_valueB[db_id2].val,vlist[pktdata[0]].param_valueB[db_id2].offset));
                                    sprintf(msg_to_log,"%s",querry_msg);
                                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);


                                    onEINTR:
                                    if (mysql_query(conn,querry_msg))
                                    {
                                            if(errno == EINTR)
                                            {
                                                goto onEINTR;
                                            }
                                            sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                    }
                                }
                            }

                        }

                        i+=4;//2 words ,so 4 bytes
                        db_id2++;
                        if(db_id2==MAXPARAMS_B)
                        {
                            break;
                        }
                    }

}

/**@brief  This function processes the third set of params from the meter.

           Function: Process_ParamsC

           Purpose:  This function will process the third set of params coming from
                     the meter starting from address offset 1280.This values will be
                     updated in the database.

           Returns:  None
*/

void Process_ParamsC(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0;


                    db_id3=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        //sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        //log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                        cval=(float )(vlist[pktdata[0]].param_valueC[db_id3].mf * mval);
                        sprintf(msg_to_log,"%s -> %.2f",vlist[pktdata[0]].param_valueC[db_id3].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        /*if(params3[db_id3].offset==1) //setting of offset commented To currect cumulative energy field where 1% change from prev val,gives only very very few values.
                        {
                            if(!compare_float(cval,0,1))
                            {
                                params3[db_id3].offset=(float )(0.01 * cval);
                                sprintf(msg_to_log,"Offset is %.2f",params3[db_id3].offset);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
                        }*/

                        if(!compare_float(cval,vlist[pktdata[0]].param_valueC[db_id3].val,vlist[pktdata[0]].param_valueC[db_id3].offset))//This may need modifn as vlist contains vals from a previous kims db as set in init_slave_params, done for emulator testing.This should work okay also without modifn.
                        {
                            vlist[pktdata[0]].param_valueC[db_id3].val=cval;//vlist set to present value.

                            //for(k=0;k<no_of_cmds;k++) //commented to temporarily disable the updation of db based on params given in config file,but instead update all params from meter
                            {
                                //if((cmd_config[k].devid==pktdata[0])&&(cmd_config[k].start_addr==params3[db_id3].addr_off))//commented to temporarily disable the updation of db based on params given in config file,but instead update all params from meter
                                {

                                    memset(querry_msg,0x0,QUERRY_MAXSIZE);
                                    sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],cval,vlist[pktdata[0]].param_valueC[db_id3].addr_off,!compare_float(cval,vlist[pktdata[0]].param_valueC[db_id3].val,vlist[pktdata[0]].param_valueC[db_id3].offset));
                                    sprintf(msg_to_log,"%s",querry_msg);
                                    log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);

                                    onEINTR:
                                    if (mysql_query(conn,querry_msg))
                                    {
                                            if(errno == EINTR)
                                            {
                                                goto onEINTR;
                                            }
                                            sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_DEFAULT);
                                    }
                                }
                            }

                        }

                        //The following cases done,because for paramsC,the length of each param varies differently.
                        if(db_id3>14 && db_id3<19)
                        {
                            i+=8;
                        }
                        else if(db_id3>21 && db_id3<25)
                        {
                            i+=6;
                        }
                        else if(db_id3==25)
                        {
                            i+=16;

                        }
                        else if(db_id3>25 && db_id3<29)
                        {
                            i+=2;
                        }
                        else
                        {
                            i+=4;
                        }


                        db_id3++;
                        if(db_id3==(MAXPARAMS_C-1))
                        {
                            break;

                        }
                    }

}
