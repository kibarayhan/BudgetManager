package com.budget.service.transaction;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.budget.model.dto.Account;
import com.budget.model.dto.Customer;
import com.budget.model.dto.Transaction;
import com.budget.model.repo.AccountRepository;
import com.budget.model.repo.CustomerRepository;

@Service
@Qualifier("incomeValidator")
public class IncomeValidator implements TransactionValidator {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;

	@Autowired
	public IncomeValidator(CustomerRepository customerRepository, AccountRepository accountRepository) {
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Transaction validateTransaction(String fromCustomerName, String fromAccountName, String toCustomerName,
			String toAccountName, double transferAmount, String transferDescription)
			throws TransactionInvalidException {
		Optional<Customer> toCustomer = customerRepository.findByName(toCustomerName);

		Account toAccount = accountRepository.getByCustomerIdAndAccountName(toCustomer.get().getId(), toAccountName);

		return new Transaction(null, null, toCustomer.get(), toAccount, BigDecimal.valueOf(transferAmount));

	}

}
