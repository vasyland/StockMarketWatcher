package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.stock.model.WatchSymbol;

@Repository
public interface WatchSymbolRepository extends CrudRepository<WatchSymbol, String> {
}
