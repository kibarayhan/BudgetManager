package com.budget.service;

import com.budget.model.dto.Account;

import java.util.List;

/**
 * Created by a579295 on 11.09.2016.
 */
public interface AccountSearchService {
	
    List<Account> findByCustomerIdAndAccountName(int customerId, String accountName);
    
    int countByCustomerIdAndAccountName(int customerId, String accountName);
}
