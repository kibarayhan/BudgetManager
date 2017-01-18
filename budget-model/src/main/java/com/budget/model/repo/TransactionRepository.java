package com.budget.model.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.budget.model.dto.Account;
import com.budget.model.dto.Customer;
import com.budget.model.dto.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

	List<Transaction> findByFromCustomer(Customer fromCustomer);

	List<Transaction> findByFromAccount(Account fromAccount);

	List<Transaction> findByToCustomer(Customer toCustomer);
	
	List<Transaction> findByToAccount(Account toAccount);

}
