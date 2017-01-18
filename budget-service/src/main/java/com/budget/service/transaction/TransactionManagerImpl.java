package com.budget.service.transaction;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.budget.model.dto.Transaction;
import com.budget.service.transaction.conversion.CurrencyConverter;
import com.budget.service.transaction.conversion.CurrencyConvertionException;

/**
 * Created by a579295 on 18.09.2016.
 */
@Service
public class TransactionManagerImpl implements MyTransactionManager {

	private final TransactionValidator transferValidator;

	private final TransactionValidator incomeValidator;

	private final TransactionValidator outcomeValidator;
	
	private final CurrencyConverter currencyConverter;

	public TransactionManagerImpl(@Qualifier("transferValidator") TransactionValidator transferValidator,
			@Qualifier("incomeValidator") TransactionValidator incomeValidator,
			@Qualifier("outcomeValidator") TransactionValidator outcomeValidator, 
			CurrencyConverter currencyConverter) {
		this.transferValidator = transferValidator;
		this.incomeValidator = incomeValidator;
		this.outcomeValidator = outcomeValidator;
		this.currencyConverter = currencyConverter;
	}

	@Override
	public Transaction validateTransaction(String fromCustomerName, String fromAccountName, String toCustomerName,
			String toAccountName, double transferAmount, String transferDescription)
			throws TransactionInvalidException {

		staticValidation(transferAmount, transferDescription);

		if (StringUtils.isEmpty(fromCustomerName) || StringUtils.isEmpty(fromAccountName)) {
			return incomeValidator.validateTransaction(null, null, toCustomerName, toAccountName,
					transferAmount, transferDescription);
		}

		if (StringUtils.isEmpty(toCustomerName) || StringUtils.isEmpty(toAccountName)) {
			return outcomeValidator.validateTransaction(fromCustomerName, fromAccountName, null,
					null, transferAmount, transferDescription);
		}

		return transferValidator.validateTransaction(fromCustomerName, fromAccountName, toCustomerName, toAccountName,
				transferAmount, transferDescription);
	}

	private void staticValidation(double transferAmount, String transferDescription)
			throws TransactionInvalidException {
		if (StringUtils.isEmpty(transferDescription)) {
			throw new TransactionInvalidException("Description can not be empty.");
		}

		if (transferAmount <= 0) {
			throw new TransactionInvalidException("Transfer amount is not valid. Can not be zero or less.");
		}
	}

	@Override
	public Transaction createTransaction(Transaction transaction) throws CurrencyConvertionException {
		return null;
	}

	@Override
	public void doIt() throws IllegalStateException {
		throw new IllegalStateException("bad");		
	}
}
