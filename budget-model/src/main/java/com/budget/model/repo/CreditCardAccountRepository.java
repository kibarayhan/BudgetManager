package com.budget.model.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.budget.model.dto.CreditCardAccount;

@Repository
public interface CreditCardAccountRepository extends JpaRepository<CreditCardAccount, Integer>, 
QueryDslPredicateExecutor<CreditCardAccount> {

	@Query("SELECT c FROM CreditCardAccount c where c.account.name = :accountName")
    Optional<CreditCardAccount> getByAccountName(@Param("accountName") String accountName);

	@Query("SELECT c FROM CreditCardAccount c where c.account.id = :accountId")
	Optional<CreditCardAccount> getByAccountId(@Param("accountId") int accountId);
}
