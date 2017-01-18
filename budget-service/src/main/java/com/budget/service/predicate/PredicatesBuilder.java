package com.budget.service.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;

public class PredicatesBuilder<T> {
	private List<SearchCriteria> params;
	private final Class<T> typeParameterClass;
	
	public PredicatesBuilder(Class<T> typeParameterClass) {
		this.params = new ArrayList<>();
		this.typeParameterClass = typeParameterClass;
	}

	public PredicatesBuilder<T> with(String key, String operation, Object value) {
		params.add(new SearchCriteria(key, operation, value));
		return this;
	}

	public BooleanExpression build() {
		if (params.isEmpty()) {
			return null;
		}

		List<BooleanExpression> predicates = new ArrayList<BooleanExpression>();
		CustomPredicate<T> predicate;
		for (SearchCriteria param : params) {
			predicate = new CustomPredicate<>(param, typeParameterClass);
			BooleanExpression exp = predicate.getPredicate();
			if (exp != null) {
				predicates.add(exp);
			}
		}

		BooleanExpression result = predicates.get(0);
		for (int i = 1; i < predicates.size(); i++) {
			result = result.and(predicates.get(i));
		}
		
		return result;
	}

}
