package com.stock.tasks;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.stock.model.WatchSymbol;
import com.stock.services.SymbolService;
import com.stock.yahoo.AllSymbolsData;
import com.stock.yahoo.SymbolCurrentState;

@Component
public class Test {
	
	private static final Logger log = LogManager.getLogger(Test.class);
	
	@Value("${symbol-list}")
	private String symbolList;
	
	@Value("${console-symbol}")
	private String consoleSymbol;
	
	/* Services */
	private AllSymbolsData allSymbolsData;
	private SymbolService symbolService;
	
	private List<WatchSymbol> wsl;
	
	public Test(AllSymbolsData allSymbolsData, SymbolService symbolService) {
		super();
		this.allSymbolsData = allSymbolsData;
		this.symbolService = symbolService;
		
		wsl = symbolService.getWatchSymbolsData();
	}



	/* Every 5 seconds */
	@Scheduled(cron = "${cron-string}")
	public void everyFiveSeconds() {
//		System.out.println("Periodic task: " + new Date());
//		System.out.println("Dude => " + symbolList);
		
//		List<WatchSymbol> wsl = symbolService.getWatchSymbolsData();
		this.wsl.stream().forEach(t -> {
			System.out.println(t.getSymbol());
		});		
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		
		try {
			
			String destFile = "C:\\tmp\\marketcap-data\\stock-2023-11-15.txt";
			try(
				FileWriter fileWriter = new FileWriter(destFile, true);
				PrintWriter pw = new PrintWriter(fileWriter);
				) {
				
				List<Future<SymbolCurrentState>> data = allSymbolsData.getCurrentData(symbolList);
	
				for (int y = 0; y < data.size(); y++) {
					Future<SymbolCurrentState> future = data.get(y);
					SymbolCurrentState p = future.get();
					BigDecimal outstandingShares = p.getMarketCap().divide(p.getPrice(), MathContext.DECIMAL32);
					if(p.getSymbol().equalsIgnoreCase(consoleSymbol)) 
						System.out.printf("%-8S | %6.2f | %5.2f | %6.2f | %12.6f | %n", p.getSymbol(), p.getPrice(),
								p.getChangedPercent(), p.getMarketCap(), outstandingShares);
					
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
			
			} catch (IOException e) {
				e.printStackTrace();
			}
			

		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
