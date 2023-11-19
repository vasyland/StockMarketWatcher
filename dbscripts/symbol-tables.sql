-- -----------------------------------------------------
-- Table `horse2`.`watch_symbol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `horse2`.`watch_symbol` (
  `symbol` VARCHAR(10) NOT NULL COMMENT 'Stock symbol TSX with .TO',
  `quoterly_dividend_amount` DECIMAL(10,4) NULL COMMENT 'Majority ov comapnies pay on quaterly basis',
  `upper_yield` DECIMAL(6,4) NULL COMMENT 'Upper yeild where price is at lowest point',
  `lower_yield` DECIMAL(6,4) NULL COMMENT 'Lowe yield when price is at highest point',
  `updated_on` DATETIME(6) NULL COMMENT 'Date when record was created or updated',
  PRIMARY KEY (`symbol`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `horse2`.`symbol_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `horse2`.`symbol_status` (
  `symbol` VARCHAR(10) NOT NULL COMMENT 'Stock ticker. TSX with .TO',
  `current_price` DECIMAL(10,4) NULL COMMENT 'Cuurent price taken from yahoo finance',
  `current_yield` DECIMAL(6,4) NULL COMMENT 'Current yeild calculated from (quaterly_dividend_amount * 4) /current_price * 100',
  `recomended_action` VARCHAR(15) NULL COMMENT 'Possible Values: BUY, SELL, HOLD',
  `status` VARCHAR(25) NULL COMMENT 'Status of the findings: OUTDATED, NOT AVAILABLE, ERROR, ACTIVE',
  `updated_on` DATETIME(6) NULL,
  PRIMARY KEY (`symbol`))
ENGINE = InnoDB;