package com.stock.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "watch_symbol", uniqueConstraints = {@UniqueConstraint(columnNames = "symbol")})
public class WatchSymbol {

  // private static final long serialVersionUID = -2952735933715107255L;

  @Id
  @Column(name = "symbol", unique = true, nullable = false, length = 10)
  private String symbol;
  @Column(name = "quoterly_dividend_amount")
  private BigDecimal quoterlyDividendAmount;
  @Column(name = "upper_yield")
  private BigDecimal upperYield;
  @Column(name = "lower_yield")
  private BigDecimal lowerYield;
  @Column(name = "updated_on")
  private LocalDateTime updatedOn;

  public WatchSymbol() {
    super();
  }

  public WatchSymbol(String symbol, BigDecimal quoterlyDividendAmount, BigDecimal upperYield,
      BigDecimal lowerYield, LocalDateTime updatedOn) {
    super();
    this.symbol = symbol;
    this.quoterlyDividendAmount = quoterlyDividendAmount;
    this.upperYield = upperYield;
    this.lowerYield = lowerYield;
    this.updatedOn = updatedOn;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public BigDecimal getQuoterlyDividendAmount() {
    return quoterlyDividendAmount;
  }

  public void setQuoterlyDividendAmount(BigDecimal quoterlyDividendAmount) {
    this.quoterlyDividendAmount = quoterlyDividendAmount;
  }

  public BigDecimal getUpperYield() {
    return upperYield;
  }

  public void setUpperYield(BigDecimal upperYield) {
    this.upperYield = upperYield;
  }

  public BigDecimal getLowerYield() {
    return lowerYield;
  }

  public void setLowerYield(BigDecimal lowerYield) {
    this.lowerYield = lowerYield;
  }

  public LocalDateTime getUpdatedOn() {
    return updatedOn;
  }

  public void setUpdatedOn(LocalDateTime updatedOn) {
    this.updatedOn = updatedOn;
  }

  @Override
  public String toString() {
    return "WatchSymbol [symbol=" + symbol + ", quoterlyDividendAmount=" + quoterlyDividendAmount
        + ", upperYield=" + upperYield + ", lowerYield=" + lowerYield + ", updatedOn=" + updatedOn
        + "]";
  }
}
