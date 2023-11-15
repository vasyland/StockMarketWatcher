package com.stock.yahoo;

import java.math.BigDecimal;

public class SymbolCurrentState {

	private String symbol;
	private BigDecimal price;
	private BigDecimal previousClose;
	private BigDecimal changedPercent;
	private BigDecimal marketCap;
	
	public SymbolCurrentState() {
		super();
	}

	public SymbolCurrentState(String symbol, BigDecimal price, BigDecimal previousClose, BigDecimal changedPercent, BigDecimal marketCap) {
		super();
		this.symbol = symbol;
		this.price = price;
		this.previousClose = previousClose;
		this.changedPercent = changedPercent;
		this.marketCap = marketCap;
	}
	public String getSymbol() {
		return symbol;
	}
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getPreviousClose() {
		return previousClose;
	}
	public void setPreviousClose(BigDecimal previousClose) {
		this.previousClose = previousClose;
	}
	public BigDecimal getChangedPercent() {
		return changedPercent;
	}
	public void setChangedPercent(BigDecimal changedPercent) {
		this.changedPercent = changedPercent;
	}
	
	public BigDecimal getMarketCap() {
		return marketCap;
	}

	public void setMarketCap(BigDecimal marketCap) {
		this.marketCap = marketCap;
	}

	@Override
	public String toString() {
		return "SymbolCurrentState [symbol=" + symbol + ", price=" + price + ", previousClose=" + previousClose
				+ ", changedPercent=" + changedPercent + ", " + marketCap + "]";
	}
	
}
