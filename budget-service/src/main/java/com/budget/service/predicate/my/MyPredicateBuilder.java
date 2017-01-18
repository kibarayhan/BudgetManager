package com.budget.service.predicate.my;

import com.querydsl.core.types.dsl.BooleanExpression;

import java.util.ArrayList;
import java.util.List;

public class MyPredicateBuilder<T> {
	private List<MyPredicate> params;
	private final Class<T> typeParameterClass;
	BooleanExpression result = null;
	
	public MyPredicateBuilder(Class<T> typeParameterClass) {
		this.params = new ArrayList<>();
		this.typeParameterClass = typeParameterClass;
	}

	public MyPredicateBuilder<T> with(MyPredicate myPredicate) {
		params.add(myPredicate);
		return this;
	}

	public MyPredicateBuilder<T> and(MyPredicate myPredicate) {
		if (result == null){
			result = myPredicate.getPredicate(typeParameterClass);
		}else{
			result = result.and(myPredicate.getPredicate(typeParameterClass));
		}
		
		return this;
	}

	public MyPredicateBuilder<T> or(MyPredicate myPredicate) {
		if (result == null){
			result = myPredicate.getPredicate(typeParameterClass);
		}else{
			result = result.or(myPredicate.getPredicate(typeParameterClass));
		}
		
		return this;
	}

	public BooleanExpression build() {
		return result;
	}

	
	public MyPredicateBuilder<T> and() {
		if (params.isEmpty()) {
			return this;
		}

		List<BooleanExpression> predicates = new ArrayList<>(params.size());
		for (MyPredicate param : params) {
			BooleanExpression exp = param.getPredicate(typeParameterClass);
			if (exp != null) {
				predicates.add(exp);
			}
		}

		if (result == null){
			result = predicates.get(0);
			for (int i = 1; i < predicates.size(); i++) {
				result = result.and(predicates.get(i));
			}
		}
		else{
			for (int i = 0; i < predicates.size(); i++) {
				result = result.and(predicates.get(i));
			}
			
		}
		
		params.clear();
		
		return this;
	}

	public MyPredicateBuilder<T> or() {
		if (params.isEmpty()) {
			return this;
		}
		
		List<BooleanExpression> predicates = new ArrayList<>(params.size());
		for (MyPredicate param : params) {
			BooleanExpression exp = param.getPredicate(typeParameterClass);
			if (exp != null) {
				predicates.add(exp);
			}
		}
		
		if (result == null){
			result = predicates.get(0);
			for (int i = 1; i < predicates.size(); i++) {
				result = result.or(predicates.get(i));
			}
		}
		else{
			for (int i = 0; i < predicates.size(); i++) {
				result = result.or(predicates.get(i));
			}
			
		}
		
		params.clear();
		
		return this;
	}

}
