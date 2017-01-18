package com.budget.service.predicate;

import com.budget.model.dto.QCustomer;
import com.querydsl.core.types.Predicate;

/**
 * Created by a579295 on 11.09.2016.
 */
public final class CustomerPrediate {
	
	private CustomerPrediate() {
	}

	public static Predicate nameContainsIgnoreCase(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return QCustomer.customer.isNotNull();
		} else {
			return QCustomer.customer.name.containsIgnoreCase(searchTerm);
			// .or(QCustomer.customer.emailAddress.containsIgnoreCase(searchTerm));
		}
	}

	public static Predicate customerIdEquals(int customerId) {
		return QCustomer.customer.id.eq(customerId);
	}

}
