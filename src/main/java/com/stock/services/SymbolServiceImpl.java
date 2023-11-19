package com.stock.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.stock.model.SymbolStatus;
import com.stock.model.WatchSymbol;
import com.stock.repositories.SymbolRepository;
import com.stock.repositories.SymbolStatusRepository;
import com.stock.repositories.WatchSymbolRepository;

@Service
public class SymbolServiceImpl implements SymbolService {

	private static final Logger log = LogManager.getLogger(SymbolServiceImpl.class);

	private SymbolRepository symbolRepository;
	private WatchSymbolRepository watchSymbolRepository;
	private SymbolStatusRepository symbolStatusRepository;

	public SymbolServiceImpl(SymbolRepository symbolRepository, WatchSymbolRepository watchSymbolRepository,
			SymbolStatusRepository symbolStatusRepository) {
		super();
		this.symbolRepository = symbolRepository;
		this.watchSymbolRepository = watchSymbolRepository;
		this.symbolStatusRepository = symbolStatusRepository;
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

	/**
	 * Delete all records from SYMBOL_STATUS table
	 */
	@Override
	public void cleanSymbolStatus() {
		symbolStatusRepository.deleteAll();
	}

	@Override
	public Iterable<SymbolStatus> saveSymbolStatuses(List<SymbolStatus> s) {
		return symbolStatusRepository.saveAll(s);
	}
	
}
