package com.stock.yahoo;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
/**
 * https://www.youtube.com/watch?v=ysP07P_B88w&ab_channel=KKJavaTutorials
 */
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.springframework.stereotype.Component;

@Component
public class AllSymbolsData {

	private static ArrayBlockingQueue<String> symbolQueue = null;
	
	public List<Future<SymbolCurrentState>>  getCurrentData(List<String> workList) throws InterruptedException, ExecutionException {

		AllSymbolsData tc = new AllSymbolsData();

//		List<String> workList = YahooCurrentData.getSymbolList(symbols);
//		System.out.println("Get Tasks: List size is: " + workList.size());
		
		symbolQueue = new ArrayBlockingQueue<>(workList.size());
		symbolQueue.addAll(workList);

		List<Callable<SymbolCurrentState>> callableTasks = tc.getTasks(workList);
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Future<SymbolCurrentState>> futureList = null;
		
		futureList = executor.invokeAll(callableTasks);
		executor.shutdownNow();

		return futureList;
	} // End of main	
	
	
	private List<Callable<SymbolCurrentState>> getTasks(List<String> workList) {
		List<Callable<SymbolCurrentState>> r = new ArrayList<Callable<SymbolCurrentState>>();
			
		for(int i = 0; i < workList.size(); i++) {
			
			String symbol = workList.get(i);
			
			Callable<SymbolCurrentState> task = new Callable<SymbolCurrentState>() {

				@Override
				public SymbolCurrentState call() throws InterruptedException {
					SymbolCurrentState r = null;
					//r = getQuotes(symbol);
					r = getSymbolData(symbol);
					return r;
				}
			};
			r.add(task);
		}
		return r;
	}
	
	
	private SymbolCurrentState getSymbolData(String symbol) {
		YahooHistoryPrice yhp = new YahooHistoryPrice();
	    
	    SymbolCurrentState r = new SymbolCurrentState();

	    /* Getting last price from yahoo history */
	    BigDecimal lastPrice = yhp.getCurrentPrice(yhp, symbol);
	    r.setSymbol(symbol);
	    r.setPrice(lastPrice);
	    
	    return r;
	}
}



/**
public static void main(String[] args) throws InterruptedException, ExecutionException {

	AllSymbolsData tc = new AllSymbolsData();

	List<String> workList = YahooCurrentData.getSymbolList();
	System.out.println("Get Tasks: List size is: " + workList.size());
	
	symbolQueue = new ArrayBlockingQueue<>(workList.size());
	symbolQueue.addAll(workList);
	System.out.println("Sumbol Queue size: " + symbolQueue.size());		

	List<Callable<SymbolCurrentState>> callableTasks = tc.getTasks(workList);
	
	ExecutorService executor = Executors.newFixedThreadPool(10);
//	Future<SymbolCurrentState> future = null;
	List<Future<SymbolCurrentState>> futureList = null;
	
	futureList = executor.invokeAll(callableTasks);
	System.out.println("callableTasks.size = " + callableTasks.size());
	System.out.println("futureList.size = " + futureList.size());
	
	for(int y = 0; y < futureList.size(); y++) {
		Future<SymbolCurrentState> future =	futureList.get(y);
		SymbolCurrentState p = future.get();
		//System.out.println("Callable result = " + p.getSymbol() + " " + p.getPrice() + "  PrevClose: " + p.getPreviousClose() + " Changed %: " + p.getChangedPercent());
		BigDecimal outstandingShares = p.getMarketCap().divide(p.getPrice(), MathContext.DECIMAL32);
		System.out.printf("%-8S | %6.2f | %5.2f | %6.2f | %12.6f | %n", p.getSymbol(), p.getPrice(), p.getChangedPercent(), p.getMarketCap(), outstandingShares);
		
	}
	
		System.out.println("It is done!");
		executor.shutdownNow();
} // End of main
*/
