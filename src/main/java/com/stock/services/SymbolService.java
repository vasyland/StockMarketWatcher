package com.stock.services;

import java.util.List;

import com.stock.model.SymbolStatus;
import com.stock.model.WatchSymbol;

public interface SymbolService {

	List<String> getSymbols();
	List<WatchSymbol> getWatchSymbolsData();
	void cleanSymbolStatus();
	Iterable<SymbolStatus> saveSymbolStatuses(List<SymbolStatus> s);
}
