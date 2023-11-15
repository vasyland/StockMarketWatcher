package com.stock.yahoo;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.beans.factory.annotation.Value;

/**
 * <b>Get the Html source from a https url </b>
 * https://stackoverflow.com/questions/9501237/read-input-stream-twice
 */
public class YahooCurrentData {
	


	public static void main(String[] args) throws Exception {
		YahooCurrentData dude = new YahooCurrentData();
		SymbolCurrentState test = null;
		String testSymbol = "SLF.TO";
		
		try {
			InputStream yahooDataPage = dude.getYahooDataPage(testSymbol);
			try {
				test = dude.getSymbolData(yahooDataPage, testSymbol);
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			System.out.println("Price: " + test.getPrice() +  " Prev. Close: " + test.getPreviousClose() + " Percent changed: " + test.getChangedPercent());
			yahooDataPage.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Get a list of monitored symbols
	 * @return
	 */
	public static List<String> getSymbolList(String symbols) {
		//String symbols = "TD.TO, ENB.TO, CNQ.TO, NA.TO, CVE.TO, SU.TO, RY.TO";
		return Arrays.asList(symbols.split("\\s*,\\s*"));
	}
	
	
	public SymbolCurrentState getSymbolData(InputStream yahooDataPage, String symbol) throws Exception {
		SymbolCurrentState result = new SymbolCurrentState();
		
		String sPrice = null;
		String sPreviousClose = null;
		String sChangedPercent = null;
		String sMarketCap = null;
	
		InputStreamReader isr = new InputStreamReader(yahooDataPage);
		BufferedReader in = new BufferedReader(isr);
		String inputLine;

		while ((inputLine = in.readLine()) != null) {

			// Price
			String pPrice = "data-symbol=\"" + symbol + "\"";
			String rawPrice = null;
			if (inputLine.contains("data-field=\"regularMarketPrice\"") && inputLine.contains(pPrice)) {

				int startValue = inputLine.indexOf("value=\"");
				int endValue = inputLine.indexOf("active=\"\">");
				rawPrice = inputLine.substring(startValue, endValue);
				sPrice = makeItDigit(rawPrice);
			}
			
			String rawPreviousClose = null;
			String pPreviousClose = "data-test=\"PREV_CLOSE-value\">";
			if (inputLine.contains(pPreviousClose)) {

				int startValue = inputLine.indexOf("PREV_CLOSE-value\">");
				String prevChunk = inputLine.substring(startValue + "PREV_CLOSE-value\">".length());
				int endValue = prevChunk.indexOf("</td></tr>");
				rawPreviousClose = prevChunk.substring(0, endValue);
				sPreviousClose = makeItDigit(rawPreviousClose);
				//System.out.println("Previous Close: " + sPreviousClose);
			}
			
			String rawPercent = null;
			if ((inputLine.contains("<span class=\"C($negativeColor)\">(")
					|| inputLine.contains("<span class=\"C($positiveColor)\">"))
					&& inputLine.contains("%)</span></fin-streamer>")) {

				// System.out.println("pStart = " + pStart);
				int pEnd = inputLine.indexOf(("%)</span></fin-streamer>"));
				int pStart = pEnd - 6;
				rawPercent = inputLine.substring(pStart, pEnd);
				// System.out.println("Percent: " + rawPercent);
				sChangedPercent = makeItDigit(rawPercent);
				// System.out.println("PERCENT: " + percent);
			}
			
			String rawMarketCap = null;
			if (inputLine.contains("data-test=\"MARKET_CAP-value")) {

				// System.out.println("pStart = " + pStart);
				int pStart = inputLine.indexOf("data-test=\"MARKET_CAP-value\">") + 29;
				int pEnd = pStart + 9;
				
				rawMarketCap = inputLine.substring(pStart, pEnd);
				// System.out.println("Percent: " + rawPercent);
				sMarketCap = makeItDigit(rawMarketCap);
				// System.out.println("MARKET CAP: " + sMarketCap);
			}			
			
		}
		
		//SymbolCurrentState(String symbol, BigDecimal price, BigDecimal previousClose, BigDecimal changedPercent)
		BigDecimal dPrice = null;
		BigDecimal dPreviousClose = null;
		BigDecimal dChangedPercent = null;
		BigDecimal dMarketCap = null;
		try {
			dPrice = new BigDecimal(sPrice);
			dPreviousClose = new BigDecimal(sPreviousClose);
			dChangedPercent = new BigDecimal(sChangedPercent);
			dMarketCap = new BigDecimal(sMarketCap);
		//} catch (NumberFormatException nfe) {
		} catch (Exception nfe) {
			System.out.println("ERROR SYMBOL: " + symbol);
			dPrice = new BigDecimal("0");
			dPreviousClose = new BigDecimal("0");
			dChangedPercent = new BigDecimal("0");
			dMarketCap = new BigDecimal("0");
		}
		
		result.setSymbol(symbol);
		result.setPrice(dPrice);
		result.setPreviousClose(dPreviousClose);
		result.setChangedPercent(dChangedPercent);
		result.setMarketCap(dMarketCap);

		in.close();
		return result;
	}

	
	/*
	 * Get entire Yahoo Data Page as a stream
	 */
	public InputStream getYahooDataPage(String symbol) throws Exception {
		String httpsURL = "https://finance.yahoo.com/quote/" + symbol + "?p=" + symbol;

		URL myurl = new URL(httpsURL);
		HttpsURLConnection con = (HttpsURLConnection) myurl.openConnection();
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:63.0) Gecko/20100101 Firefox/63.0");
		return con.getInputStream();
	}
	
	
	/**
	 * Scans string and extracts digits in order to build a number representation of
	 * the value
	 * 
	 * @param p Piece of raw string containing data
	 * @return Digital representation of the value
	 */
	public synchronized String makeItDigit(String p) {
		String result = "";
		boolean isDigitSeen = false;
		int e;
		for (e = 0; e < p.length(); e++) {
			switch (p.charAt(e)) {
			case ',':
				break;
			case '0':
			case '1':
			case '2':
			case '3':
			case '4':
			case '5':
			case '6':
			case '7':
			case '8':
			case '9':
			case ':':
			case '+':
			case '-':
			case '.':
				isDigitSeen = true;
				result = result + p.charAt(e);
				break;
			}
		}
		if (!isDigitSeen) {
			return "0";
		}
		return result;
	}
	
	
}
