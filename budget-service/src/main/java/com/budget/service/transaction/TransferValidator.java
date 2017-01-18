package com.budget.service.transaction;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.budget.model.dto.Account;
import com.budget.model.dto.Customer;
import com.budget.model.dto.Transaction;
import com.budget.model.repo.AccountRepository;
import com.budget.model.repo.CustomerRepository;

@Service
@Qualifier("transferValidator")
public class TransferValidator implements TransactionValidator {

	private final CustomerRepository customerRepository;
	private final AccountRepository accountRepository;

	@Autowired
	public TransferValidator(CustomerRepository customerRepository, AccountRepository accountRepository) {
		this.customerRepository = customerRepository;
		this.accountRepository = accountRepository;
	}

	@Override
	public Transaction validateTransaction(String fromCustomerName, String fromAccountName, String toCustomerName,
			String toAccountName, double transferAmount, String transferDescription)
			throws TransactionInvalidException {
		
		staticValidator(fromCustomerName, fromAccountName, toCustomerName, toAccountName);

		Optional<Customer> fromCustomer = customerRepository.findByName(fromCustomerName);

		Account fromAccount = accountRepository.getByCustomerIdAndAccountName(fromCustomer.get().getId(),
				fromAccountName);

		if (fromAccount.getAmount().doubleValue() < transferAmount) {
			throw new TransactionInvalidException("From Account doesn't have valid amount.");
		}
		
		Optional<Customer> toCustomer = customerRepository.findByName(toCustomerName);
		if (!toCustomer.isPresent()) {
			throw new TransactionInvalidException("to Customer is not valid.");
		}

		Account toAccount = accountRepository.getByCustomerIdAndAccountName(toCustomer.get().getId(), toAccountName);
		
		return new Transaction(fromCustomer.get(), fromAccount, toCustomer.get(), toAccount,
				BigDecimal.valueOf(transferAmount));
	}

	private void staticValidator(String fromCustomerName, String fromAccountName, String toCustomerName,
			String toAccountName) throws TransactionInvalidException {
		
		if (StringUtils.isEmpty(fromCustomerName) || StringUtils.isEmpty(fromAccountName)) {
			throw new TransactionInvalidException("From Customer is not valid.");
		}

		if (fromCustomerName.equals(toCustomerName) && fromAccountName.equals(toAccountName)) {
			throw new TransactionInvalidException("From and To accounts can not be the same.");
		}
	}

}
