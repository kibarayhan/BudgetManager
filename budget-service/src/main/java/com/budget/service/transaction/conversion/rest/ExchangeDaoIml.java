package com.budget.service.transaction.conversion.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.eclipsesource.json.Json;
import com.eclipsesource.json.JsonObject;
import com.eclipsesource.json.JsonValue;

@Component
public class ExchangeDaoIml implements ExchangeDao {

	private final RestTemplate restTemplate;

	private static final String restUrlToRequest = "http://api.fixer.io/latest?base=%s&symbols=%s";

	private String toCurrencyType;

	@Autowired
	public ExchangeDaoIml(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public Double getRate(String from, String to) {
		String cur2 = restTemplate.getForObject(String.format(restUrlToRequest, from, to), String.class);

		JsonObject object = Json.parse(cur2).asObject();
		JsonObject rates = object.get("rates").asObject();
		JsonValue rateValue = rates.get(toCurrencyType);
		if (rateValue != null && !StringUtils.isEmpty(rateValue.toString())) {
			return rateValue.asDouble();
		}

		return null;
	}

}
