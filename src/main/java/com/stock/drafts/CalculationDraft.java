package com.stock.drafts;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.stock.model.SymbolStatus;
import com.stock.model.WatchSymbol;
import com.stock.yahoo.SymbolCurrentState;

public class CalculationDraft {

	public static void main(String[] args) {
		
		List<WatchSymbol> wsl = mockWatchSymbols();
		List<SymbolCurrentState> scs = mockSymbolCurrentState();
		processSymbolStatus(wsl, scs);
	}
	
	
	/**
	 * Calculation of the current yield of the symbol
	 * @param wsl
	 * @param scs
	 */
	public static void processSymbolStatus(List<WatchSymbol> wsl, List<SymbolCurrentState> scs) {
		
		int res;
		int res2;
		int res3;
		String action = "";
		
		for(SymbolCurrentState c: scs) {
			
			Optional<WatchSymbol> ws = wsl.stream().filter(t -> t.getSymbol().equalsIgnoreCase(c.getSymbol())).findFirst();
			if(ws.isEmpty()) {
				System.out.println("Data for symbol " + c.getSymbol() + " not found.");
				continue;
			} else {
			    
				/* Calculation of the current yield */
			    BigDecimal yield = ws.get().getQuoterlyDividendAmount().multiply(BigDecimal.valueOf(400))
			            .divide(c.getPrice(), RoundingMode.HALF_EVEN);
			    BigDecimal yieldRange = ws.get().getUpperYield().subtract(ws.get().getLowerYield());
			    BigDecimal quoterOfUpperYield =  yieldRange.divide(BigDecimal.valueOf(4), 3, RoundingMode.HALF_EVEN);
			    BigDecimal allowToBuyYield = ws.get().getUpperYield().subtract(quoterOfUpperYield);
			    
			    BigDecimal sellPoint = ws.get().getLowerYield().add(quoterOfUpperYield);
			    
			    
			    /* 
			     * Action = "Buy" if current yield is above Upper yield or in the top of 1/4th of the range
			     * between Upper and Lower yields 
			     */
			    res = yield.compareTo(allowToBuyYield);
			    res2 = ws.get().getUpperYield().compareTo(BigDecimal.valueOf(0.0));
			    if (res == 0 || res == 1 && res2 != 0) {
			       action = "Buy";
			    } else {
			       action = "";
			    }
			    
			    res3 = yield.compareTo(sellPoint);
			    if (res3 == -1 && res2 != 0) {
				   action = "Sell";
			    }
			    
			    if(res == -1 && res3 == 1 && res2 != 0) {
			    	action = "Hold";
			    }
			    
			    
			    System.out.println("\n" + c.getSymbol() + "-> Price:" + c.getPrice() + 
			    		"  QDivAmt: " + ws.get().getQuoterlyDividendAmount() + 
			    		"  Yield: " + yield + 
			    		"  \n Upper Yield: " + ws.get().getUpperYield() +
			    		"  Lower Yield: " + ws.get().getLowerYield() +
			    		" Quoter of Yield Range: " + quoterOfUpperYield +
			    		"  Allowed to Buy Yield: " + allowToBuyYield +
			    		" Action: " + action);
			    
			    /* Clean SYMBOL_STATUS table and populate with symbols that have Action = "Buy" only */
			    	SymbolStatus symbolStatus = new SymbolStatus();
			    	symbolStatus.setSymbol(c.getSymbol());
				    symbolStatus.setCurrentPrice(c.getPrice());
				    symbolStatus.setCurrentYield(yield);
				    symbolStatus.setStatus("Active");
				    symbolStatus.setRecomendedAction(action);

			}
		}
	}

	
	/*
	 * Mocking current state of symbols
	 */
	static List<SymbolCurrentState> mockSymbolCurrentState() {
		List<SymbolCurrentState> r = new ArrayList();
		
		SymbolCurrentState c1 = new SymbolCurrentState();
		c1.setSymbol("ENB.TO");
		c1.setPrice(new BigDecimal("66.22"));
		c1.setPreviousClose(new BigDecimal("45.12"));
		
		SymbolCurrentState c2 = new SymbolCurrentState();
		c2.setSymbol("BB.TO");
		c2.setPrice(new BigDecimal("5.05"));
		c2.setPreviousClose(new BigDecimal("5.01"));
		
		SymbolCurrentState c3 = new SymbolCurrentState();
		c3.setSymbol("RY.TO");
		c3.setPrice(new BigDecimal("120.49"));
		c3.setPreviousClose(new BigDecimal("119.87"));
		
		SymbolCurrentState c4 = new SymbolCurrentState();
		c4.setSymbol("BMO.TO");
		c4.setPrice(new BigDecimal("112.03"));
		c4.setPreviousClose(new BigDecimal("111.87"));
		
		SymbolCurrentState c5 = new SymbolCurrentState();
		c5.setSymbol("SHOP.TO");
		c5.setPrice(new BigDecimal("93.87"));
		c5.setPreviousClose(new BigDecimal("91.28"));
		
		SymbolCurrentState c6 = new SymbolCurrentState();
		c6.setSymbol("BNS.TO");
		c6.setPrice(new BigDecimal("61.09"));
		c6.setPreviousClose(new BigDecimal("60.85"));
		
		r.add(c1);
		r.add(c2);
		r.add(c3);
		r.add(c4);
		r.add(c5);
		r.add(c6);
		
		return r;
	}
	
	
	/**
	 * Mocking watch list
	 * @return
	 */
	static List<WatchSymbol> mockWatchSymbols() {
		LocalDateTime ldt = LocalDateTime.now();  
		List<WatchSymbol> wsl = new ArrayList();
		
		WatchSymbol w1 = new WatchSymbol();
		w1.setSymbol("ENB.TO");
		w1.setQuoterlyDividendAmount(new BigDecimal("0.8875"));
		w1.setUpperYield(new BigDecimal("7.56"));
		w1.setLowerYield(new BigDecimal("6.72"));
		w1.setUpdatedOn(ldt);

		WatchSymbol w2 = new WatchSymbol();
		w2.setSymbol("BMO.TO");
		w2.setQuoterlyDividendAmount(new BigDecimal("1.43"));
		w2.setUpperYield(new BigDecimal("4.8"));
		w2.setLowerYield(new BigDecimal("4.2"));
		w2.setUpdatedOn(ldt);

		WatchSymbol w3 = new WatchSymbol();
		w3.setSymbol("SU.TO");
		w3.setQuoterlyDividendAmount(new BigDecimal("0.52"));
		w3.setUpperYield(new BigDecimal("5.5"));
		w3.setLowerYield(new BigDecimal("4.85"));
		w3.setUpdatedOn(ldt);
		
		WatchSymbol w4 = new WatchSymbol();
		w4.setSymbol("BNS.TO");
		w4.setQuoterlyDividendAmount(new BigDecimal("1.03"));
		w4.setUpperYield(new BigDecimal("6.43"));
		w4.setLowerYield(new BigDecimal("5.77"));
		w4.setUpdatedOn(ldt);
		
		WatchSymbol w5 = new WatchSymbol();
		w5.setSymbol("RY.TO");
		w5.setQuoterlyDividendAmount(new BigDecimal("1.35"));
		w5.setUpperYield(new BigDecimal("4.41"));
		w5.setLowerYield(new BigDecimal("4.05"));
		w5.setUpdatedOn(ldt);
		
		wsl.add(w1);
		wsl.add(w2);
		wsl.add(w3);
		wsl.add(w4);
		wsl.add(w5);
		
		return wsl;
	}
	
	
}