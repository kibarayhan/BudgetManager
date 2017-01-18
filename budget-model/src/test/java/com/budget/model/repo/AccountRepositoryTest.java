package com.budget.model.repo;

import com.budget.model.dto.Account;
import com.budget.model.dto.Currency;
import com.budget.model.dto.Customer;
import com.budget.model.repo.AccountRepository;
import com.budget.model.repo.CurrencyRepository;
import com.budget.model.repo.CustomerRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
// @SpringApplicationConfiguration(classes = BudgetModelApplication.class)
public class AccountRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	private Customer customer;
	private Account account;
	private String account1Name = "customer_account1";
	private String account2Name = "customer_account2";
	private String account3Name = "customer_account3";
	private String accountNamePrefix = "customer_account";


	@Before
	public void setUp() throws Exception {
		customerRepository.deleteAll();
		currencyRepository.deleteAll();

		customer = new Customer("c1", "c1", "c1@c1.com");

		Currency cur = new Currency("TRY", "Turkish New Lira", "TL");
		currencyRepository.save(cur);

		account = new Account(account1Name, cur, BigDecimal.valueOf(100), customer);
		new Account(account2Name, cur, BigDecimal.valueOf(101), customer);
		new Account(account3Name, cur, BigDecimal.valueOf(102), customer);

		customerRepository.save(customer);
	}

	@Test
	public void getByCustomerIdAndAccountName_ShouldReturnSpecificAccount() {
		Account a1Ret = accountRepository.getByCustomerIdAndAccountName(customer.getId(), account.getName());
		assertEquals("findByCustomerIdAndAccountName failed", account.getId(), a1Ret.getId());
	}

	@Test
	public void getByCustomerIdAndStartsWithAccountName_ShouldReturn3Accounts() {
		List<Account> a1RetLists = accountRepository.getByCustomerIdAndStartsWithAccountName(customer.getId(), 
				accountNamePrefix);
		assertEquals(3, a1RetLists.size());
	}

	@Test
	public void findAll_ShouldReturn3Accounts() {
		List<Account> accounts = accountRepository.findAll();
		assertEquals(3, accounts.size());
	}

	@Test
	public void getCountByCustomerId_ShouldReturn3Accounts() {
		int realCount = accountRepository.getCountByCustomerId(customer.getId());
		assertEquals(3, realCount);
	}
}
