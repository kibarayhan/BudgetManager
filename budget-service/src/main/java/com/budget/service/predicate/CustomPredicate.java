package com.budget.service.predicate;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.core.types.dsl.StringPath;

public class CustomPredicate<T> {

	private final Class<T> typeParameterClass;
	private SearchCriteria criteria;

	public CustomPredicate(SearchCriteria criteria, Class<T> typeParameterClass) {
		this.criteria = criteria;
		this.typeParameterClass = typeParameterClass;
	}

	public BooleanExpression getPredicate() {
		PathBuilder<T> entityPath = new PathBuilder<>(typeParameterClass, getClassName());

		if (isNumeric(criteria.getValue().toString())) {
			NumberPath<Integer> path = entityPath.getNumber(criteria.getKey(), Integer.class);
			int value = Integer.parseInt(criteria.getValue().toString());
			if (":".equalsIgnoreCase(criteria.getOperation())) {
				return path.eq(value);
			} else if (">".equalsIgnoreCase(criteria.getOperation())) {
				return path.gt(value);
			} else if ("<".equalsIgnoreCase(criteria.getOperation())) {
				return path.lt(value);
			}
		} else {
			StringPath path = entityPath.getString(criteria.getKey());
			if (":".equalsIgnoreCase(criteria.getOperation())) {
				return path.containsIgnoreCase(criteria.getValue().toString());
			}
		}
		return null;
	}

	private String getClassName() {
		String simpleName = typeParameterClass.getSimpleName();
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1, simpleName.length());
	}

	private boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	// public static boolean isNumeric(final String str) {
	// try {
	// Integer.parseInt(str);
	// } catch (final NumberFormatException e) {
	// return false;
	// }
	// return true;
	// }
}
