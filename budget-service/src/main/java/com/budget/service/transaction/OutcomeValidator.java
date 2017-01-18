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
@Qualifier("outcomeValidator")
public class OutcomeValidator implements TransactionValidator {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;

	@Autowired
	public OutcomeValidator(CustomerRepository customerRepository, AccountRepository accountRepository) {
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Transaction validateTransaction(String fromCustomerName, String fromAccountName, String toCustomerName,
			String toAccountName, double transferAmount, String transferDescription)
			throws TransactionInvalidException {
		Optional<Customer> fromCustomer = customerRepository.findByName(fromCustomerName);

		Account fromAccount = accountRepository.getByCustomerIdAndAccountName(fromCustomer.get().getId(),
				fromAccountName);
		
		if (fromAccount.getAmount().doubleValue() < transferAmount) {
			throw new TransactionInvalidException("From Account doesn't have valid amount.");
		}
		
		return new Transaction(fromCustomer.get(), fromAccount, null, null, BigDecimal.valueOf(transferAmount));
	}

}
