CREATE DATABASE IF NOT EXISTS `zakupki`;

CREATE TABLE IF NOT EXISTS `zakupki`.`purchase_plan` ( 
  `guid` varchar(36) not null,
  `customer_inn` varchar(12) null,
  `placer_inn` varchar(12) not null,
  `plan_type` varchar(10) null,
  `is_upload_complete` tinyint(1) null,
  `create_date_time` datetime null,
  `url_eis` varchar(3000) null,
  `url_vsrz` varchar(3000) null,
  `url_kis_rmis` varchar(3000) null,
  `registration_number` bigint null,
  `name` varchar(2000) null,
  `additional_info` varchar(2000) null,
  `start_date` date not null,
  `end_date` date not null,
  `approve_date` date not null,
  `publication_date_time` datetime null,
  `is_digit_form` tinyint(1) null,
  `summ_size_ch15` tinyint(1) null,
  `is_imported_from_vsrz` tinyint(1) null,
  `status_code` varchar(1) null,
  `version` int null,
  `modification_description` varchar(2000) null,
  `use_new_classifiers` tinyint(1) null,
  `exclude_volume` decimal(22, 2) null,
  `volume_smb` decimal(22, 2) null,
  `annual_volume` decimal(22, 2) null,
  `percent_smb` int null,
  `smb_partition_changed` tinyint(1) null,
  `annual_volume_smb_less_18_percent` tinyint(1) null,
  `reporting_year` int not null,
  `previous_year_annual_volume` decimal(22, 2) null,
  `previous_year_annual_volume_hitech` decimal(22, 2) null,
  `previous_year_annual_volume_hitech_smb` decimal(22, 2) null,
  `annual_year_annual_volume_hitech_summ` decimal(22, 2) null,
  `annual_year_annual_volume_hitech_increase` decimal(22, 2) null,
  `annual_year_annual_volume_hitech_percent` decimal(22, 2) null,
  `annual_year_annual_volume_hitech_smb_summ` decimal(22, 2) null,
  `annual_year_annual_volume_hitech_smb_increase` decimal(22, 2) null,
  `annual_year_annual_volume_hitech_smb_percent` decimal(22, 2) null,
  PRIMARY KEY (`guid`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`customer` ( 
  `inn` varchar(12) not null,
  `full_name` varchar(1000) null,
  `short_name` varchar(500) null,
  `iko` varchar(22) null,
  `kpp` varchar(9) not null,
  `ogrn` varchar(13) not null,
  `legal_address` varchar(2000) null,
  `postal_address` varchar(2000) null,
  `phone` varchar(300) null,
  `fax` varchar(300) null,
  `email` varchar(300) null,
  `okato` varchar(11) null,
  `okopf_code` varchar(5) null,
  `okpo` varchar(8) null,
  `customer_registration_date` datetime null,
  `timezone_offset` int null,
  `timezone_name` varchar(100) null,
  `region` varchar(200) null,
  `customer_assessed_compliance` tinyint(1) null,
  `customer_monitored_compliance` tinyint(1) null,
  PRIMARY KEY (`inn`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`okopf` ( 
  `code` varchar(5) not null,
  `name` varchar(200) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`purchase_plan_item` ( 
  `guid` varchar(36) not null,
  `notice_info_guid` varchar(36) null,
  `lot_guid` varchar(36) null,
  `okato` varchar(11) null,
  `region` varchar(1000) null,
  `is_general_address` tinyint(1) null,
  `purchase_method_code` int null,
  `purchase_method_name` varchar(2000) null,
  `is_electronic` tinyint(1) null,
  `planned_after_second_year` tinyint(1) null,
  `is_purchase_ignored` tinyint(1) null,
  `purchase_period_year` int null,
  PRIMARY KEY (`guid`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`plan_item_status` ( 
  `code` varchar(1) not null,
  `name` varchar(15) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`long_term_volumes` ( 
  `plan_item_guid` varchar(36) not null,
  `is_smb` tinyint(1) not null,
  `volume` decimal(22, 2) unsigned null,
  `volume_rub` decimal(22, 2) null,
  `currency_code` varchar(3) null,
  `exchange_rate` decimal(16, 6) null,
  `exchange_rate_date` date null,
  PRIMARY KEY (`plan_item_guid`, `is_smb`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`long_term_volume_detail` ( 
  `long_term_value_guid` varchar(36) not null,
  `year` int not null,
  `summ` decimal(22, 2) unsigned null,
  `summ_rub` decimal(22, 2) unsigned null,
  PRIMARY KEY (`long_term_value_guid`, `year`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`innovation_plan_item` ( 
  `guid` varchar(36) not null,
  `ignored_purchase` tinyint(1) null,
  `purchase_period_year` int not null,
  PRIMARY KEY (`guid`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`currency` ( 
  `code` varchar(3) not null,
  `digital_code` varchar(3) not null,
  `name` varchar(50) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`innovation_plan_item_row` ( 
  `plan_item_guid` varchar(36) not null,
  `ordinal_number` int not null,
  `additional_info` varchar(2000) null,
  `okdp_code` varchar(20) null,
  `okdp_name` varchar(550) null,
  `okpd2_code` varchar(20) null,
  `okpd2_name` varchar(550) null,
  `okved_code` varchar(20) null,
  `okved_name` varchar(550) null,
  `okved2_code` varchar(20) null,
  `okved2_name` varchar(550) null,
  PRIMARY KEY (`plan_item_guid`, `ordinal_number`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`purchase_plan_item_row` ( 
  `plan_item_guid` varchar(36) not null,
  `ordinal_number` int not null,
  `additional_info` varchar(2000) null,
  `okdp_code` varchar(20) null,
  `okdp_name` varchar(550) null,
  `okpd2_code` varchar(20) null,
  `okpd2_name` varchar(550) null,
  `okved_code` varchar(20) null,
  `okved_name` varchar(550) null,
  `okved2_name` varchar(550) null,
  `okved2_code` varchar(20) null,
  `okato` varchar(11) null,
  `region` varchar(1000) null,
  `impossible_to_determine_attr` tinyint(1) null,
  `okei_code` varchar(20) null,
  `okei_name` varchar(550) null,
  `qty` decimal(25, 5) null,
  PRIMARY KEY (`plan_item_guid`, `ordinal_number`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`okdp` ( 
  `code` varchar(20) not null,
  `name` varchar(500) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`okpd2` ( 
  `code` varchar(20) not null,
  `name` varchar(500) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`okved` ( 
  `code` varchar(20) not null,
  `name` varchar(500) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`okved2` ( 
  `code` varchar(20) not null,
  `name` varchar(500) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`okei` ( 
  `code` varchar(20) not null,
  `name` varchar(500) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`plan_status` ( 
  `code` varchar(1) not null,
  `name` varchar(35) not null,
  PRIMARY KEY (`code`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`plan_item` ( 
  `guid` varchar(36) not null,
  `purchase_plan_guid` varchar(36) not null,
  `ordinal_number` int not null,
  `contract_subject` varchar(2000) not null,
  `plan_item_customer_inn` varchar(12) not null,
  `minimum_requirements` varchar(2000) null,
  `contract_end_date` date not null,
  `additional_info` varchar(1000) null,
  `modification_description` varchar(2000) null,
  `status_code` varchar(1) null,
  `is_purchase_placed` tinyint(1) null,
  `changed_gws_and_dates` tinyint(1) null,
  `changed_nmsk_more_ten_percent` tinyint(1) null,
  `other_changes` tinyint(1) null,
  `check_result` varchar(8) null,
  `error_messages` varchar(4000) null,
  `cancellation_reason` varchar(14) null,
  `long_term` tinyint(1) null,
  `shared` tinyint(1) null,
  `initial_position_guid` varchar(36) null,
  `initial_plan_guid` varchar(36) null,
  `maximum_contract_price` decimal(22, 2) null,
  `currency_code` varchar(3) null,
  `exchange_rate` decimal(16, 6) null,
  `exchange_rate_date` date null,
  `maximum_contract_price_rub` decimal(22, 2) null,
  `order_pricing` varchar(2000) null,
  `innovation_equivalent` tinyint(1) null,
  `purchase_category_code` int null,
  `is_smb` tinyint(1) not null,
  `is_innovation` tinyint(1) not null,
  PRIMARY KEY (`guid`)
);

CREATE TABLE IF NOT EXISTS `zakupki`.`purchase_category` ( 
  `code` int not null,
  `description` varchar(1100) not null,
  PRIMARY KEY (`code`)
);

ALTER TABLE `zakupki`.`purchase_plan` ADD CONSTRAINT `purchase_plan_placer_inn_foreign` FOREIGN KEY (`placer_inn`) REFERENCES `zakupki`.`customer` (`inn`);
ALTER TABLE `zakupki`.`purchase_plan` ADD CONSTRAINT `purchase_plan_status_code_foreign` FOREIGN KEY (`status_code`) REFERENCES `zakupki`.`plan_status` (`code`);
ALTER TABLE `zakupki`.`purchase_plan` ADD CONSTRAINT `purchase_plan_customer_inn_foreign` FOREIGN KEY (`customer_inn`) REFERENCES `zakupki`.`customer` (`inn`);

ALTER TABLE `zakupki`.`customer` ADD CONSTRAINT `organization_okopf_code_foreign` FOREIGN KEY (`okopf_code`) REFERENCES `zakupki`.`okopf` (`code`);

ALTER TABLE `zakupki`.`purchase_plan_item` ADD CONSTRAINT `purchase_plan_item_guid_foreign` FOREIGN KEY (`guid`) REFERENCES `zakupki`.`plan_item` (`guid`);

ALTER TABLE `zakupki`.`long_term_volumes` ADD CONSTRAINT `long_term_volumes_currency_code_foreign` FOREIGN KEY (`currency_code`) REFERENCES `zakupki`.`currency` (`code`);
ALTER TABLE `zakupki`.`long_term_volumes` ADD CONSTRAINT `long_term_volumes_purchase_plan_item_guid_foreign` FOREIGN KEY (`plan_item_guid`) REFERENCES `zakupki`.`plan_item` (`guid`);

ALTER TABLE `zakupki`.`long_term_volume_detail` ADD CONSTRAINT `long_term_value_detail_long_term_value_guid_foreign` FOREIGN KEY (`long_term_value_guid`) REFERENCES `zakupki`.`long_term_volumes` (`plan_item_guid`);

ALTER TABLE `zakupki`.`innovation_plan_item` ADD CONSTRAINT `innovation_plan_item_guid_foreign` FOREIGN KEY (`guid`) REFERENCES `zakupki`.`plan_item` (`guid`);

ALTER TABLE `zakupki`.`innovation_plan_item_row` ADD CONSTRAINT `innovation_plan_row_item_okdp_code_foreign` FOREIGN KEY (`okdp_code`) REFERENCES `zakupki`.`okdp` (`code`);
ALTER TABLE `zakupki`.`innovation_plan_item_row` ADD CONSTRAINT `innovation_plan_row_item_okpd2_code_foreign` FOREIGN KEY (`okpd2_code`) REFERENCES `zakupki`.`okpd2` (`code`);
ALTER TABLE `zakupki`.`innovation_plan_item_row` ADD CONSTRAINT `innovation_plan_row_item_okved_code_foreign` FOREIGN KEY (`okved_code`) REFERENCES `zakupki`.`okved` (`code`);
ALTER TABLE `zakupki`.`innovation_plan_item_row` ADD CONSTRAINT `innovation_plan_row_item_okved2_code_foreign` FOREIGN KEY (`okved2_code`) REFERENCES `zakupki`.`okved2` (`code`);
ALTER TABLE `zakupki`.`innovation_plan_item_row` ADD CONSTRAINT `innovation_plan_row_item_plan_item_guid_foreign` FOREIGN KEY (`plan_item_guid`) REFERENCES `zakupki`.`plan_item` (`guid`);

ALTER TABLE `zakupki`.`purchase_plan_item_row` ADD CONSTRAINT `purchase_plan_item_row_okdp_code_foreign` FOREIGN KEY (`okdp_code`) REFERENCES `zakupki`.`okdp` (`code`);
ALTER TABLE `zakupki`.`purchase_plan_item_row` ADD CONSTRAINT `purchase_plan_item_row_okpd2_code_foreign` FOREIGN KEY (`okpd2_code`) REFERENCES `zakupki`.`okpd2` (`code`);
ALTER TABLE `zakupki`.`purchase_plan_item_row` ADD CONSTRAINT `purchase_plan_item_row_okved_foreign` FOREIGN KEY (`okved_code`) REFERENCES `zakupki`.`okved` (`code`);
ALTER TABLE `zakupki`.`purchase_plan_item_row` ADD CONSTRAINT `purchase_plan_item_row_okved2_foreign` FOREIGN KEY (`okved2_code`) REFERENCES `zakupki`.`okved2` (`code`);
ALTER TABLE `zakupki`.`purchase_plan_item_row` ADD CONSTRAINT `purchase_plan_item_row_okei_code_foreign` FOREIGN KEY (`okei_code`) REFERENCES `zakupki`.`okei` (`code`);
ALTER TABLE `zakupki`.`purchase_plan_item_row` ADD CONSTRAINT `purchase_plan_item_row_plan_item_guid_foreign` FOREIGN KEY (`plan_item_guid`) REFERENCES `zakupki`.`plan_item` (`guid`);

ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_purchase_plan_guid_foreign` FOREIGN KEY (`purchase_plan_guid`) REFERENCES `zakupki`.`purchase_plan` (`guid`);
ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_plan_item_customer_inn_foreign` FOREIGN KEY (`plan_item_customer_inn`) REFERENCES `zakupki`.`customer` (`inn`);
ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_status_code_foreign` FOREIGN KEY (`status_code`) REFERENCES `zakupki`.`plan_item_status` (`code`);
ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_initial_position_guid_foreign` FOREIGN KEY (`initial_position_guid`) REFERENCES `zakupki`.`plan_item` (`guid`);
ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_initial_plan_guid_foreign` FOREIGN KEY (`initial_plan_guid`) REFERENCES `zakupki`.`purchase_plan` (`guid`);
ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_purchase_category_code_foreign` FOREIGN KEY (`purchase_category_code`) REFERENCES `zakupki`.`purchase_category` (`code`);
ALTER TABLE `zakupki`.`plan_item` ADD CONSTRAINT `plan_item_currency_code_foreign` FOREIGN KEY (`currency_code`) REFERENCES `zakupki`.`currency` (`code`);
