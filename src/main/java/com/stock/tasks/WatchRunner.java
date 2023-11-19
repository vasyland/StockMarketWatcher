package com.stock.tasks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stock.model.SymbolStatus;
import com.stock.model.WatchSymbol;
import com.stock.services.SymbolService;
import com.stock.yahoo.AllSymbolsData;
import com.stock.yahoo.SymbolCurrentState;

@Component
public class WatchRunner {
	
	private static final Logger log = LogManager.getLogger(WatchRunner.class);

	/* Services */
	private AllSymbolsData allSymbolsData;
	private SymbolService symbolService;

	public WatchRunner(AllSymbolsData allSymbolsData, SymbolService symbolService) {
		super();
		this.allSymbolsData = allSymbolsData;
		this.symbolService = symbolService;
	}

	/* Every 5 seconds */
	@Scheduled(cron = "${cron-string}")
	public void everyFiveSeconds() {

		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		try {
			
			String destFile = "C:\\tmp\\marketcap-data\\stock-2023-11-19.txt";
			try(
				FileWriter fileWriter = new FileWriter(destFile, true);
				PrintWriter pw = new PrintWriter(fileWriter);
				) {
				
				/* Here we need to get a list of symbols from the watch table and a list of all symbols entered by users */
				List<String> symbolList = symbolService.getSymbols();
				log.info("#1 Symbols to process: " + symbolList);
			
				/* Getting Yahoo current prices for each symbol */
				List<Future<SymbolCurrentState>> data = allSymbolsData.getCurrentData(symbolList);
	
				/* Getting watched symbols with defined yield range */
				List<WatchSymbol>  watchedSymbols = symbolService.getWatchSymbolsData();

				List<SymbolCurrentState> scs = new ArrayList<>();
				
				/* Calculate current yield for each symbol */
				for (int y = 0; y < data.size(); y++) {
					
					Future<SymbolCurrentState> future = data.get(y);
					SymbolCurrentState p = future.get();
					BigDecimal outstandingShares = p.getMarketCap().divide(p.getPrice(), MathContext.DECIMAL32);
					
					scs.add(p);
					
//					if(p.getSymbol().equalsIgnoreCase(consoleSymbol)) 
//						System.out.printf("%-8S | %6.2f | %5.2f | %6.2f | %12.6f | %n", p.getSymbol(), p.getPrice(),
//								p.getChangedPercent(), p.getMarketCap(), outstandingShares);
					
					//pw.print("Date,Symbol,Price,ChangedProcent,MarketCap,OutstandingShares");
					date = new Date();
					String line = String.format("%-19S, %-8S,%6.2f,%5.2f,%6.2f,%8.6f%n",  
							dateFormat.format(new Date()), 
							p.getSymbol().trim(), 
							p.getPrice(),
							p.getChangedPercent(), 
							p.getMarketCap(), 
							outstandingShares);
					
					pw.print(line);
				}
				
			    /* Process Buy symbol list  */
				processSymbolStatus(watchedSymbols, scs);
				log.info("#2 Process Buy symbol list done");
			
			} catch (IOException e) {
				log.error("#3 ERROR - " + e.getMessage());
			}
		} catch (InterruptedException | ExecutionException e) {
			log.error("#4 ERROR - " + e.getMessage());
		}
	}
	
	
	/**
	 * Calculation of the current yield of the symbol
	 * @param wsl
	 * @param scs
	 */
	public void processSymbolStatus(List<WatchSymbol> wsl, List<SymbolCurrentState> scs) {
		
		int res;
		int res2;
		int res3;
		
		String action = "";
		List<SymbolStatus> result = new ArrayList<>();
		
		for(SymbolCurrentState c: scs) {
			
			Optional<WatchSymbol> ws = wsl.stream().filter(t -> t.getSymbol().equalsIgnoreCase(c.getSymbol())).findFirst();
			if(ws.isEmpty()) {
				log.warn("#5 Data for symbol " + c.getSymbol() + " not found.");
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
			    
			    if(res2 == 0) {
			    	action = "N/A";
			    }
			    
			    log.info("\n" + c.getSymbol() + "  Price:" + c.getPrice() + 
			    		"  QDivAmt: " + ws.get().getQuoterlyDividendAmount() + 
			    		"  Yield: " + yield + 
			    		"  \n         Upper Yield: " + ws.get().getUpperYield() +
			    		"  Lower Yield: " + ws.get().getLowerYield() +
			    		"  Quoter of Yield Range: " + quoterOfUpperYield +
			    		"  Allowed to Buy Yield: " + allowToBuyYield +
			    		"  Action: " + action);
			    
			    /* Clean SYMBOL_STATUS table and populate with symbols that have Action = "Buy" only */
		    	SymbolStatus symbolStatus = new SymbolStatus();
		    	symbolStatus.setSymbol(c.getSymbol());
			    symbolStatus.setCurrentPrice(c.getPrice());
			    symbolStatus.setCurrentYield(yield);
			    symbolStatus.setStatus("Active");
			    symbolStatus.setRecomendedAction(action);
			    
			    LocalDateTime ldt = LocalDateTime.now();
			    symbolStatus.setUpdatedOn(ldt);
			    
			    result.add(symbolStatus);
			    
			}
		}
		
        /* Clean table from the records  */
		symbolService.cleanSymbolStatus();
		log.info("Cleaning table done.");
		
		/* Saving calculations into table  */
		Iterable<SymbolStatus> j = symbolService.saveSymbolStatuses(result);
		log.info("SYMBOL_STATUS table populated with new data.");
	}
	
}