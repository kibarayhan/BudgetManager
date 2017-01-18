package com.budget.service.predicate.my;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class EqualPredicate extends MyPredicate {

	public EqualPredicate(String key, Object value) {
		super(key, value);
	}

	@Override
	public BooleanExpression getPredicate(Class<?> typeParameterClass) {
		PathBuilder<?> entityPath = getPathBuilder(typeParameterClass);

		if (isNumeric(getValue())) {
			NumberPath<Integer> path = entityPath.getNumber(getKey(), Integer.class);
			int numberValue = Integer.parseInt(getValue());
			return path.eq(numberValue);
		} else {
			StringPath path = entityPath.getString(getKey());
			return path.containsIgnoreCase(getValue());
		}
	}

}
