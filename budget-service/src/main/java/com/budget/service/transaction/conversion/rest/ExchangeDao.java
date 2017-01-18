package com.budget.service.transaction.conversion.rest;

public interface ExchangeDao {
	Double getRate(String from, String to);
}
