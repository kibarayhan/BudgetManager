package com.budget.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.budget.model.dto.Customer;
import com.budget.model.repo.CustomerRepository;
import com.budget.service.predicate.CustomerPrediate;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;

/**
 * Created by a579295 on 11.09.2016.
 */
@Service
public class CustomerSearchServiceImpl implements CustomerSearchService {

    CustomerRepository customerRepository;

    @Transactional(readOnly = true)
    @Override
    public List<Customer> findBySearchTerm(String searchTerm) {
        Predicate searchPred = CustomerPrediate.nameContainsIgnoreCase(searchTerm);
        return Lists.newArrayList(customerRepository.findAll(searchPred));
    }
}
