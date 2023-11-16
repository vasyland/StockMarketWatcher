package com.stock.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stock.model.SymbolStatus;


@Repository
public interface SymbolStatusRepository extends CrudRepository<SymbolStatus, String>{

}
