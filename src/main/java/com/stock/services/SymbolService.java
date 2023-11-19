package com.stock.services;

import java.util.List;

import com.stock.model.WatchSymbol;

public interface SymbolService {

	List<String> getSymbols();
	List<WatchSymbol> getWatchSymbolsData();
}
