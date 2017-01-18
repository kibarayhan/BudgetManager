package com.budget.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.budget.model.dto.Account;
import com.budget.model.repo.AccountRepository;
import com.budget.service.predicate.AccountPrediate;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;

/**
 * Created by a579295 on 11.09.2016.
 */
@Service
public class AccountSearchServiceImpl implements AccountSearchService {

	@Autowired
	AccountRepository accountRepository;

	@Transactional(readOnly = true)
	@Override
	public List<Account> findByCustomerIdAndAccountName(int customerId, String accountName) {
		Predicate predicate = AccountPrediate.customerIdEqualsAndNameStartsWith(customerId, accountName);

		Iterable<Account> findAll = accountRepository.findAll(predicate);
		return Lists.newArrayList(findAll);
	}

	@Transactional(readOnly = true)
	@Override
	public int countByCustomerIdAndAccountName(int customerId, String accountName) {
		Predicate predicate = AccountPrediate.customerIdEqualsAndNameStartsWith(customerId, accountName);
		return (int) accountRepository.count(predicate);
	}
}
