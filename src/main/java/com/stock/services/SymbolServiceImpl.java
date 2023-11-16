package com.stock.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import com.stock.model.WatchSymbol;
import com.stock.repositories.WatchSymbolRepository;

@Service
public class SymbolServiceImpl implements SymbolService {

	private static final Logger log = LogManager.getLogger(SymbolServiceImpl.class);

	private WatchSymbolRepository watchSymbolRepository;

	public SymbolServiceImpl(WatchSymbolRepository watchSymbolRepository) {
		super();
		this.watchSymbolRepository = watchSymbolRepository;
	}

	@Override
	public List<WatchSymbol> getWatchSymbolsData() {
		Iterable<WatchSymbol> p = watchSymbolRepository.findAll();
		List<WatchSymbol> ws = Streamable.of(p).toList();
		log.info("Number of selected watch symbols is: ", ws.size());
		return ws;
	}

}
