
--
-- Dumping data for table `address_map`
--

INSERT INTO `address_map` (`id`, `off_set`, `param_name`, `name`, `word_length`, `multi_factor`) VALUES
(1, 0, 'R_Phase_Voltage_4W_RY_Voltage_3W', 'Phase1 Voltage', 2, 0.01),
(2, 2, 'Y_Phase_Voltage_4W_BY_Voltage_3W', 'Phase2 Voltage', 2, 0.01),
(3, 4, 'B_Phase_Voltage_4W', 'Phase3 Voltage', 2, 0.01),
(4, 6, 'R_Phase_Current_4W_R_Current_3W', 'Phase1 Current', 2, 0.001),
(5, 8, 'Y_Phase_Current_4W_B_Current_3W', 'Phase2 Current', 2, 0.001),
(6, 10, 'B_Phase_Current_4W', 'Phase3 Current', 2, 0.001),
(7, 12, 'Active_Power_R_Phase_4W_RY_Active_Power_3W', 'Phase1 Active Power', 2, 0.0001),
(8, 14, 'Active_Power_Y_Phase_4W_BY_Active_Power_3W', 'Phase2 Active Power', 2, 0.0001),
(9, 16, 'Active_Power_B_Phase_4W', 'Phase3 Active Power', 2, 0.0001),
(10, 18, 'Reactive_Power_R_Phase_4W_RY_Reactive_Power_3W', 'Phase1 Reactive Power', 2, 0.0001),
(11, 20, 'Reactive_Power_Y_Phase_4W_BY_Reactive_Power_3W', 'Phase2 Reactive Power', 2, 0.0001),
(12, 22, 'Reactive_Power_B_Phase_4W', 'Phase3 Reactive Power', 2, 0.0001),
(13, 24, 'Apparent_Power_R_Phase_4W_RY_Apparent_Power_3W', 'Phase1 Apparent Power', 2, 0.0001),
(14, 26, 'Apparent_Power_Y_Phase_4W_BY_Apparent_Power_3W', 'Phase2 Apparent Power', 2, 0.0001),
(15, 28, 'Apparent_Power_B_Phase_4W', 'Phase3 Apparent Power', 2, 0.0001),
(16, 30, 'Power_Factor_R_Phase_4W_Power_Factor_R_Phase_3W', 'Phase1 Power Factor', 2, 0.001),
(17, 32, 'Power_Factor_Y_Phase_4W_Power_Factor_B_Phase_3W', 'Phase2 Power Factor', 2, 0.001),
(18, 34, 'Power_Factor_B_Phase_4W', 'Phase3 Power Factor', 2, 0.001),
(19, 36, 'Total_Active_Power', 'Total Active Power', 2, 0.0001),
(20, 38, 'Total_Reactive_Power', 'Total Reactive Power', 2, 0.0001),
(21, 40, 'Total_Apparent_Power', 'Total Apparent Power', 2, 0.0001),
(22, 42, 'Total_Power_Factor', 'Total Power Factor', 2, 0.001),
(23, 44, 'Line_Frequency', 'Line Frequency', 2, 0.01),
(24, 46, 'Phase_Sequence', 'Phase Sequence', 2, 1),
(25, 514, 'Cumulative_energy_forward_kWh', 'Cumulative Energy Forward kWh', 2, 0.01),
(26, 516, 'Cumulative_energy_forward_kVArh_lag', 'Cumulative Energy Forward kVArh_lag', 2, 0.01),
(27, 518, 'Cumulative_energy_forward_kVArh_lead', 'Cumulative Energy Forward kVArh_lead', 2, 0.01),
(28, 520, 'Cumulative_energy_reverse_kVAh', 'Cumulative Energy Reverse kVAh', 2, 0.01),
(29, 522, 'Cumulative_energy_reverse_kWh', 'Cumulative Energy Reverse kWh', 2, 0.01),
(30, 524, 'Cumulative_energy_reverse_kVArh_lag', 'Cumulative Energy Reverse kVArh_lag', 2, 0.01),
(31, 526, 'Cumulative_energy_reverse_kVArh_lead', NULL, 2, 0.01),
(32, 528, 'Backup_1_Cumulative_energy_forward_kVAh', NULL, 2, 0.01),
(33, 530, 'Backup_1_Cumulative_energy_forward_kWh', NULL, 2, 0.01),
(34, 532, 'Backup_1_Cumulative_energy_forward_kVArh_lag', NULL, 2, 0.01),
(35, 534, 'Backup_1_Cumulative_energy_forward_kVArh_lead', NULL, 2, 0.01),
(36, 536, 'Backup_1_Cumulative_energy_reverse_kVAh', NULL, 2, 0.01),
(37, 538, 'Backup_1_Cumulative_energy_reverse_kWh', NULL, 2, 0.01),
(38, 540, 'Backup_1_Cumulative_energy_reverse_kVArh_lag', NULL, 2, 0.01),
(39, 542, 'Backup_1_Cumulative_energy_reverse_kVArh_lead', NULL, 2, 0.01),
(40, 544, 'Backup_2_Cumulative_energy_forward_kVAh', NULL, 2, 0.01),
(41, 546, 'Backup_2_Cumulative_energy_forward_kWh', NULL, 2, 0.01),
(42, 548, 'Backup_2_Cumulative_energy_forward_kVArh_lag', NULL, 2, 0.01),
(43, 550, 'Backup_2_Cumulative_energy_forward_kVArh_lead', NULL, 2, 0.01),
(44, 552, 'Backup_2_Cumulative_energy_reverse_kVAh', NULL, 2, 0.01),
(45, 554, 'Backup_2_Cumulative_energy_reverse_kWh', NULL, 2, 0.01),
(46, 556, 'Backup_2_Cumulative_energy_reverse_kVArh_lag', NULL, 2, 0.01),
(47, 558, 'Backup_2_Cumulative_energy_reverse_kVArh_lead', NULL, 2, 0.01),
(48, 560, 'Backup_3_Cumulative_energy_forward_kVAh', NULL, 2, 0.01),
(49, 562, 'Backup_3_Cumulative_energy_forward_kWh', NULL, 2, 0.01),
(50, 564, 'Backup_3_Cumulative_energy_forward_kVArh_lag', NULL, 2, 0.01),
(51, 566, 'Backup_3_Cumulative_energy_forward_kVArh_lead', NULL, 2, 0.01),
(52, 568, 'Backup_3_Cumulative_energy_reverse_kVAh', NULL, 2, 0.01),
(53, 570, 'Backup_3_Cumulative_energy_reverse_kWh', NULL, 2, 0.01),
(54, 572, 'Backup_3_Cumulative_energy_reverse_kVArh_lag', NULL, 2, 0.01),
(55, 574, 'Backup_3_Cumulative_energy_reverse_kVArh_lead', NULL, 2, 0.01),
(56, 576, 'Backup_4_Cumulative_energy_forward_kVAh', NULL, 2, 0.01),
(57, 578, 'Backup_4_Cumulative_energy_forward_kWh', NULL, 2, 0.01),
(58, 580, 'Backup_4_Cumulative_energy_forward_kVArh_lag', NULL, 2, 0.01),
(59, 582, 'Backup_4_Cumulative_energy_forward_kVArh_lead', NULL, 2, 0.01),
(60, 584, 'Backup_4_Cumulative_energy_reverse_kVAh', NULL, 2, 0.01),
(61, 586, 'Backup_4_Cumulative_energy_reverse_kWh', NULL, 2, 0.01),
(62, 588, 'Backup_4_Cumulative_energy_reverse_kVArh_lag', NULL, 2, 0.01),
(63, 590, 'Backup_4_Cumulative_energy_reverse_kVArh_lead', NULL, 2, 0.01),
(64, 1280, 'Reset_MD1', NULL, 2, 0.01),
(65, 1282, 'Reset_MD2', NULL, 2, 0.01),
(66, 1284, 'Reset_MD3', NULL, 2, 0.01),
(67, 1286, 'Backup_1_MD1', NULL, 2, 0.01),
(68, 1288, 'Backup_1_MD2', NULL, 2, 0.01),
(69, 1290, 'Backup_1_MD3', NULL, 2, 0.01),
(70, 1292, 'Backup_2_MD1', NULL, 2, 0.01),
(71, 1294, 'Backup_2_MD2', NULL, 2, 0.01),
(72, 1296, 'Backup_2_MD3', NULL, 2, 0.01),
(73, 1298, 'Backup_3_MD1', NULL, 2, 0.01),
(74, 1300, 'Backup_3_MD2', NULL, 2, 0.01),
(75, 1302, 'Backup_3_MD3', NULL, 2, 0.01),
(76, 1304, 'Backup_4_MD1', NULL, 2, 0.01),
(77, 1306, 'Backup_4_MD2', NULL, 2, 0.01),
(78, 1308, 'Backup_4_MD3', NULL, 2, 0.01),
(79, 1536, 'Backup_1_reset_date_time_&_type', NULL, 4, 1),
(80, 1540, 'Backup_2_reset_date_time_&_type', NULL, 4, 1),
(81, 1544, 'Backup_3_reset_date_time_&_type', NULL, 4, 1),
(82, 1548, 'Backup_4_reset_date_time_&_type', NULL, 4, 1),
(83, 1792, 'Reset_Cumulative_MD1', NULL, 2, 0.01),
(84, 1794, 'Reset_Cumulative_MD2', NULL, 2, 0.01),
(85, 1796, 'Reset_Cumulative_MD3', NULL, 2, 0.01),
(86, 1872, 'RD1_elapsed_time', NULL, 3, 0.01),
(87, 1875, 'RD2_elapsed_time', NULL, 3, 0.01),
(88, 1878, 'RD3_elapsed_time', NULL, 3, 0.01),
(89, 1920, 'Anomaly_string_Format:_MSB_A_all_other_bytes_must_be_0xFF_Faults_are_indicated_by_digits_AENRXD_E–_flash_code_corruption_N_EEPROM_setup_corruption_R_RTC_corruption_XD_exception_illegal_opcode', NULL, 8, 1),
(90, 1928, 'Reset_count_Format:_00_to_99_0x0063', NULL, 1, 1),
(91, 1929, 'Com.count_no._of_times_meter_programmed_via_front_panel_Format_00_to_99_0x0063', NULL, 1, 1),
(92, 1930, 'CT_Tapping_0x0001_1A_tappin_0x0005_5A_tapping', NULL, 1, 1),
(93, 1931, 'Reserved_READ_AND_WRITE_PARAMETERS_Real_time_clock_RTC', NULL, 1, 1),
(94, 256, 'Current_Time_Year_Month_Format:YYMM_BCD', NULL, 1, 1),
(95, 257, 'Current_Time_Date_Day_Format:DTDY_BCD', NULL, 1, 1),
(96, 258, 'Current_Time_Hour_Minute_Format:HHMM_BCD', NULL, 1, 1),
(97, 259, 'Current_Time_econd_Format:SS00_BCD', NULL, 1, 1),
(98, 2048, 'CT_Primary', NULL, 1, 1),
(99, 2049, 'CT_Secondary', NULL, 1, 1),
(100, 2050, 'PT_Primary', NULL, 1, 1),
(101, 2051, 'PT_Secondary', NULL, 1, 1),
(102, 2128, 'Reset_type_days_and_Lockout_days_1st_word_Reset_time_HH_MM_hour_min_2nd_word', NULL, 2, 1),
(103, 2160, 'Setting_for_MD1', NULL, 2, 1),
(104, 2162, 'Setting_for_MD2', NULL, 2, 1),
(105, 2164, 'Setting_for_MD3', NULL, 2, 1),
(106, 2304, 'Method_of_energy_calculation_Lead=Lead_Lead=UPF', NULL, 1, 1),
(107, 2305, 'Meter_direction_Unidirectional_Bidirectional', NULL, 1, 1),
(108, 512, 'Cumulative_energy_forward_kVAh', NULL, 2, 0.01);

--
-- Dumping data for table `menu`
--

INSERT INTO `menu` (`id`, `name`, `description`) VALUES
(1, 'User Settings', NULL),
(2, 'System Settings', NULL),
(3, 'Devices', NULL),
(4, 'Charts', NULL),
(5, 'Alerts', 'Alert description'),
(6, 'Email/SMS Settings', NULL),
(7, 'Notes', NULL),
(8, 'Live Chat', NULL),
(9, 'Roles', NULL),
(10, 'Devize Zone', NULL);


--
-- Dumping data for table `role`
--

INSERT INTO `role` (`id`, `name`, `description`) VALUES
(1, 'ROLE_ADMIN', 'role description');

--
-- Dumping data for table `role_menu`
--

INSERT INTO `role_menu` (`id`, `role_id`, `menu_id`) VALUES
(1, 1, 1),
(2, 1, 2),
(3, 1, 3),
(4, 1, 4),
(5, 1, 5),
(6, 1, 6),
(7, 1, 7),
(8, 1, 8),
(9, 1, 9),
(10, 1, 10);

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `email`, `enabled`, `name`, `password`, `username`, `role`) VALUES
(1, 'admin@cirakas.com', b'1', 'admin', '8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918', 'admin', 1);


--
-- Dumping data for table `system_settings`
--

INSERT INTO `system_settings` (`id`, `mdv`, `rates_per_unit`, `time_zone`, `system_clock`) VALUES
(1, 45, 2.5, 'time-zone', '12/04/2007');