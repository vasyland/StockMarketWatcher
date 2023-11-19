package com.stock.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;

@Repository
public class SymbolRepository {

	@PersistenceContext
	EntityManager entityManager = null;
	
	public List<String> getSymbolForProcessing() {
		//List<String> r = new ArrayList();
//	    TypedQuery<String> sql = entityManager.createQuery("SELECT symbol FROM scenario_details "
//	    		+ "UNION ALL "
//	    		+ "SELECT SYMBOL FROM watch_symbol) T "
//	    		+ "GROUP BY symbol", java.lang.String.class);
	    
//	    TypedQuery<Symbol> sql2 = entityManager.createQuery("SELECT symbol FROM scenario_details", Symbol.class);

//		@Query(value="SELECT SYMBOL FROM ("
//		+ "SELECT symbol FROM scenario_details "
//		+ "UNION ALL "
//		+ "SELECT SYMBOL FROM watch_symbol) T "
//		+ "GROUP BY symbol", nativeQuery=true);
	    
		Query sql3 = entityManager.createNativeQuery("SELECT SYMBOL FROM ("
				+ "SELECT symbol FROM watch_symbol "
				+ "UNION ALL "
				+ "SELECT symbol FROM scenario_details) "
				+ "T GROUP BY symbol");
		
	    return sql3.getResultList();
	}

//	List<Symbol> getSymbolsForProcessing();
}
