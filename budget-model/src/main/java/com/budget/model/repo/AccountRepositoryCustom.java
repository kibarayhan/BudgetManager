package com.budget.model.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.budget.model.dto.Account;

import java.util.List;

public interface AccountRepositoryCustom {
	@Query("SELECT a FROM Account a where a.customer.id = :customerId")
    List<Account> getByCustomerId(@Param("customerId") String customerId);

	@Query("SELECT a FROM Account a where a.customer.name = :customerName")
    List<Account> getByCustomerName(@Param("customerName") String customerName);

	@Query("SELECT a FROM Account a where a.customer.id = :customerId and a.name = :accountName")
    Account getByCustomerIdAndAccountName(@Param("customerId") int customerId,
                                           @Param("accountName") String accountName);

	@Query("SELECT t FROM Account t WHERE " + "t.customer.id = :customerId AND "
			+ "LOWER(t.name) LIKE LOWER(CONCAT('%',:accountName, '%')) ")
    List<Account> getByCustomerIdAndStartsWithAccountName(@Param("customerId") int customerId,
                                                           @Param("accountName") String accountName);

	@Query("SELECT count(1) FROM Account a where a.customer.id = :customerId")
    int getCountByCustomerId(@Param("customerId") int customerId);
}
