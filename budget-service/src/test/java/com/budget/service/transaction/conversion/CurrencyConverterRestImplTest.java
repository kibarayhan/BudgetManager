package com.budget.service.transaction.conversion;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.budget.model.dto.Currency;
import com.budget.service.transaction.conversion.rest.ExchangeDao;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CurrencyConverterRestImplTest {

	@Mock
	private ExchangeDao exchangeDao;
	
	private CurrencyConverter currencyConverter;
	
	private Currency fromCurrency;
	private Currency toCurrency;

	
	@Before
	public void setUp() throws Exception {
		currencyConverter = new CurrencyConverterRestImpl(exchangeDao);
	}

	@Test
	public void convert_GivenSameCurrencyAsFromTo_ShouldReturnSameAmount() throws CurrencyConvertionException {
		double amount = 15.0;
		fromCurrency = new Currency("TRY", "Turkish New Lira", "TL");
		toCurrency = new Currency("TRY", "Turkish New Lira", "TL");
		double realAmount = currencyConverter.convert(fromCurrency, toCurrency, amount);
		assertEquals(amount, realAmount, 0);
	}
	
	
	@Test
	public void convert_GivenRateAs1_ShouldReturnSameAmount() throws CurrencyConvertionException {
		double amount = 15.0;
		fromCurrency = new Currency("TRY", "Turkish New Lira", "TL");
		toCurrency = new Currency("Cur1", "Turkish New Lira", "Cur1");
		// Matchers.anyString()
		when(exchangeDao.getRate(fromCurrency.getCode(), toCurrency.getCode())).thenReturn(new Double(1));
		
		double realAmount = currencyConverter.convert(fromCurrency, toCurrency, amount);
		assertEquals(amount, realAmount, 0);
	}

	@Test
	public void convert_GivenRateAs2_ShouldReturnDoubleAmount() throws CurrencyConvertionException {
		double amount = 15.0;
		int multiplier = 2;
		fromCurrency = new Currency("TRY", "Turkish New Lira", "TL");
		toCurrency = new Currency("Cur1", "Turkish New Lira", "Cur1");
		// Matchers.anyString()
		when(exchangeDao.getRate(fromCurrency.getCode(), toCurrency.getCode())).thenReturn(new Double(multiplier));
		
		double realAmount = currencyConverter.convert(fromCurrency, toCurrency, amount);
		assertEquals(amount * multiplier, realAmount, 0);
	}
	
	@Test (expected = CurrencyConvertionException.class)
	public void convert_GivenRateAsNull_ShouldReturnException() throws CurrencyConvertionException {
		double amount = 15.0;
		fromCurrency = new Currency("TRY", "Turkish New Lira", "TL");
		toCurrency = new Currency("Cur1", "Turkish New Lira", "Cur1");
		// Matchers.anyString()
		when(exchangeDao.getRate(fromCurrency.getCode(), toCurrency.getCode())).thenReturn(null);
		
		currencyConverter.convert(fromCurrency, toCurrency, amount);
	}
	
}
