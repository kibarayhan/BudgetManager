package com.budget.service.transaction;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertNotNull;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.budget.model.dto.Account;
import com.budget.model.dto.Currency;
import com.budget.model.dto.Customer;
import com.budget.model.dto.Transaction;
import com.budget.model.repo.AccountRepository;
import com.budget.model.repo.CurrencyRepository;
import com.budget.model.repo.CustomerRepository;
import com.budget.testutil.ThrowableCaptor;

/**
 * Created by a579295 on 18.09.2016.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class TransactionManagerImplTest {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	CurrencyRepository currencyRepository;

	@Autowired
	MyTransactionManager transactionManager;

	private Customer fromCustomer;
	private Account fromAccount;
	private Currency currency;

	private Account toAccount;
	private String fromAccountName = "from_account";
	private String toAccountName = "to_account";

	@Rule
	public final ExpectedException thrown = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		accountRepository.deleteAll();
		customerRepository.deleteAll();
		currencyRepository.deleteAll();

		fromCustomer = new Customer("from_customer_name", "password", "emailAddress@email.com");

		currency = new Currency("TRY", "Turkish New Lira", "TL");
		currencyRepository.save(currency);

		fromAccount = new Account(fromAccountName, currency, BigDecimal.valueOf(100), fromCustomer);
		toAccount = new Account(toAccountName, currency, BigDecimal.valueOf(200), fromCustomer);

		customerRepository.save(fromCustomer);
	}

	@Test
	public void validateTransaction_GivenAllParameters_ShouldValid() throws TransactionInvalidException {
		Transaction tr = transactionManager.validateTransaction(fromCustomer.getName(), fromAccount.getName(),
				fromCustomer.getName(), toAccount.getName(), 50, "description");

		assertNotNull(tr);
	}

	@Test
	public void validateTransaction_GivenFromAsNull_ShouldValidAsIncomeTransaction()
			throws TransactionInvalidException {
		Transaction tr = transactionManager.validateTransaction(null, null, fromCustomer.getName(), toAccount.getName(),
				300, "description");

		assertNotNull(tr);
	}

	@Test
	public void validateTransaction_GivenToAsNull_ShouldValidAsOutcomeTransaction() throws TransactionInvalidException {
		Transaction tr = transactionManager.validateTransaction(fromCustomer.getName(), fromAccount.getName(), null,
				null, 50, "description");

		assertNotNull(tr);
	}

	@Test
	public void validateTransaction_GivenEmptyDescription_ShouldNotValid() throws TransactionInvalidException {
		// method 1
		// thrown.expect(TransactionInvalidException.class);
		// thrown.expectMessage(containsString("Description can not be empty."));
		// transactionManager.validateTransaction(fromCustomer.getName(),
		// fromAccount.getName(),
		// fromCustomer.getName(), toAccount.getName(), 300, "");

		// method 2
//		 catchException(transactionManager).validateTransaction(fromCustomer.getName(),
//		 fromAccount.getName(),
//		 fromCustomer.getName(), toAccount.getName(), 300, "");
//		
//		 assertThat(caughtException(), allOf(
//		 is(TransactionInvalidException.class),
//		 hasMessage("Description can not be empty."), hasNoCause() ) );

		// method 3
		// act
		// Throwable actual = ThrowableCaptor.captureThrowable(transactionManager::doIt);
		Throwable actual = ThrowableCaptor
				.captureThrowable(() -> transactionManager.validateTransaction(fromCustomer.getName(),
						fromAccount.getName(), fromCustomer.getName(), toAccount.getName(), 300, ""));
		// assert
		assertThat(actual).isInstanceOf(TransactionInvalidException.class).hasMessage("Description can not be empty.");
	}

	@Test
	public void validateTransaction_GivenNullDescription_ShouldNotValid() throws TransactionInvalidException {
		// method 1
//		thrown.expect(TransactionInvalidException.class);
//		thrown.expectMessage(containsString("Description can not be empty."));
//
//		transactionManager.validateTransaction(fromCustomer.getName(), fromAccount.getName(), fromCustomer.getName(),
//				toAccount.getName(), 300, null);

		// method 3
		// act
		Throwable actual = ThrowableCaptor
				.captureThrowable(() -> transactionManager.validateTransaction(fromCustomer.getName(),
						fromAccount.getName(), fromCustomer.getName(), toAccount.getName(), 300, null));
		// assert
		assertThat(actual).isInstanceOf(TransactionInvalidException.class).hasMessage("Description can not be empty.");
	}

	@Test
	public void validateTransaction_GivenSameCustomerAndAccounts_ShouldNotValid() throws TransactionInvalidException {
		// method 1
//		thrown.expect(TransactionInvalidException.class);
//		thrown.expectMessage(containsString("From and To accounts can not be the same."));
//
//		transactionManager.validateTransaction(fromCustomer.getName(), fromAccount.getName(), fromCustomer.getName(),
//				fromAccount.getName(), 300, "description");

		// method 3
		// act
		Throwable actual = ThrowableCaptor
				.captureThrowable(() -> transactionManager.validateTransaction(fromCustomer.getName(), 
						fromAccount.getName(), fromCustomer.getName(),
						fromAccount.getName(), 300, "description"));
		// assert
		assertThat(actual).isInstanceOf(TransactionInvalidException.class).
			hasMessage("From and To accounts can not be the same.");

	}

	@Test
	public void validateTransaction_GivenZeroTransferAmount_ShouldNotValid() throws TransactionInvalidException {
		// method 1
//		thrown.expect(TransactionInvalidException.class);
//		thrown.expectMessage(containsString("Transfer amount is not valid. Can not be zero or less."));
//
//		transactionManager.validateTransaction(fromCustomer.getName(), fromAccount.getName(), fromCustomer.getName(),
//				toAccount.getName(), 0, "description");

		// method 2
		assertThatThrownBy(() ->transactionManager.validateTransaction(fromCustomer.getName(), 
				fromAccount.getName(), fromCustomer.getName(),
				toAccount.getName(), 0, "description") ).
				isInstanceOf(TransactionInvalidException.class).
				hasMessage("Transfer amount is not valid. Can not be zero or less.");
		
		// method 3
		// act
//		Throwable actual = ThrowableCaptor
//				.captureThrowable(() -> transactionManager.validateTransaction(fromCustomer.getName(), 
//						fromAccount.getName(), fromCustomer.getName(),
//						toAccount.getName(), 0, "description"));
//		// assert
//		assertThat(actual).isInstanceOf(TransactionInvalidException.class).
//			hasMessage("Transfer amount is not valid. Can not be zero or less.");
	}

	@Test
	public void testName() {

	}
}
