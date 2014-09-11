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
PARAM_DETAILS params1[MAXPARAMS_A]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,0.0,1},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,0.0,1},{4,"B_Phase_Voltage_4W",2,0.01,0.0,1},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,0.0,1},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,0.0,1},{10,"B_Phase_Current_4W",2,0.001,0.0,1},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,0.0,1},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,0.0,1},{16,"Active_Power_B_Phase_4W",2,0.0001,0.0,1},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,0.0,1},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,0.0,1},{22,"Reactive_Power_B_Phase_4W",2,0.0001,0.0,1},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,0.0,1},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,0.0,1},{28,"Apparent_Power_B_Phase_4W",2,0.0001,0.0,1},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.0,0.1},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.0,0.1},{34,"Power_Factor_B_Phase_4W",2,0.001,0.0,0.1},{36,"Total_Active_Power",2,0.0001,0.0,1},{38,"Total_Reactive_Power",2,0.0001,0.0,1},{40,"Total_Apparent_Power",2,0.0001,0.0,1},{42,"Total_Power_Factor",2,0.001,0.0,1},{44,"Line_Frequency",2,0.01,0.0,1},{46,"Phase_Sequence",2,1,0.0,1}};
PARAM_DETAILS params2[MAXPARAMS_B]={{512,"Cumulative_energy_forward_kVAh",2,0.01,0.0,1},{514,"Cumulative_energy_forward_kWh",2,0.01,0.0,1},{516,"Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1},{518,"Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1},{520,"Cumulative_energy_reverse_kVAh",2,0.01,0.0,1},{522,"Cumulative_energy_reverse_kWh",2,0.01,0.0,1},{524,"Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1},{526,"Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1},{528,"Backup_1_Cumulative_energy_forward_kVAh",2,0.01,0.0,1},{530,"Backup_1_Cumulative_energy_forward_kWh",2,0.01,0.0,1},{532,"Backup_1_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1},{534,"Backup_1_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1},{536,"Backup_1_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1},{538,"Backup_1_Cumulative_energy_reverse_kWh",2,0.01,0.0,1},{540,"Backup_1_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1},{542,"Backup_1_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1},{544,"Backup_2_Cumulative_energy_forward_kVAh",2,0.01,0.0,1},{546,"Backup_2_Cumulative_energy_forward_kWh",2,0.01,0.0,1},{548,"Backup_2_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1},{550,"Backup_2_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1},{552,"Backup_2_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1},{554,"Backup_2_Cumulative_energy_reverse_kWh",2,0.01,0.0,1},{556,"Backup_2_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1},{558,"Backup_2_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1},{560,"Backup_3_Cumulative_energy_forward_kVAh",2,0.01,0.0,1},{562,"Backup_3_Cumulative_energy_forward_kWh",2,0.01,0.0,1},{564,"Backup_3_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1},{566,"Backup_3_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1},{568,"Backup_3_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1},{570,"Backup_3_Cumulative_energy_reverse_kWh",2,0.01,0.0,1},{572,"Backup_3_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1},{574,"Backup_3_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1},{576,"Backup_4_Cumulative_energy_forward_kVAh",2,0.01,0.0,1},{578,"Backup_4_Cumulative_energy_forward_kWh",2,0.01,0.0,1},{580,"Backup_4_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,1},{582,"Backup_4_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,1},{584,"Backup_4_Cumulative_energy_reverse_kVAh",2,0.01,0.0,1},{586,"Backup_4_Cumulative_energy_reverse_kWh",2,0.01,0.0,1},{588,"Backup_4_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,1},{590,"Backup_4_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,1}};
PARAM_DETAILS params3[MAXPARAMS_C]={{1280,"Reset_MD1",2,0.01,0.0,1},{1282,"Reset_MD2",2,0.01,0.0,1},{1284,"Reset_MD3",2,0.01,0.0,1},{1286,"Backup_1_MD1",2,0.01,0.0,1},{1288,"Backup_1_MD2",2,0.01,0.0,1},{1290,"Backup_1_MD3",2,0.01,0.0,1},{1292,"Backup_2_MD1",2,0.01,0.0,1},{1294,"Backup_2_MD2",2,0.01,0.0,1},{1296,"Backup_2_MD3",2,0.01,0.0,1},{1298,"Backup_3_MD1",2,0.01,0.0,1},{1300,"Backup_3_MD2",2,0.01,0.0,1},{1302,"Backup_3_MD3",2,0.01,0.0,1},{1304,"Backup_4_MD1",2,0.01,0.0,1},{1306,"Backup_4_MD2",2,0.01,0.0,1},{1308,"Backup_4_MD3",2,0.01,0.0,1}};//,{1536,"Backup_1_reset_date_time_&_type",4,1,0.0,1},{1540,"Backup_2_reset_date_time_&_type",4,1,0.0,1},{1544,"Backup_3_reset_date_time_&_type",4,1,0.0,1},{1548,"Backup_4_reset_date_time_&_type",4,1,0.0,1},{1792,"Reset_Cumulative_MD1",2,0.01,0.0,1},{1794,"Reset_Cumulative_MD2",2,0.01,0.0,1},{1796,"Reset_Cumulative_MD3",2,0.01,0.0,1},{1872,"RD1_elapsed_time",3,0.01,0.0,1},{1875,"RD2_elapsed_time",3,0.01,0.0,1},{1878,"RD3_elapsed_time",3,0.01,0.0,1},{1920,"Anomaly_string_Format:_MSB_A_all_other_bytes_must_be_0xFF_Faults_are_indicated_by_digits_AENRXD_E–_flash_code_corruption_N_EEPROM_setup_corruption_R_RTC_corruption_XD_exception_illegal_opcode",8,1,0.0,1},{1928,"Reset_count_Format:_00_to_99_0x0063",1,1,0.0,1},{1929,"Com.count_no._of_times_meter_programmed_via_front_panel_Format_00_to_99_0x0063",1,1,0.0,1},{1930,"CT_Tapping_0x0001_1A_tappin_0x0005_5A_tapping",1,1,0.0,1},{1931,"Reserved_READ_AND_WRITE_PARAMETERS_Real_time_clock_RTC",1,1,0.0,1},{256,"Current_Time_Year_Month_Format:YYMM_BCD",1,1,0.0,1},{257,"Current_Time_Date_Day_Format:DTDY_BCD",1,1,0.0,1},{258,"Current_Time_Hour_Minute_Format:HHMM_BCD",1,1,0.0,1},{259,"Current_Time_econd_Format:SS00_BCD",1,1,0.0,1},{2048,"CT_Primary",1,1,0.0,1},{2049,"CT_Secondary",1,1,0.0,1},{2050,"PT_Primary",1,1,0.0,1},{2051,"PT_Secondary",1,1,0.0,1},{2128,"Reset_type_days_and_Lockout_days_1st_word_Reset_time_HH_MM_hour_min_2nd_word",2,1,0.0,1},{2160,"Setting_for_MD1",2,1,0.0,1},{2162,"Setting_for_MD2",2,1,0.0,1},{2164,"Setting_for_MD3",2,1,0.0,1},{2304,"Method_of_energy_calculation_Lead=Lead_Lead=UPF",1,1,0.0,1},{2305,"Meter_direction_Unidirectional_Bidirectional",1,1,0.0,1}};
PARAM_DETAILS params1rand[MAXPARAMS_A]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,227.0,5},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,236.0,5},{4,"B_Phase_Voltage_4W",2,0.01,239.0,5},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,119.0,10},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,103.0,10},{10,"B_Phase_Current_4W",2,0.001,123.0,10},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,22.0,5},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,21.0,5},{16,"Active_Power_B_Phase_4W",2,0.0001,24.0,5},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,16.0,5},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,14.0,5},{22,"Reactive_Power_B_Phase_4W",2,0.0001,19.0,5},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,28.0,5},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,26.0,5},{28,"Apparent_Power_B_Phase_4W",2,0.0001,31.0,5},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.5,0.1},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.5,0.1},{34,"Power_Factor_B_Phase_4W",2,0.001,0.5,0.1},{36,"Total_Active_Power",2,0.0001,68.0,5},{38,"Total_Reactive_Power",2,0.0001,50.0,5},{40,"Total_Apparent_Power",2,0.0001,85.0,5},{42,"Total_Power_Factor",2,0.001,0.0,5},{44,"Line_Frequency",2,0.01,0.0,5},{46,"Phase_Sequence",2,1,0.0,5}};


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
int i=0,k=0;
unsigned int mval=0;
float cval=0.0;
NVALUE neg_val;

                    db_id1=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        //sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        //log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);

                        if(pktdata[i]==0xFF)
                        {
                            neg_val.bval[0]=pktdata[i+3];
                            neg_val.bval[1]=pktdata[i+2];
                            neg_val.wval=neg_val.wval-1;
                            neg_val.wval=~neg_val.wval;

                            cval=(float )(params1[db_id1].mf * neg_val.wval * -1);
                            sprintf(msg_to_log,"Neg Value is %.2f",cval);
                            log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        }
                        else
                        {
                            reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                            cval=(float )(params1[db_id1].mf * mval);
                        }
                        sprintf(msg_to_log,"%s -> %.2f",params1[db_id1].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        if(params1[db_id1].offset==1)
                        {
                            if(!compare_float(cval,0,1))
                            {
                                params1[db_id1].offset=(float )(0.01 * cval);
                                sprintf(msg_to_log,"Offset is %.2f",params1[db_id1].offset);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
                        }

                        if(!compare_float(cval,vlist[pktdata[0]].param_valueA[db_id1],params1[db_id1].offset))
                        {
                            vlist[pktdata[0]].param_valueA[db_id1]=cval;
                            for(k=0;k<no_of_cmds;k++)
                            {
                                if((cmd_config[k].devid==pktdata[0])&&(cmd_config[k].start_addr==params1[db_id1].addr_off))
                                {
                                    memset(querry_msg,0x0,QUERRY_MAXSIZE);
                                    sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],cval,params1[db_id1].addr_off,!compare_float(cval,vlist[pktdata[0]].param_valueA[db_id1],params1[db_id1].offset));
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

                        i+=4;
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
int i=0,k=0;
unsigned int mval=0;
float cval=0;


                    db_id2=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        //sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        //log_to_file(msg_to_log,strlen(msg_to_log),3);
                        reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                        cval=(float )(params2[db_id2].mf * mval);
                        sprintf(msg_to_log,"%s -> %.2f",params2[db_id2].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        if(params2[db_id2].offset==1)
                        {
                            if(!compare_float(cval,0,1))
                            {
                                params2[db_id2].offset=(float )(0.01 * cval);
                                sprintf(msg_to_log,"Offset is %.2f",params2[db_id2].offset);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
                        }

                        if(!compare_float(cval,vlist[pktdata[0]].param_valueB[db_id2],params2[db_id2].offset))
                        {
                            vlist[pktdata[0]].param_valueB[db_id2]=cval;
                            for(k=0;k<no_of_cmds;k++)
                            {
                                if((cmd_config[k].devid==pktdata[0])&&(cmd_config[k].start_addr==params2[db_id2].addr_off))
                                {

                                    memset(querry_msg,0x0,QUERRY_MAXSIZE);
                                    sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],cval,params2[db_id2].addr_off,!compare_float(cval,vlist[pktdata[0]].param_valueB[db_id2],params2[db_id2].offset));
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

                        i+=4;
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
int i=0,k=0;
unsigned int mval=0;
float cval=0;


                    db_id3=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        //sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        //log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                        cval=(float )(params3[db_id3].mf * mval);
                        sprintf(msg_to_log,"%s -> %.2f",params3[db_id3].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                        if(params3[db_id3].offset==1)
                        {
                            if(!compare_float(cval,0,1))
                            {
                                params3[db_id3].offset=(float )(0.01 * cval);
                                sprintf(msg_to_log,"Offset is %.2f",params3[db_id3].offset);
                                log_to_file(msg_to_log,strlen(msg_to_log),DEBUG_LEVEL_3);
                            }
                        }

                        if(!compare_float(cval,vlist[pktdata[0]].param_valueC[db_id3],params3[db_id3].offset))
                        {
                            vlist[pktdata[0]].param_valueC[db_id3]=cval;

                            for(k=0;k<no_of_cmds;k++)
                            {
                                if((cmd_config[k].devid==pktdata[0])&&(cmd_config[k].start_addr==params3[db_id3].addr_off))
                                {

                                    memset(querry_msg,0x0,QUERRY_MAXSIZE);
                                    sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map,category) VALUES (%d,%.2f,%d,%d)",pktdata[0],cval,params3[db_id3].addr_off,!compare_float(cval,vlist[pktdata[0]].param_valueC[db_id3],params3[db_id3].offset));
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
