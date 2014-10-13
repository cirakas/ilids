--
-- Table structure for table `address_map`
--

CREATE TABLE IF NOT EXISTS `address_map` (
  `id` bigint(50) NOT NULL AUTO_INCREMENT,
  `off_set` bigint(50) NOT NULL,
  `param_name` varchar(1000) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `word_length` bigint(50) NOT NULL,
  `multi_factor` double NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_OFFSET` (`off_set`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8mb4 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `alerts`
--

CREATE TABLE IF NOT EXISTS `alerts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(20) NOT NULL,
  `power` double NOT NULL,
  `time` datetime DEFAULT NULL,
  `delete` bit(1) DEFAULT b'0',
  `deleteFlag` bit(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `device_id_UNIQUE` (`device_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `charts`
--

CREATE TABLE IF NOT EXISTS `charts` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `chart_key` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `data`
--

CREATE TABLE IF NOT EXISTS `data` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `device_id` bigint(50) NOT NULL DEFAULT '0',
  `data` double NOT NULL,
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `address_map` bigint(20) NOT NULL,
  `category` int(3) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `data_UNIQUE` (`id`),
  KEY `device_id_UNIQUE` (`device_id`),
  KEY `address_map` (`address_map`),
  KEY `data_prod_time` (`time`),
  KEY `data_prod_idx` (`address_map`,`time`,`data`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `devices`
--

CREATE TABLE IF NOT EXISTS `devices` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `slave_id` bigint(50) NOT NULL DEFAULT '0',
  `created_date` date DEFAULT NULL,
  `device_zone` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `code_UNIQUE` (`slave_id`),
  KEY `FK5CF8ACDD678FAE3B` (`device_zone`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `device_zone`
--

CREATE TABLE IF NOT EXISTS `device_zone` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` varchar(255) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `exception_log`
--

CREATE TABLE IF NOT EXISTS `exception_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `exception_remark` varchar(255) DEFAULT NULL,
  `exception_type` varchar(255) DEFAULT NULL,
  `issue_caused_module` varchar(255) DEFAULT NULL,
  `issue_occured_time` datetime DEFAULT NULL,
  `user_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `mail_sms`
--

CREATE TABLE IF NOT EXISTS `mail_sms` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mail` varchar(255) NOT NULL,
  `sms` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `menu`
--

CREATE TABLE IF NOT EXISTS `menu` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(225) NOT NULL,
  `description` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE IF NOT EXISTS `notes` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `note` varchar(1000) DEFAULT NULL,
  `user` bigint(20) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user_Id` (`user`),
  KEY `FK6424EC11468C42F` (`user`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role`
--

CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `role_menu`
--

CREATE TABLE IF NOT EXISTS `role_menu` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `role_id` bigint(8) NOT NULL,
  `menu_id` bigint(8) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `IDX_ROLEID` (`role_id`),
  KEY `IDX_MENUID` (`menu_id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `system_settings`
--

CREATE TABLE IF NOT EXISTS `system_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mdv` double DEFAULT NULL,
  `rates_per_unit` double DEFAULT NULL,
  `time_zone` varchar(45) DEFAULT NULL,
  `system_clock` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `enabled` bit(1) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `username` varchar(255) NOT NULL,
  `role` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`),
  KEY `FK285FEB115EFB10` (`role`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Table structure for table `user_settings`
--

CREATE TABLE IF NOT EXISTS `user_settings` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user` bigint(20) DEFAULT NULL,
  `sent_mail` bit(1) DEFAULT b'0',
  `sent_sms` bit(1) DEFAULT b'0',
  `chart` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `user` (`user`),
  KEY `chart` (`chart`),
  KEY `FK588616171468C42F` (`user`),
  KEY `FK588616171161D1BA` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 AUTO_INCREMENT=1 ;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `alerts`
--
ALTER TABLE `alerts`
  ADD CONSTRAINT `FK_DEV_ALERT` FOREIGN KEY (`device_id`) REFERENCES `devices` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `data`
--
ALTER TABLE `data`
  ADD CONSTRAINT `FK_ADDRESS_MAP` FOREIGN KEY (`address_map`) REFERENCES `address_map` (`off_set`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  ADD CONSTRAINT `FK_DEV_DATA` FOREIGN KEY (`device_id`) REFERENCES `devices` (`slave_id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `devices`
--
ALTER TABLE `devices`
  ADD CONSTRAINT `FK5CF8ACDD678FAE3B` FOREIGN KEY (`device_zone`) REFERENCES `device_zone` (`id`);

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_3` FOREIGN KEY (`user`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;

--
-- Constraints for table `user`
--
ALTER TABLE `user`
  ADD CONSTRAINT `FK285FEB115EFB10` FOREIGN KEY (`role`) REFERENCES `role` (`id`);

--
-- Constraints for table `user_settings`
--
ALTER TABLE `user_settings`
  ADD CONSTRAINT `FK588616171161D1BA` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  ADD CONSTRAINT `FK_CHARTS_USER` FOREIGN KEY (`chart`) REFERENCES `charts` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
