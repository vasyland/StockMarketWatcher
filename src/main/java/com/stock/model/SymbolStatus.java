package com.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "symbol_status")
public class SymbolStatus implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="symbol", nullable=false, updatable=true)
	private String symbol;
	@Column(name="current_price")
	private BigDecimal currentPrice;
	@Column(name = "quoterly_dividend_amount")
	private BigDecimal quoterlyDividendAmount;
	@Column(name="current_yield")
	private BigDecimal currentYield;
	@Column(name="upper_yield")
	private BigDecimal upperYield;
	@Column(name="lower_yield")
	private BigDecimal lowerYield;
	
	@Column(name="allowed_buy_price")
	private BigDecimal allowedBuyPrice;
	@Column(name="best_buy_price")
	private BigDecimal bestBuyPrice;
	@Column(name="allowed_buy_yield")
	private BigDecimal allowedBuyYield;
	@Column(name="SELL_POINT_YIELD")
	private BigDecimal sellPointYield;
	@Column(name="recommended_action")
	private String recommendedAction;
	@Column(name = "updated_on")
	private LocalDateTime updatedOn;
	
	public SymbolStatus() {
		super();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public BigDecimal getCurrentPrice() {
		return currentPrice;
	}

	public void setCurrentPrice(BigDecimal currentPrice) {
		this.currentPrice = currentPrice;
	}

	public BigDecimal getCurrentYield() {
		return currentYield;
	}

	public void setCurrentYield(BigDecimal currentYield) {
		this.currentYield = currentYield;
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

	public BigDecimal getAllowedBuyYield() {
		return allowedBuyYield;
	}

	public void setAllowedBuyYield(BigDecimal allowedBuyYield) {
		this.allowedBuyYield = allowedBuyYield;
	}

	public BigDecimal getSellPointYield() {
		return sellPointYield;
	}

	public void setSellPointYield(BigDecimal sellPointYield) {
		this.sellPointYield = sellPointYield;
	}

	public String getRecommendedAction() {
		return recommendedAction;
	}

	public void setRecommendedAction(String recommendedAction) {
		this.recommendedAction = recommendedAction;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	public BigDecimal getQuoterlyDividendAmount() {
		return quoterlyDividendAmount;
	}

	public void setQuoterlyDividendAmount(BigDecimal quoterlyDividendAmount) {
		this.quoterlyDividendAmount = quoterlyDividendAmount;
	}

	public BigDecimal getAllowedBuyPrice() {
		return allowedBuyPrice;
	}

	public void setAllowedBuyPrice(BigDecimal allowedBuyPrice) {
		this.allowedBuyPrice = allowedBuyPrice;
	}

	public BigDecimal getBestBuyPrice() {
		return bestBuyPrice;
	}

	public void setBestBuyPrice(BigDecimal bestBuyPrice) {
		this.bestBuyPrice = bestBuyPrice;
	}

	@Override
	public String toString() {
		return "SymbolStatus [symbol=" + symbol + ", currentPrice=" + currentPrice + ", quoterlyDividendAmount="
				+ quoterlyDividendAmount + ", currentYield=" + currentYield + ", upperYield=" + upperYield
				+ ", lowerYield=" + lowerYield + ", allowedBuyPrice=" + allowedBuyPrice + ", bestBuyPrice="
				+ bestBuyPrice + ", allowedBuyYield=" + allowedBuyYield + ", sellPointYield=" + sellPointYield
				+ ", recommendedAction=" + recommendedAction + ", updatedOn=" + updatedOn + "]";
	}

	
}
