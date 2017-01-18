package com.budget.model.repo;

import com.budget.model.dto.Account;
import com.budget.model.dto.CreditCardAccount;
import com.budget.model.dto.Currency;
import com.budget.model.dto.Customer;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CreditCardAccountRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CreditCardAccountRepository creditCardAccountRepository;

	@Autowired
	private CurrencyRepository currencyRepository;
	
	private Customer customer;
	private Account account1;
	private Account account2;
	private List<Account> accountList;
	
	private CreditCardAccount creditCardAccount;

	@Before
	public void setUp() throws Exception {
		creditCardAccountRepository.deleteAll();
		accountRepository.deleteAll();
		customerRepository.deleteAll();
		currencyRepository.deleteAll();

		customer = new Customer("c1", "c1", "c1@c1.com");
		creditCardAccount = new CreditCardAccount(BigDecimal.valueOf(100), 4);

		Currency cur = new Currency("TRY", "Turkish New Lira", "TL");
		currencyRepository.save(cur);
		
		accountList = new ArrayList<>();
		account1 = new Account("c1_a1", cur, BigDecimal.valueOf(100), customer, creditCardAccount);
		accountList.add(account1);
		account2 = new Account("c1_a2", cur, BigDecimal.valueOf(100), customer);
		accountList.add(account2);
		
		customerRepository.save(customer);
	}

	@Test
	public void findAll_GetAllAccounts_ShouldReturnAllAccounts() {
		List<Account> accounts = accountRepository.findAll();
		assertEquals(accountList.size(), accounts.size());
	}

	@Test
	public void findAll_GetAllCreditCardsAccounts_ShouldReturn1CreditCardAccount(){
		List<CreditCardAccount> creditCardAccount = creditCardAccountRepository.findAll();
		assertEquals(1, creditCardAccount.size());
	}
	
	@Test
	public void getByCustomerIdAndAccountName_accountDoesNotHaveTheCreditCardAccount_ShouldReturnNullAsCreditCardAccount() {
		Account a2 = accountRepository.getByCustomerIdAndAccountName(customer.getId(), account2.getName());
		assertEquals(account2, a2);
		assertNull(a2.getCreditCardAccount());
	}

	@Test
	public void getByCustomerIdAndAccountName_accountHasTheCreditCardAccountTest_ShouldReturn1CreditCardAccount() {
		Account a1 = accountRepository.getByCustomerIdAndAccountName(customer.getId(), account1.getName());
		assertEquals(account1, a1);
		assertEquals(creditCardAccount, a1.getCreditCardAccount());
	}

	@Test
	public void getByAccountName_GettingCreditCardAccount_ShouldReturnSpecificCreditCardAccount() {
		Optional<CreditCardAccount> realCreditCardAccount = creditCardAccountRepository.getByAccountName(account1.getName());
		assertEquals(creditCardAccount, realCreditCardAccount.get());
	}

	@Test
	public void getByAccountId_GettingCreditCardAccount_ShouldReturnSpecificCreditCardAccount() {
		Optional<CreditCardAccount> realCreditCardAccount = creditCardAccountRepository.getByAccountId(account1.getId());
		assertEquals(creditCardAccount, realCreditCardAccount.get());
	}
}
