package com.budget.service;

import com.budget.model.dto.Customer;

import java.util.List;

/**
 * Created by a579295 on 11.09.2016.
 */
public interface CustomerSearchService{
	
    List<Customer> findBySearchTerm(String searchTerm);

}
