package com.budget.service.transaction.conversion;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.budget.model.dto.Currency;
import com.budget.service.transaction.conversion.rest.ExchangeDao;

@Service
public class CurrencyConverterRestImpl implements CurrencyConverter {

	private static final Logger logger = LoggerFactory.getLogger(CurrencyConverterRestImpl.class);

	private final ExchangeDao exchangeDao;

	@Autowired
	public CurrencyConverterRestImpl(ExchangeDao exchangeDao) {
		this.exchangeDao = exchangeDao;
	}

	public double convert(Currency from, Currency to, double amount) throws CurrencyConvertionException {
		if (from.equals(to)) {
			return amount;
		}

		Double rateValue = exchangeDao.getRate(from.getCode(), to.getCode());

		if (rateValue != null) {
			return amount * rateValue;
		}

		logger.error("Invalid rate");
		throw new CurrencyConvertionException("Invalid rate");

	}
}
