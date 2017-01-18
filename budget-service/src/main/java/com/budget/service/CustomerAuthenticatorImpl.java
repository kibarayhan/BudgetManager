package com.budget.service;

import com.budget.model.dto.Customer;
import com.budget.model.repo.CustomerRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Optional;

/**
 * Created by a579295 on 18.09.2016.
 */
@Service
public class CustomerAuthenticatorImpl implements CustomerAuthenticator {

	private static final Logger logger = Logger.getLogger(CustomerAuthenticatorImpl.class.getName());

	private final CustomerRepository customerRepository;

	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	public CustomerAuthenticatorImpl(CustomerRepository customerRepository,
			BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.customerRepository = customerRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	public boolean login(String username, String password) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
			return false;
		}

		Optional<Customer> existingCustomer = this.customerRepository.findByName(username);
		if (!existingCustomer.isPresent()) {
			return false;
		}

		if (!bCryptPasswordEncoder.matches(password, existingCustomer.get().getPassword())) {
			return false;
		}

		logger.info(String.format("The user %s is logged successful.", username));
		return true;
	}

	@Override
	public boolean register(String username, String password, String passwordAgain, String eMailAddress) {
		if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(eMailAddress)) {
			return false;
		}

		if (StringUtils.isEmpty(passwordAgain) || !password.equals(passwordAgain)) {
			return false;
		}

		Optional<Customer> existingCustomer = this.customerRepository.findByName(username);
		if (existingCustomer.isPresent()) {
			return false;
		}

		this.customerRepository.save(new Customer(username, bCryptPasswordEncoder.encode(password), eMailAddress));
		return true;
	}
}
