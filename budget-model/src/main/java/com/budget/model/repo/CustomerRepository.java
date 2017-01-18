package com.budget.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.budget.model.dto.Customer;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer>,
        CustomerRepositoryCustom {

	Optional<Customer> findByName(String name);

	Optional<Customer> findByNameContains(String name);
	
}
