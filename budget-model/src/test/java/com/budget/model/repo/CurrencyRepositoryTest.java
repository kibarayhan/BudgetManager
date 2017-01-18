package com.budget.model.repo;

import com.budget.model.dto.Currency;
import com.budget.model.repo.AccountRepository;
import com.budget.model.repo.CreditCardAccountRepository;
import com.budget.model.repo.CurrencyRepository;
import com.budget.model.repo.CustomerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CurrencyRepositoryTest {
	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CreditCardAccountRepository creditCardAccountRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	private Currency defaultCurrency;
	private Currency AUD;
	private Currency BGN;
	
	@Before
    public void setUp() throws Exception {
		creditCardAccountRepository.deleteAll();
		accountRepository.deleteAll();
		customerRepository.deleteAll();
		currencyRepository.deleteAll();

		defaultCurrency = new Currency("TRY", "Turkish New Lira", "TL");
		defaultCurrency.setDefault(true);

		currencyRepository.save(defaultCurrency);

		AUD = new Currency("AUD", "Australian Dollar", "$");
		currencyRepository.save(AUD);
		BGN = new Currency("BGN", "Bulgarian Lev", "лв");
		currencyRepository.save(BGN);
		Currency BRL = new Currency("BRL", "Brazilian Real", "R$");
		currencyRepository.save(BRL);
    }

	@Test
	public void findAll_ShouldReturn4Currency() {
		List<Currency> currencies = currencyRepository.findAll();
		assertEquals(4, currencies.size());
	}

	@Test
	public void getDefaultCurrency_ShouldReturn1Currency() {
		Currency realDefault = currencyRepository.getDefaultCurrency();
		assertEquals(defaultCurrency, realDefault);
	}

	@Test
	public void findByCode_ShouldReturn1Currency() {
		Currency real = currencyRepository.findByCode("AUD");
		assertEquals(AUD, real);
	}
	
	@Test
	public void findByName_ShouldReturn1Currency() {
		Currency real = currencyRepository.findByName("Bulgarian Lev");
		assertEquals(BGN, real);
	}
	
}
