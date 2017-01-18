package com.budget.service.transaction.conversion;

import com.budget.model.dto.Currency;

public interface CurrencyConverter {
	
	public double convert(Currency from, Currency to, double amount ) throws CurrencyConvertionException;

}
