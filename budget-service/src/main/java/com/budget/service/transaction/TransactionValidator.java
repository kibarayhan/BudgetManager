package com.budget.service.transaction;

import com.budget.model.dto.Transaction;

/**
 * Created by a579295 on 18.09.2016.
 */
public interface TransactionValidator {
	Transaction validateTransaction(String fromCustomerName, String fromAccountName, String toCustomerName,
			String toAccountName, double transferAmount, String transferDescription) throws TransactionInvalidException;
}
