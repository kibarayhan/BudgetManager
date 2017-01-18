package com.budget.service.transaction;

import com.budget.model.dto.Transaction;
import com.budget.service.transaction.conversion.CurrencyConvertionException;

/**
 * Created by a579295 on 18.09.2016.
 */
public interface MyTransactionManager extends TransactionValidator {
    Transaction createTransaction(Transaction transaction) throws CurrencyConvertionException;
    public void doIt() throws IllegalStateException;

}
