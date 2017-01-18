package com.budget.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.budget.model.dto.Currency;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {

	Currency findByCode(String code);
	Currency findByName(String name);
	
	@Query("SELECT c FROM Currency c where c.isDefault = true")
	Currency getDefaultCurrency();
	
}
