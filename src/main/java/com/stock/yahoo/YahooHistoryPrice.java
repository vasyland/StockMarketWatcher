package com.stock.yahoo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class YahooHistoryPrice {

	DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static void main(String[] args) {
		YahooHistoryPrice yhp = new YahooHistoryPrice();
		BigDecimal result = yhp.getCurrentPrice(yhp, "ENB.TO");
		System.out.println("Current Price is: " + result);
	}

	/**
	 * Getting Last Price 
	 * @param yahooHistoryService
	 * @param symbol
	 * @return
	 */
	public BigDecimal getCurrentPrice(YahooHistoryPrice yahooHistoryService, String symbol) {
		BigDecimal currentPrice = new BigDecimal("0");

		/* Get Start Date */
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, -2);

		int startDay = cal.get(Calendar.DAY_OF_MONTH); // System.out.println("startDay=" + startDay);
		int startMonth = cal.get(Calendar.MONTH); // System.out.println("startMonth=" + startMonth);
		int startYear = cal.get(Calendar.YEAR); // System.out.println("startYear=" + startYear);

		cal.set(Calendar.YEAR, startYear);
		cal.set(Calendar.MONTH, startMonth);
		cal.set(Calendar.DAY_OF_MONTH, startDay);

		/* Getting Tomorrow's date */
		Calendar cal1 = Calendar.getInstance();
		cal1.add(Calendar.DAY_OF_MONTH, 1);

		int endDay = cal1.get(Calendar.DAY_OF_MONTH);
		int endMonth = cal1.get(Calendar.MONTH);
		int endYear = cal.get(Calendar.YEAR);

		cal1.set(Calendar.YEAR, endYear);
		cal1.set(Calendar.MONTH, endMonth);
		cal1.set(Calendar.DAY_OF_MONTH, endDay);

		java.util.Date period1 = null;
		java.util.Date period2 = null;
		try {
			period1 = yahooHistoryService.datechange(cal);
			period2 = yahooHistoryService.datechange(cal1);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		/* Getting dates as a long number */
		long strDate = period1.getTime();
		strDate = strDate / 1000;
		long strDate1 = period2.getTime();
		strDate1 = strDate1 / 1000;

		/* Getting data from Yahoo History */
		try {
			currentPrice = yahooHistoryService.getCurrentPrice(symbol, strDate, strDate1);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return currentPrice;
	}

	/**
	 * Getting history price data from yahoo
	 * 
	 * @param symbol
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public BigDecimal getCurrentPrice(final String symbol, final long strStartDate, final long strEndDate)
			throws IOException, ParseException {

		String interval = "1d";
		String link = "https://query1.finance.yahoo.com/v7/finance/download/" + symbol + "?period1=" + strStartDate
				+ "&period2=" + strEndDate + "&interval=1d&events=history";
		
		// "&period2=" + strEndDate + "&interval=" + interval + "&events=history";
		//System.out.println("Link: " + link);
		// link = "https://query1.finance.yahoo.com/v7/finance/download/TD.TO?period1=1705795200&period2=1706572800&interval=1d&events=history";

		URL url = new URL(link);
		URLConnection urlConn = url.openConnection();
		InputStreamReader inStream = new InputStreamReader(urlConn.getInputStream());
		BufferedReader buf = new BufferedReader(inStream);
		String line;
		List<YahooCurrentData> recentPrices = new ArrayList<>();

		while ((line = buf.readLine()) != null) {
			// System.out.println(line); //Date,Open,High,Low,Close,Adj Close,Volume
			if (line != null && !line.contains("Date") && line.length() > 50) {
				String[] mem = line.split(",");

				YahooCurrentData cd = new YahooCurrentData();

				/* Converting string into ISO_DATE */
				LocalDate priceDate = LocalDate.parse(mem[0]);
				cd.setDate(priceDate);
				cd.setClose(mem[4]);
				recentPrices.add(cd);
				// System.out.println("Date = " + priceDate + " Price is: " + mem[4]);
			}
		}

		/* sort in reversed order - last price will be a first element */
		recentPrices.sort((o2, o1) -> o1.getDate().compareTo(o2.getDate()));
		YahooCurrentData lastRecord = recentPrices.stream().findFirst().orElse(null);

		BigDecimal closePrice = new BigDecimal("0");
		if (lastRecord != null) {
			closePrice = new BigDecimal(lastRecord.getClose());
		}
		return closePrice;
	}

	/**
	 * Converting Calendar date into long number
	 * 
	 * @param cal
	 * @return
	 * @throws ParseException
	 */
	public java.util.Date datechange(final Calendar cal) throws ParseException {
		java.util.Date dateOne = cal.getTime();
		String a = dateOne.toString();
		String b[] = a.split(" ");
		String c = b[1] + " " + b[2] + " " + b[5];
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd yyyy", Locale.ENGLISH);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		cal.setTime(sdf.parse(c));
		dateOne = cal.getTime();
		sdf.format(dateOne);
		return dateOne;
	}

}
