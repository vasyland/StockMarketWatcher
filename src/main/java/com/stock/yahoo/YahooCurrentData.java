package com.stock.yahoo;

import java.time.LocalDate;

public class YahooCurrentData {

	private LocalDate date;
	private String close;
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public String getClose() {
		return close;
	}
	public void setClose(String close) {
		this.close = close;
	}
	@Override
	public String toString() {
		return "YahooCurrentData [date=" + date + ", close=" + close + "]";
	}
	
}
