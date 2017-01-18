package com.budget.service.predicate.my;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyIterable.emptyIterable;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNot.not;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.budget.model.dto.Account;
import com.budget.model.dto.Currency;
import com.budget.model.dto.Customer;
import com.budget.model.repo.AccountRepository;
import com.budget.model.repo.CurrencyRepository;
import com.budget.model.repo.CustomerRepository;

@Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MyAccountPredicateBuilderTest {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	private Customer customer;
	private Account account1;
	private Account account2;

	@Before
	public void setUp() throws Exception {
		accountRepository.deleteAll();
		customerRepository.deleteAll();

		Currency cur = new Currency("TRY", "Turkish New Lira", "TL");
		currencyRepository.save(cur);
		
		customer = new Customer("c1", "c1", "c1@c1.com");
		account1 = new Account("c1_a1", cur, BigDecimal.valueOf(100), customer);
		account2 = new Account("c1_a2", cur, BigDecimal.valueOf(101), customer);
		customerRepository.save(customer);
	}

	@Test
	public void findAll_GivenAccountNameThatExistsForAllAccounts_MustReturnAllAccounts() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.and(new EqualPredicate("name", "c1_"));

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, containsInAnyOrder(account1, account2));
	}

	@Test
	public void findAll_GivenSpecificAccountName_MustReturnSpecificAccount() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.with(new EqualPredicate("name", "c1_a1")).and();

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, contains(account1));
		assertThat(results, not(contains(account2)));
	}

	@Test
	public void findAll_GivenAccountNameThatExistsForAllAccountsAndCustomerId_MustReturnAllAccounts() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.and(new EqualPredicate("name", "c1_")).and(new EqualPredicate("customer.id", customer.getId()));

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, containsInAnyOrder(account1, account2));
	}

	@Test
	public void findAll_GivenSpecificAccountNameAndCustomerId_MustReturnSpecificAccount() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.with(new EqualPredicate("name", "c1_a1")).with(new EqualPredicate("customer.id", customer.getId()))
				.and();

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, contains(account1));
		assertThat(results, not(contains(account2)));
	}

	@Test
	public void findAll_GivenSpecificAccountNameOrCustomerId_MustReturnAllAccounts() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.and(new EqualPredicate("name", "c1_a1")).or(new EqualPredicate("customer.id", customer.getId()));
		
		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, containsInAnyOrder(account1, account2));
	}

	@Test
	public void findAll_GivenInvalidCustomerId_mustReturnNoResult() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.with(new EqualPredicate("customer.id", -100)).and();

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, emptyIterable());
	}

	@Test
	public void findAll_GivenSpecificAccountAmount_mustReturnSpecificAccount() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.with(new EqualPredicate("amount", account1.getAmount())).and();

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, contains(account1));
		assertThat(results, not(contains(account2)));
	}

	@Test
	public void findAll_GivenAccountAmountGreaterThanOne_mustReturnThatAccount() {
		MyPredicateBuilder<Account> builder = new MyPredicateBuilder<>(Account.class)
				.with(new GreaterThanPredicate("amount", 100)).and();

		Iterable<Account> results = accountRepository.findAll(builder.build());
		assertThat(results, contains(account2));
		assertThat(results, not(contains(account1)));
	}

}
