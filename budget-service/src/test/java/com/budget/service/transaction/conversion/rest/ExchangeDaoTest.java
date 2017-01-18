package com.budget.service.transaction.conversion.rest;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.internal.matchers.Any;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.client.RestTemplate;

import com.budget.model.dto.Currency;
import com.budget.service.transaction.conversion.rest.ExchangeDao;
import com.budget.service.transaction.conversion.rest.ExchangeDaoIml;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ExchangeDaoTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void convert_GivenSameCurrencyAsFromTo_ShouldReturnSameAmount(){
	}
	
	
}
