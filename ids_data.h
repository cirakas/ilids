#include "ids_common.h"


#define Read_Output_Register 03
#define Preset_Single_Register 06
#define Preset_Multiple_Registers 16

#define MAX_PARAMS 79

void prepare_slave_data(BYTE *inbuf,int inlen);
void process_master_data(BYTE *inbuf,int inlen);
int Check_CRC(BYTE * pktdata, int pktcount);
WORD Add_CRC(BYTE buf[], int len);
BYTE LoByte(WORD val);
BYTE HiByte(WORD val);
WORD LoWord(unsigned int val);
WORD HiWord(unsigned int val);
void make_val(BYTE * inval,int val);
void reverse_b(BYTE *t_addr,BYTE *s_addr,int bcount);


typedef struct
{
    WORD p_addr;
    char * p_name;
    WORD nwords;
    float mf;
    float p_val;
    float offset;
}PARAM_DETAILS;

BYTE out_buf[MAXSIZE];

PARAM_DETAILS param_list[MAX_PARAMS]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,232.73,5},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,236.69,5},\
{4,"B_Phase_Voltage_4W",2,0.01,236.83,5},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,220.68,10},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,253.47,10},\
{10,"B_Phase_Current_4W",2,0.001,229.11,10},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,49.13,5},\
{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,54.57,5},{16,"Active_Power_B_Phase_4W",2,0.0001,50.47,5},\
{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,24.09,5},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,24.66,5},\
{22,"Reactive_Power_B_Phase_4W",2,0.0001,22.5,5},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,54.72,5},\
{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,59.97,5},{28,"Apparent_Power_B_Phase_4W",2,0.0001,56.59,5},\
{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.70,0.05},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.80,0.05},\
{34,"Power_Factor_B_Phase_4W",2,0.001,0.90,0.05},{36,"Total_Active_Power",2,0.0001,149.39,5},{38,"Total_Reactive_Power",2,0.0001,73.83,5},\
{40,"Total_Apparent_Power",2,0.0001,164.75,5},{42,"Total_Power_Factor",2,0.001,0.0,5},{44,"Line_Frequency",2,0.01,0.0,5},{46,"Phase_Sequence",2,1,0.0,5},\
{512,"Cumulative_energy_forward_kVAh",2,0.01,2942067.75,5},{514,"Cumulative_energy_forward_kWh",2,0.01,215155.39,5},{516,"Cumulative_energy_forward_kVArh_lag",2,0.01,7868760,5},\
{518,"Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,5},{520,"Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{522,"Cumulative_energy_reverse_kWh",2,0.01,0.0,5},\
{524,"Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{526,"Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},\
{528,"Backup_1_Cumulative_energy_forward_kVAh",2,0.01,2837265.5,5},{530,"Backup_1_Cumulative_energy_forward_kWh",2,0.01,123174.8,5},\
{532,"Backup_1_Cumulative_energy_forward_kVArh_lag",2,0.01,7818694.5,5},{534,"Backup_1_Cumulative_energy_forward_kVArh_lead",2,0.01,82.1,5},\
{536,"Backup_1_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{538,"Backup_1_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},\
{540,"Backup_1_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{542,"Backup_1_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},\
{544,"Backup_2_Cumulative_energy_forward_kVAh",2,0.01,2737930.25,5},{546,"Backup_2_Cumulative_energy_forward_kWh",2,0.01,36146.1,5},\
{548,"Backup_2_Cumulative_energy_forward_kVArh_lag",2,0.01,7771083.5,5},{550,"Backup_2_Cumulative_energy_forward_kVArh_lead",2,0.01,81.6,5},\
{552,"Backup_2_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{554,"Backup_2_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},\
{556,"Backup_2_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{558,"Backup_2_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},\
{560,"Backup_3_Cumulative_energy_forward_kVAh",2,0.01,2634013.25,5},{562,"Backup_3_Cumulative_energy_forward_kWh",2,0.01,9945701,5},\
{564,"Backup_3_Cumulative_energy_forward_kVArh_lag",2,0.01,7720155,5},{566,"Backup_3_Cumulative_energy_forward_kVArh_lead",2,0.01,81.5,5},\
{568,"Backup_3_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{570,"Backup_3_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},\
{572,"Backup_3_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{574,"Backup_3_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},\
{576,"Backup_4_Cumulative_energy_forward_kVAh",2,0.01,2535317.75,5},{578,"Backup_4_Cumulative_energy_forward_kWh",2,0.01,9859798,5},\
{580,"Backup_4_Cumulative_energy_forward_kVArh_lag",2,0.01,7671777.5,5},{582,"Backup_4_Cumulative_energy_forward_kVArh_lead",2,0.01,81.2,5},\
{584,"Backup_4_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{586,"Backup_4_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},\
{588,"Backup_4_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{590,"Backup_4_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},\
{1280,"Reset_MD1",2,0.01,341.85,5},{1282,"Reset_MD2",2,0.01,301.98,5},{1284,"Reset_MD3",2,0.01,160.83,5},{1286,"Backup_1_MD1",2,0.01,179.31,5},\
{1288,"Backup_1_MD2",2,0.01,156.85,5},{1290,"Backup_1_MD3",2,0.01,95.37,5},{1292,"Backup_2_MD1",2,0.01,178.18,5},{1294,"Backup_2_MD2",2,0.01,155.16,5},\
{1296,"Backup_2_MD3",2,0.01,88.5,5},{1298,"Backup_3_MD1",2,0.01,326.65,5},{1300,"Backup_3_MD2",2,0.01,289.35,5},{1302,"Backup_3_MD3",2,0.01,155.54,5},\
{1304,"Backup_4_MD1",2,0.01,296.82,5},{1306,"Backup_4_MD2",2,0.01,256.26,5},{1308,"Backup_4_MD3",2,0.01,151.55,5}};


/*{1872,"RD1_elapsed_time",3,0.01,0.0,5},{1875,"RD2_elapsed_time",3,0.01,0.0,5},{1878,"RD3_elapsed_time",3,0.01,0.0,5},\
{1920,"Anomaly_string_Format:_MSB_A_all_other_bytes_must_be_0xFF_Faults_are_indicated_by_digits_AENRXD_E–_flash_code_corruption_\
N_EEPROM_setup_corruption_R_RTC_corruption_XD_exception_illegal_opcode",8,1,0.0,5},{1928,"Reset_count_Format:_00_to_99_0x0063",1,1,0.0,5},\
{1929,"Com.count_no._of_times_meter_programmed_via_front_panel_Format_00_to_99_0x0063",1,1,0.0,5},\
{1930,"CT_Tapping_0x0001_1A_tappin_0x0005_5A_tapping",1,1,0.0,5},{1931,"Reserved",1,1,0.0,5},\
{256,"Current_Time_Year_Month_Format:YYMM_BCD",1,1,0.0,5},{257,"Current_Time_Date_Day_Format:DTDY_BCD",1,1,0.0,5},\
{258,"Current_Time_Hour_Minute_Format:HHMM_BCD",1,1,0.0,5},{259,"Current_Time_Second_Format:SS00_BCD",1,1,0.0,5},{2048,"CT_Primary",1,1,0.0,5},\
{2049,"CT_Secondary",1,1,0.0,5},{2050,"PT_Primary",1,1,0.0,5},{2051,"PT_Secondary",1,1,0.0,5},{2128,"Reset_type_days_and_Lockout_days_1st_word_Reset_time_\
HH_MM_hour_min_2nd_word",2,1,0.0,5},{2160,"Setting_for_MD1",2,1,0.0,5},{2162,"Setting_for_MD2",2,1,0.0,5},{2164,"Setting_for_MD3",2,1,0.0,5},\
{2304,"Method_of_energy_calculation_Lead_Lead_Lead_UPF",1,1,0.0,5},{2305,"Meter_direction_Unidirectional_Bidirectional",1,1,0.0,5}};*/

/*PARAM_DETAILS params1[24]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,0.0,5},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,0.0,5},{4,"B_Phase_Voltage_4W",2,0.01,0.0,5},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,0.0,10},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,0.0,10},{10,"B_Phase_Current_4W",2,0.001,0.0,10},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,0.0,5},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,0.0,5},{16,"Active_Power_B_Phase_4W",2,0.0001,0.0,5},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,0.0,5},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,0.0,5},{22,"Reactive_Power_B_Phase_4W",2,0.0001,0.0,5},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,0.0,5},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,0.0,5},{28,"Apparent_Power_B_Phase_4W",2,0.0001,0.0,5},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.0,0.1},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.0,0.1},{34,"Power_Factor_B_Phase_4W",2,0.001,0.0,0.1},{36,"Total_Active_Power",2,0.0001,0.0,5},{38,"Total_Reactive_Power",2,0.0001,0.0,5},{40,"Total_Apparent_Power",2,0.0001,0.0,5},{42,"Total_Power_Factor",2,0.001,0.0,5},{44,"Line_Frequency",2,0.01,0.0,5},{46,"Phase_Sequence",2,1,0.0,5}};
PARAM_DETAILS params1rand[24]={{0,"R_Phase_Voltage_4W_RY_Voltage_3W",2,0.01,227.0,5},{2,"Y_Phase_Voltage_4W_BY_Voltage_3W",2,0.01,236.0,5},{4,"B_Phase_Voltage_4W",2,0.01,239.0,5},{6,"R_Phase_Current_4W_R_Current_3W",2,0.001,119.0,10},{8,"Y_Phase_Current_4W_B_Current_3W",2,0.001,103.0,10},{10,"B_Phase_Current_4W",2,0.001,123.0,10},{12,"Active_Power_R_Phase_4W_RY_Active_Power_3W",2,0.0001,22.0,5},{14,"Active_Power_Y_Phase_4W_BY_Active_Power_3W",2,0.0001,21.0,5},{16,"Active_Power_B_Phase_4W",2,0.0001,24.0,5},{18,"Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W",2,0.0001,16.0,5},{20,"Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W",2,0.0001,14.0,5},{22,"Reactive_Power_B_Phase_4W",2,0.0001,19.0,5},{24,"Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W",2,0.0001,28.0,5},{26,"Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W",2,0.0001,26.0,5},{28,"Apparent_Power_B_Phase_4W",2,0.0001,31.0,5},{30,"Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W",2,0.001,0.5,0.1},{32,"Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W",2,0.001,0.5,0.1},{34,"Power_Factor_B_Phase_4W",2,0.001,0.5,0.1},{36,"Total_Active_Power",2,0.0001,68.0,5},{38,"Total_Reactive_Power",2,0.0001,50.0,5},{40,"Total_Apparent_Power",2,0.0001,85.0,5},{42,"Total_Power_Factor",2,0.001,0.0,5},{44,"Line_Frequency",2,0.01,0.0,5},{46,"Phase_Sequence",2,1,0.0,5}};
PARAM_DETAILS params2[40]={{512,"Cumulative_energy_forward_kVAh",2,0.01,0.0,5},{514,"Cumulative_energy_forward_kWh",2,0.01,0.0,5},{516,"Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,5},{518,"Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,5},{520,"Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{522,"Cumulative_energy_reverse_kWh",2,0.01,0.0,5},{524,"Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{526,"Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},{528,"Backup_1_Cumulative_energy_forward_kVAh",2,0.01,0.0,5},{530,"Backup_1_Cumulative_energy_forward_kWh",2,0.01,0.0,5},{532,"Backup_1_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,5},{534,"Backup_1_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,5},{536,"Backup_1_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{538,"Backup_1_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},{540,"Backup_1_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{542,"Backup_1_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},{544,"Backup_2_Cumulative_energy_forward_kVAh",2,0.01,0.0,5},{546,"Backup_2_Cumulative_energy_forward_kWh",2,0.01,0.0,5},{548,"Backup_2_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,5},{550,"Backup_2_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,5},{552,"Backup_2_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{554,"Backup_2_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},{556,"Backup_2_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{558,"Backup_2_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},{560,"Backup_3_Cumulative_energy_forward_kVAh",2,0.01,0.0,5},{562,"Backup_3_Cumulative_energy_forward_kWh",2,0.01,0.0,5},{564,"Backup_3_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,5},{566,"Backup_3_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,5},{568,"Backup_3_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{570,"Backup_3_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},{572,"Backup_3_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{574,"Backup_3_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5},{576,"Backup_4_Cumulative_energy_forward_kVAh",2,0.01,0.0,5},{578,"Backup_4_Cumulative_energy_forward_kWh",2,0.01,0.0,5},{580,"Backup_4_Cumulative_energy_forward_kVArh_lag",2,0.01,0.0,5},{582,"Backup_4_Cumulative_energy_forward_kVArh_lead",2,0.01,0.0,5},{584,"Backup_4_Cumulative_energy_reverse_kVAh",2,0.01,0.0,5},{586,"Backup_4_Cumulative_energy_reverse_kWh",2,0.01,0.0,5},{588,"Backup_4_Cumulative_energy_reverse_kVArh_lag",2,0.01,0.0,5},{590,"Backup_4_Cumulative_energy_reverse_kVArh_lead",2,0.01,0.0,5}};
PARAM_DETAILS params3[44]={{1280,"Reset_MD1",2,0.01,0.0,5},{1282,"Reset_MD2",2,0.01,0.0,5},{1284,"Reset_MD3",2,0.01,0.0,5},{1286,"Backup_1_MD1",2,0.01,0.0,5},{1288,"Backup_1_MD2",2,0.01,0.0,5},{1290,"Backup_1_MD3",2,0.01,0.0,5},{1292,"Backup_2_MD1",2,0.01,0.0,5},{1294,"Backup_2_MD2",2,0.01,0.0,5},{1296,"Backup_2_MD3",2,0.01,0.0,5},{1298,"Backup_3_MD1",2,0.01,0.0,5},{1300,"Backup_3_MD2",2,0.01,0.0,5},{1302,"Backup_3_MD3",2,0.01,0.0,5},{1304,"Backup_4_MD1",2,0.01,0.0,5},{1306,"Backup_4_MD2",2,0.01,0.0,5},{1308,"Backup_4_MD3",2,0.01,0.0,5},{1536,"Backup_1_reset_date_time_&_type",4,1,0.0,5},{1540,"Backup_2_reset_date_time_&_type",4,1,0.0,5},{1544,"Backup_3_reset_date_time_&_type",4,1,0.0,5},{1548,"Backup_4_reset_date_time_&_type",4,1,0.0,5},{1792,"Reset_Cumulative_MD1",2,0.01,0.0,5},{1794,"Reset_Cumulative_MD2",2,0.01,0.0,5},{1796,"Reset_Cumulative_MD3",2,0.01,0.0,5},{1872,"RD1_elapsed_time",3,0.01,0.0,5},{1875,"RD2_elapsed_time",3,0.01,0.0,5},{1878,"RD3_elapsed_time",3,0.01,0.0,5},{1920,"Anomaly_string_Format:_MSB_A_all_other_bytes_must_be_0xFF_Faults_are_indicated_by_digits_AENRXD_E–_flash_code_corruption_N_EEPROM_setup_corruption_R_RTC_corruption_XD_exception_illegal_opcode",8,1,0.0,5},{1928,"Reset_count_Format:_00_to_99_0x0063",1,1,0.0,5},{1929,"Com.count_no._of_times_meter_programmed_via_front_panel_Format_00_to_99_0x0063",1,1,0.0,5},{1930,"CT_Tapping_0x0001_1A_tappin_0x0005_5A_tapping",1,1,0.0,5},{1931,"Reserved_READ_AND_WRITE_PARAMETERS_Real_time_clock_RTC",1,1,0.0,5},{256,"Current_Time_Year_Month_Format:YYMM_BCD",1,1,0.0,5},{257,"Current_Time_Date_Day_Format:DTDY_BCD",1,1,0.0,5},{258,"Current_Time_Hour_Minute_Format:HHMM_BCD",1,1,0.0,5},{259,"Current_Time_econd_Format:SS00_BCD",1,1,0.0,5},{2048,"CT_Primary",1,1,0.0,5},{2049,"CT_Secondary",1,1,0.0,5},{2050,"PT_Primary",1,1,0.0,5},{2051,"PT_Secondary",1,1,0.0,5},{2128,"Reset_type_days_and_Lockout_days_1st_word_Reset_time_HH_MM_hour_min_2nd_word",2,1,0.0,5},{2160,"Setting_for_MD1",2,1,0.0,5},{2162,"Setting_for_MD2",2,1,0.0,5},{2164,"Setting_for_MD3",2,1,0.0,5},{2304,"Method_of_energy_calculation_Lead=Lead_Lead=UPF",1,1,0.0,5},{2305,"Meter_direction_Unidirectional_Bidirectional",1,1,0.0,5}};*/
