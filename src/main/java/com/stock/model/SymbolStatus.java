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
	@Column(name="SYMBOL", nullable=false, updatable=true)
	private String symbol;
	@Column(name="CURRENT_PRICE")
	private BigDecimal currentPrice;
	@Column(name="CURRENT_YIELD")
	private BigDecimal currentYield;
	@Column(name="UPPER_YIELD")
	private BigDecimal upperYield;
	@Column(name="LOWER_YIELD")
	private BigDecimal lowerYield;
	@Column(name="ALLOWEDTOBUY_YIELD")
	private BigDecimal allowedToBuyYield;
	@Column(name="SELL_POINT_YIELD")
	private BigDecimal sellPointYield;
	@Column(name="RECOMENDED_ACTION")
	private String recomendedAction;
	@Column(name = "UPDATED_ON")
	private LocalDateTime updatedOn;
	
	public SymbolStatus() {
		super();
	}

	public SymbolStatus(String symbol, BigDecimal currentPrice, BigDecimal currentYield, BigDecimal upperYield,
			BigDecimal lowerYield, BigDecimal allowedToBuyYield, BigDecimal sellPointYield, String recomendedAction,
			LocalDateTime updatedOn) {
		super();
		this.symbol = symbol;
		this.currentPrice = currentPrice;
		this.currentYield = currentYield;
		this.upperYield = upperYield;
		this.lowerYield = lowerYield;
		this.allowedToBuyYield = allowedToBuyYield;
		this.sellPointYield = sellPointYield;
		this.recomendedAction = recomendedAction;
		this.updatedOn = updatedOn;
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

	public BigDecimal getAllowedToBuyYield() {
		return allowedToBuyYield;
	}

	public void setAllowedToBuyYield(BigDecimal allowedToBuyYield) {
		this.allowedToBuyYield = allowedToBuyYield;
	}

	public BigDecimal getSellPointYield() {
		return sellPointYield;
	}

	public void setSellPointYield(BigDecimal sellPointYield) {
		this.sellPointYield = sellPointYield;
	}

	public String getRecomendedAction() {
		return recomendedAction;
	}

	public void setRecomendedAction(String recomendedAction) {
		this.recomendedAction = recomendedAction;
	}

	public LocalDateTime getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(LocalDateTime updatedOn) {
		this.updatedOn = updatedOn;
	}

	@Override
	public String toString() {
		return "SymbolStatus [symbol=" + symbol + 
				", currentPrice=" + currentPrice + 
				", currentYield=" + currentYield + 
				", upperYield=" + upperYield + 
				", lowerYield=" + lowerYield + 
				", allowedToBuyYield=" + allowedToBuyYield + 
				", sellPointYield=" + sellPointYield + 
				", recomendedAction=" + recomendedAction
				+ ", updatedOn=" + updatedOn + "]";
	}
}
