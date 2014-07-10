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
PARAM_DETAILS params1[24]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,0.0},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,0.0},{4,"B_Phase_Voltage_4W",2,0.01,0.0},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,0.0},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,0.0},{10,"B_Phase_Current_4W",2,0.001,0.0},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,0.0},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,0.0},{16,"Active_Power_B_Phase_4W",2,0.0001,0.0},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,0.0},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,0.0},{22,"Reactive_Power_B_Phase_4W",2,0.0001,0.0},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,0.0},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,0.0},{28,"Apparent_Power_B_Phase_4W",2,0.0001,0.0},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.0},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.0},{34,"Power_Factor_B_Phase_4W",2,0.001,0.0},{36,"Total_Active_Power",2,0.0001,0.0},{38,"Total_Reactive_Power",2,0.0001,0.0},{40,"Total_Apparent_Power",2,0.0001,0.0},{42,"Total_Power_Factor",2,0.001,0.0},{44,"Line_Frequency",2,0.01,0.0},{46,"Phase_Sequence",2,1}};
PARAM_DETAILS params1rand[24]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,227.0},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,236.0},{4,"B_Phase_Voltage_4W",2,0.01,239.0},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,119.0},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,103.0},{10,"B_Phase_Current_4W",2,0.001,123.0},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,22.0},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,21.0},{16,"Active_Power_B_Phase_4W",2,0.0001,24.0},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,16.0},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,14.0},{22,"Reactive_Power_B_Phase_4W",2,0.0001,19.0},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,28.0},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,26.0},{28,"Apparent_Power_B_Phase_4W",2,0.0001,31.0},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.0},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.0},{34,"Power_Factor_B_Phase_4W",2,0.001,0.0},{36,"Total_Active_Power",2,0.0001,68.0},{38,"Total_Reactive_Power",2,0.0001,50.0},{40,"Total_Apparent_Power",2,0.0001,85.0},{42,"Total_Power_Factor",2,0.001,0.0},{44,"Line_Frequency",2,0.01,0.0},{46,"Phase_Sequence",2,1,0.0}};
PARAM_DETAILS params2[40]={{512,"Cumulative_energy_forward_kVAh",2,0.01,0.0},{514,"Cumulative_energy_forward_kWh",2,0.01,0.0},{516,"Cumulative_energy_forward_kVArh_lag",2,0.01,0.0},{518,"Cumulative_energy_forward_kVArh_lead",2,0.01,0.0},{520,"Cumulative_energy_reverse_kVAh",2,0.01,0.0},{522,"Cumulative_energy_reverse_kWh",2,0.01,0.0},{524,"Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0},{526,"Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0},{528,"Backup_1_Cumulative_energy_forward_kVAh",2,0.01,0.0},{530,"Backup_1_Cumulative_energy_forward_kWh",2,0.01,0.0},{532,"Backup_1_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0},{534,"Backup_1_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0},{536,"Backup_1_Cumulative_energy_reverse_kVAh",2,0.01,0.0},{538,"Backup_1_Cumulative_energy_reverse_kWh",2,0.01,0.0},{540,"Backup_1_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0},{542,"Backup_1_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0},{544,"Backup_2_Cumulative_energy_forward_kVAh",2,0.01,0.0},{546,"Backup_2_Cumulative_energy_forward_kWh",2,0.01,0.0},{548,"Backup_2_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0},{550,"Backup_2_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0},{552,"Backup_2_Cumulative_energy_reverse_kVAh",2,0.01,0.0},{554,"Backup_2_Cumulative_energy_reverse_kWh",2,0.01,0.0},{556,"Backup_2_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0},{558,"Backup_2_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0},{560,"Backup_3_Cumulative_energy_forward_kVAh",2,0.01,0.0},{562,"Backup_3_Cumulative_energy_forward_kWh",2,0.01,0.0},{564,"Backup_3_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0},{566,"Backup_3_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0},{568,"Backup_3_Cumulative_energy_reverse_kVAh",2,0.01,0.0},{570,"Backup_3_Cumulative_energy_reverse_kWh",2,0.01,0.0},{572,"Backup_3_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0},{574,"Backup_3_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0},{576,"Backup_4_Cumulative_energy_forward_kVAh",2,0.01,0.0},{578,"Backup_4_Cumulative_energy_forward_kWh",2,0.01,0.0},{580,"Backup_4_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0},{582,"Backup_4_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0},{584,"Backup_4_Cumulative_energy_reverse_kVAh",2,0.01,0.0},{586,"Backup_4_Cumulative_energy_reverse_kWh",2,0.01,0.0},{588,"Backup_4_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0},{590,"Backup_4_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0}};
PARAM_DETAILS params3[44]={{1280,"Reset_MD1,2,0.01,0.0},{1282,Reset_MD2",2,0.01,0.0},{1284,"Reset_MD3",2,0.01,0.0},{1286,"Backup_1_MD1",2,0.01,0.0},{1288,"Backup_1_MD2",2,0.01,0.0},{1290,"Backup_1_MD3",2,0.01,0.0},{1292,"Backup_2_MD1",2,0.01,0.0},{1294,"Backup_2_MD2",2,0.01,0.0},{1296,"Backup_2_MD3",2,0.01,0.0},{1298,"Backup_3_MD1",2,0.01,0.0},{1300,"Backup_3_MD2",2,0.01,0.0},{1302,"Backup_3_MD3",2,0.01,0.0},{1304,"Backup_4_MD1",2,0.01,0.0},{1306,"Backup_4_MD2",2,0.01,0.0},{1308,"Backup_4_MD3",2,0.01,0.0},{1536,"Backup_1_reset_date_time_&_type",4,1,0.0},{1540,"Backup_2_reset_date_time_&_type",4,1,0.0},{1544,"Backup_3_reset_date_time_&_type",4,1,0.0},{1548,"Backup_4_reset_date_time_&_type",4,1,0.0},{1792,"Reset_Cumulative_MD1",2,0.01,0.0},{1794,"Reset_Cumulative_MD2",2,0.01,0.0},{1796,"Reset_Cumulative_MD3",2,0.01,0.0},{1872,"RD1_elapsed_time",3,0.01,0.0},{1875,"RD2_elapsed_time",3,0.01,0.0},{1878,"RD3_elapsed_time",3,0.01,0.0},{1920,"Anomaly_string_Format:_MSB_A_all_other_bytes_must_be_0xFF_Faults_are_indicated_by_digits_AENRXD_E–_flash_code_corruption_N_EEPROM_setup_corruption_R_RTC_corruption_XD_exception_illegal_opcode",8,1,0.0},{1928,"Reset_count_Format:_00_to_99_0x0063,1,1,0.0},{1929,Com.count_no._of_times_meter_programmed_via_front_panel_Format_00_to_99_0x0063",1,1,0.0},{1930,"CT_Tapping_0x0001_1A_tappin_0x0005_5A_tapping",1,1,0.0},{1931,"Reserved_READ_AND_WRITE_PARAMETERS_Real_time_clock_RTC",1,1,0.0},{256,"Current_Time_Year_Month_Format:YYMM_BCD",1,1,0.0},{257,"Current_Time_Date_Day_Format:DTDY_BCD",1,1,0.0},{258,"Current_Time_Hour_Minute_Format:HHMM_BCD",1,1,0.0},{259,"Current_Time_econd_Format:SS00_BCD",1,1,0.0},{2048,"CT_Primary",1,1,0.0},{2049,"CT_Secondary",1,1,0.0},{2050,"PT_Primary",1,1,0.0},{2051,"PT_Secondary",1,1,0.0},{2128,"Reset_type_days_and_Lockout_days_1st_word_Reset_time_HH_MM_hour_min_2nd_word",2,1,0.0},{2160,"Setting_for_MD1",2,1,0.0},{2162,"Setting_for_MD2",2,1,0.0},{2164,"Setting_for_MD3",2,1,0.0},{2304,"Method_of_energy_calculation_Lead=Lead_Lead=UPF",1,1,0.0},{2305,"Meter_direction_Unidirectional_Bidirectional",1,1,0.0}};


WORD Add_CRC(BYTE buf[], int len)
{
	WORD crc = 0xFFFF;
	int i=0,pos=0;


  	for(pos = 0; pos < len; pos++)
	{
    		crc ^= (WORD )buf[pos];          // XOR byte into least sig. byte of crc

    		for (i = 8; i != 0; i--)
		{    // Loop over each bit
      			if ((crc & 0x0001) != 0)
			{      // If the LSB is set
        			crc >>= 1;                    // Shift right and XOR 0xA001
        			crc ^= 0xA001;
      			}
      			else                            // Else LSB is not set
        			crc >>= 1;                    // Just shift right
    		}
  	}
  	// Note, this number has low and high bytes swapped, so use it accordingly (or swap bytes)
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

WORD LoWord(unsigned int val)
{
	return ((val<<16)>>16);
}

WORD HiWord(unsigned int val)
{
	return val>>16;
}

int compare_float(float f1, float f2)
 {
  float precision = F_PRECISION;

  if (((f1 - precision) < f2) && ((f1 + precision) > f2))
   {
    return TRUE;
   }
  else
   {
    return FALSE;
   }
 }


void reverse_b(BYTE *t_addr,BYTE *s_addr,int bcount)
{
    int i=0;

    for(i=0;i<bcount;i++)
    {
        t_addr[i]=s_addr[bcount-i-1];
    }
}

void switch_params(BYTE * pktdata, int pktcount)
{
//BYTE tdata[]={0x11,0x03,0x04,0xFF,0xFB,0xA5,0xD6,0xB0,0xD8};
//int tcount=9;


        switch(scondition)
        {
            case 0:
            if(random_mode)
            {
                rand_count+=3;//this will come around approximate updation time equal to rand_time
                if(rand_count > rand_time)
                {
                    rand_count=0;
                    Process_ParamsRand(pktdata,pktcount);
                }
            }
            else
            {
                Process_ParamsA(pktdata,pktcount);
                //Process_ParamsA(tdata,tcount); //testing current calculation
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
                        log_to_file(msg_to_log,strlen(msg_to_log));

                        if(pktdata[i]==0xFF)
                        {
                            neg_val.bval[0]=pktdata[i+3];//this is in reverse order due to endianness
                            neg_val.bval[1]=pktdata[i+2];
                            neg_val.wval=neg_val.wval-1;
                            neg_val.wval=~neg_val.wval;

                            cval=(float )(params1rand[db_id1].mf * neg_val.wval * -1);
                            //printf("\nNeg Value is %.2f\n",cval);
                            sprintf(msg_to_log,"Neg Value is %.2f",cval);
                            log_to_file(msg_to_log,strlen(msg_to_log));

                        }
                        else
                        {
                            reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                            cval=(float )(params1rand[db_id1].mf * mval);
                        }

                        tcval=0.0;
                        if(!compare_float(params1rand[db_id1].val,0.0))
                        {
                            tcval=(params1rand[db_id1].val-RANGEVAL) + (drand48() * ((params1rand[db_id1].val+RANGEVAL) - (params1rand[db_id1].val-RANGEVAL)));
                        }
                        sprintf(msg_to_log,"%s -> %.2f",params1rand[db_id1].pname,tcval);
                        log_to_file(msg_to_log,strlen(msg_to_log));
                        if(!compare_float(tcval,params1rand[db_id1].val))
                        {
                            memset(querry_msg,0x0,QUERRY_MAXSIZE);
                            sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map) VALUES (%d,%.2f,%d)",pktdata[0],tcval,params1rand[db_id1].addr_off);
                            onEINTR:
                            if (mysql_query(conn,querry_msg))
                            {
                                   if(errno == EINTR)
                                   {
                                        goto onEINTR;
                                   }
                                   sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                   log_to_file(msg_to_log,strlen(msg_to_log));
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


void Process_ParamsA(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0;
NVALUE neg_val;

                    db_id1=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        log_to_file(msg_to_log,strlen(msg_to_log));

                        if(pktdata[i]==0xFF)
                        {
                            neg_val.bval[0]=pktdata[i+3];//this is in reverse order due to endianness
                            neg_val.bval[1]=pktdata[i+2];
                            neg_val.wval=neg_val.wval-1;
                            neg_val.wval=~neg_val.wval;

                            cval=(float )(params1[db_id1].mf * neg_val.wval * -1);
                            //printf("\nNeg Value is %.2f\n",cval);
                            sprintf(msg_to_log,"Neg Value is %.2f",cval);
                            log_to_file(msg_to_log,strlen(msg_to_log));

                        }
                        else
                        {
                            reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                            cval=(float )(params1[db_id1].mf * mval);
                        }
                        sprintf(msg_to_log,"%s -> %.2f",params1[db_id1].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log));
                        //printf("\n%s -> %.2f : %.2f\n",params1[db_id1].pname,cval,params1[db_id1].val);
                        if(!compare_float(cval,params1[db_id1].val))
                        {
                            //printf("\n%s -> %.2f : %.2f\n",params1[db_id1].pname,cval,params1[db_id1].val);
                            params1[db_id1].val=cval;
                            memset(querry_msg,0x0,QUERRY_MAXSIZE);
                            //sprintf(querry_msg,"INSERT INTO data(device_id, data, address_map) VALUES (%d,%f,%d)",pktdata[0],cval,db_id1+1);
                            // INSERT INTO `data`(`device_id`, `data`, `address_map`) VALUES ([value-1],[value-2],[value-3])
                            sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map) VALUES (%d,%.2f,%d)",pktdata[0],cval,params1[db_id1].addr_off);
                            /*onEINTR:
                            if (mysql_query(conn,querry_msg))
                            {
                                   if(errno == EINTR)
                                   {
                                        goto onEINTR;
                                   }
                                   sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                   log_to_file(msg_to_log,strlen(msg_to_log));
                            }*/
                        }

                        i+=4;
                        db_id1++;
                        if(db_id1==MAXPARAMS_A)
                        {
                            break;
                        }


                    }


}

void Process_ParamsB(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0;


                    db_id2=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        log_to_file(msg_to_log,strlen(msg_to_log));
                        reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                        cval=(float )(params2[db_id2].mf * mval);
                        sprintf(msg_to_log,"%s -> %.2f",params2[db_id2].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log));
                        if(!compare_float(cval,params2[db_id2].val))
                        {
                            //printf("\n%s -> %.2f : %.2f\n",params2[db_id2].pname,cval,params2[db_id2].val);
                            params2[db_id2].val=cval;
                            //sprintf(querry_msg,"INSERT INTO data(device_id, data, address_map) VALUES (%d,%f,%d)",pktdata[0],cval,db_id2+1);
                            sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map) VALUES (%d,%.2f,%d)",pktdata[0],cval,params2[db_id2].addr_off);

                            /*onEINTR:
                            if (mysql_query(conn,querry_msg))
                            {
                                    if(errno == EINTR)
                                    {
                                        goto onEINTR;
                                    }
                                    sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                    log_to_file(msg_to_log,strlen(msg_to_log));
                            }*/

                        }

                        i+=4;
                        db_id2++;
                        if(db_id2==MAXPARAMS_B)
                        {
                            break;
                        }
                    }

}

void Process_ParamsC(BYTE * pktdata, int pktcount)
{
int i=0;
unsigned int mval=0;
float cval=0;


                    db_id3=0;

                    for(i=3;i<(pktcount-2);)
                    {
                        sprintf(msg_to_log,"%X %X %X %X",pktdata[i],pktdata[i+1],pktdata[i+2],pktdata[i+3]);
                        log_to_file(msg_to_log,strlen(msg_to_log));
                        reverse_b((BYTE *)&mval,(&pktdata[i]),4);
                        cval=(float )(params3[db_id3].mf * mval);
                        sprintf(msg_to_log,"%s -> %.2f",params3[db_id3].pname,cval);
                        log_to_file(msg_to_log,strlen(msg_to_log));
                        if(!compare_float(cval,params3[db_id3].val))
                        {
                            //printf("\n%s -> %.2f : %.2f\n",params3[db_id3].pname,cval,params3[db_id3].val);
                            params3[db_id3].val=cval;
                            //sprintf(querry_msg,"INSERT INTO data(device_id, data, address_map) VALUES (%d,%f,%d)",pktdata[0],cval,db_id3+1);
                            sprintf(querry_msg,"INSERT INTO data(device_id,data,address_map) VALUES (%d,%.2f,%d)",pktdata[0],cval,params3[db_id3].addr_off);

                            /*onEINTR:
                            if (mysql_query(conn,querry_msg))
                            {
                                    if(errno == EINTR)
                                    {
                                        goto onEINTR;
                                    }
                                    sprintf(msg_to_log,"Error entering database values : %s: %s",strerror(errno),querry_msg);
                                    log_to_file(msg_to_log,strlen(msg_to_log));
                            }*/

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
                        //32th one is reserved,skip//rest of the 12 values in manual is
                        //read/write params which is mixed between different sets of addresses.
                        if(db_id3==(MAXPARAMS_C-1))
                        {
                            break;

                        }
                    }

}
