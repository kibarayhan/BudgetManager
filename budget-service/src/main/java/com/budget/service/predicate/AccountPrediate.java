package com.budget.service.predicate;

import com.budget.model.dto.QAccount;
import com.querydsl.core.types.Predicate;

/**
 * Created by a579295 on 11.09.2016.
 */
public final class AccountPrediate {
	
	private AccountPrediate() {
	}

	public static Predicate nameContainsIgnoreCase(String searchTerm) {
		if (searchTerm == null || searchTerm.isEmpty()) {
			return QAccount.account.isNotNull();
		} else {
			return QAccount.account.name.containsIgnoreCase(searchTerm);
			// .or(QCustomer.customer.emailAddress.containsIgnoreCase(searchTerm));
		}
	}

	public static Predicate customerIdEquals(int customerId) {
		return QAccount.account.customer.id.eq(customerId);
	}

	public static Predicate nameStartsWith(String name) {
		return QAccount.account.name.startsWith(name);
	}

	public static Predicate customerIdEqualsAndNameStartsWith(int customerId, String name) {
		return // QAccount.account.name.startsWith(name);
		QAccount.account.customer.id.eq(customerId).and(QAccount.account.name.startsWith(name));
	}

}
