package com.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
	@Column(name="RECOMENDED_ACTION")
	private String recomendedAction;
	@Column(name="STATUS")
	private String status;
	@Column(name = "UPDATED_ON")
	private LocalDateTime updatedOn;
	
	public SymbolStatus() {
		super();
	}

	public SymbolStatus(String symbol, BigDecimal currentPrice, BigDecimal currentYield, String recomendedAction,
			String status, LocalDateTime updatedOn) {
		super();
		this.symbol = symbol;
		this.currentPrice = currentPrice;
		this.currentYield = currentYield;
		this.recomendedAction = recomendedAction;
		this.status = status;
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

	public String getRecomendedAction() {
		return recomendedAction;
	}

	public void setRecomendedAction(String recomendedAction) {
		this.recomendedAction = recomendedAction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
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
				",\n currentPrice=" + currentPrice + 
				",\n currentYield=" + currentYield + 
				",\n recomendedAction=" + recomendedAction + 
				",\n status=" + status + 
				",\n updatedOn=" + updatedOn + "]";
	}
	
	
}
