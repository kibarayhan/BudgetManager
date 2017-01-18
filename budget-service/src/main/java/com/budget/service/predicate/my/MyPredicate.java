package com.budget.service.predicate.my;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

public abstract class MyPredicate {
	
	private String key;
	private Object value;


	public MyPredicate(String key, Object value) {
		this.key = key;
		this.value = value;
	}

	public abstract BooleanExpression getPredicate(Class<?> typeParameterClass);

	private String getClassName(Class<?> typeParameterClass) {
		String simpleName = typeParameterClass.getSimpleName();
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1, simpleName.length());
	}

	protected boolean isNumeric(String s) {
		return s.matches("[-+]?\\d*\\.?\\d+");
	}

	protected PathBuilder<?> getPathBuilder(Class<?> typeParameterClass) {
		return new PathBuilder<>(typeParameterClass, getClassName(typeParameterClass));
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value.toString();
	}
}
