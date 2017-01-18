package com.budget.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.budget.model.dto.Account;
import com.budget.model.dto.Customer;

import java.util.List;

@Repository
public interface AccountRepository
		extends JpaRepository<Account, Integer>, AccountRepositoryCustom, QueryDslPredicateExecutor<Account> {

	List<Account> findByCustomer(Customer customer);

	Account findByName(String name);

}
