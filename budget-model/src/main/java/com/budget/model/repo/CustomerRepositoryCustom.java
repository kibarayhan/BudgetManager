package com.budget.model.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import com.budget.model.dto.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepositoryCustom extends QueryDslPredicateExecutor<Customer>{

	@Query("SELECT c FROM Customer c where c.name = :customerName")
    Optional<Customer> getByCustomerName(@Param("customerName") String customerName);
	
	@Query("SELECT t FROM Customer t WHERE " +
            "LOWER(t.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(t.emailAddress) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<Customer> getBySearchTerm(@Param("searchTerm") String searchTerm);	
	
}
