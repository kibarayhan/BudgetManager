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
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class CustomerRepositoryTest {

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	private Customer customer;
	private String customerName = "customer";
	private String emailAddress = "c1@c1.com";
	
	@Before
    public void setUp() throws Exception {
		accountRepository.deleteAll();
		customerRepository.deleteAll();
		currencyRepository.deleteAll();

		Currency cur = new Currency("TRY", "Turkish New Lira", "TL");
		currencyRepository.save(cur);
		
		customer = new Customer(customerName, customerName, emailAddress);

		new Account("c1_a1", cur, BigDecimal.valueOf(100), customer);
		new Account("c1_a2", cur, BigDecimal.valueOf(101), customer);
		
		customer = customerRepository.save(customer);
    }

	@Test
	public void findByNameContains_GivenPartialOfCustomerName_ShouldReturnSpecificCustomer() {
		Optional<Customer> realAccount = customerRepository.findByNameContains(customerName.substring(0, 3));
		assertEquals(true, realAccount.isPresent());
		assertEquals(customer, realAccount.get());
	}

	@Test
	public void findByNameContains_GivenWrongCustomerName_ShouldReturnNoCustomer() {
		Optional<Customer> realAccount = customerRepository.findByNameContains(customerName + "a");
		assertEquals(false, realAccount.isPresent());
	}

	@Test
	public void getByCustomerName_GivenCustomerName_ShouldReturnSpecificCustomer() {
		Optional<Customer> realAccount = customerRepository.getByCustomerName(customerName);
		assertEquals(customer, realAccount.get());
	}

	@Test
	public void getBySearchTerm_GivenAccountName_ShouldReturnSpecificCustomer() {
		List<Customer> realAccount = customerRepository.getBySearchTerm(customerName);
		assertEquals(customer, realAccount.get(0));
	}

	@Test
	public void getBySearchTerm_GivenAccountEMail_ShouldReturnSpecificCustomer() {
		List<Customer> realAccount = customerRepository.getBySearchTerm(emailAddress);
		assertEquals(customer, realAccount.get(0));
	}

	@Test
	public void getBySearchTerm_GivenWrongAccountEMail_ShouldReturnZeroAccount() {
		List<Customer> realAccount = customerRepository.getBySearchTerm("nonexisting@email.com");
		assertEquals(0, realAccount.size());
	}
	
}
