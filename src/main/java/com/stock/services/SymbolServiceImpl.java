package com.stock.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.stock.model.WatchSymbol;
import com.stock.repositories.SymbolRepository;
import com.stock.repositories.WatchSymbolRepository;

@Service
public class SymbolServiceImpl implements SymbolService {

	private static final Logger log = LogManager.getLogger(SymbolServiceImpl.class);

	private SymbolRepository symbolRepository;
	private WatchSymbolRepository watchSymbolRepository;

	public SymbolServiceImpl(SymbolRepository symbolRepository, WatchSymbolRepository watchSymbolRepository) {
		super();
		this.symbolRepository = symbolRepository;
		this.watchSymbolRepository = watchSymbolRepository;
	}

	/* 
	 * Get a list of symbols that we have dividend yields set
	 * and list of symbols entered by users that don't have dividend yields set. 
	 **/
	@Override
	public List<String> getSymbols() {
		return symbolRepository.getSymbolForProcessing();
	}
	
	/*
	 * Get a list of symbols with dividend yields defined.
	 */
	@Override
	public List<WatchSymbol> getWatchSymbolsData() {
		Iterable<WatchSymbol> p = watchSymbolRepository.findAll();
		List<WatchSymbol> ws = Streamable.of(p).toList();
		log.info("Number of selected watch symbols is: ", ws.size());
		return ws;
	}

	
	
}
