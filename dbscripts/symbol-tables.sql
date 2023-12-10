-- -----------------------------------------------------
-- Table `horse2`.`watch_symbol`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `horse2`.`watch_symbol` (
  `symbol` VARCHAR(10) NOT NULL COMMENT 'Stock symbol TSX with .TO',
  `quoterly_dividend_amount` DECIMAL(10,4) NULL COMMENT 'Majority of comapnies pay dividends on a quaterly basis',
  `upper_yield` DECIMAL(6,4) NULL COMMENT 'Upper yeild where price is at lowest point',
  `lower_yield` DECIMAL(6,4) NULL COMMENT 'Lower yield when price is at highest point',
  `updated_on` DATETIME(6) NULL COMMENT 'Date when record was created or updated',
  PRIMARY KEY (`symbol`))
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `horse2`.`symbol_status`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `horse2`.`symbol_status` (
  `symbol` VARCHAR(10) NOT NULL COMMENT 'Stock ticker. TSX with .TO',
  `current_price` DECIMAL(10,4) NULL COMMENT 'Cuurent price taken from yahoo finance',
  `quoterly_dividend_amount` DECIMAL(10,4) NULL COMMENT 'Majority of comapnies pay dividends on a quaterly basis',
  `current_yield` DECIMAL(6,4) NULL COMMENT 'Current yeild calculated from (quaterly_dividend_amount * 4) /current_price * 100',
  `upper_yield` DECIMAL(6,4) NULL DEFAULT NULL COMMENT 'Upper yield' ,
  `lower_yield` DECIMAL(6,4) NULL DEFAULT NULL COMMENT 'Lower_yield' ,
  `allowed_buy_yield` DECIMAL(6,4) NULL DEFAULT NULL ,
  `sell_point_yield` DECIMAL(6,4) NULL DEFAULT NULL COMMENT 'Status of the findings: OUTDATED, NOT AVAILABLE, ERROR, ACTIVE',
  `allowed_buy_price` DECIMAL(6,4) NULL DEFAULT NULL COMMENT 'Price just below top yiled price. ',
  `best_buy_price` DECIMAL(6,4) NULL DEFAULT NULL COMMENT 'Best price to buy. It is at the top of the yield',
  `recommended_action` VARCHAR(15) NULL COMMENT 'Possible Values: BUY, SELL, HOLD',
  `updated_on` DATETIME(6) NULL,
  PRIMARY KEY (`symbol`))
ENGINE = InnoDB;
