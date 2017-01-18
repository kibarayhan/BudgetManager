package com.budget.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Created by a579295 on 10.09.2016.
 */
@Entity
@Table(name = "account")
public class Account{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "date_created", nullable = false)
	private Date dateCreated;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "account")
	CreditCardAccount creditCardAccount;

	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "auto_clear_transaction", nullable = false)
	private boolean autoClearTransaction;

	public Account() {
	}

	public Account(String name, Currency currency, BigDecimal amount, Customer customer) {
		this.name = name;
		this.amount = amount;
		this.customer = customer;
		this.currency = currency;
		this.autoClearTransaction = true;
		this.customer.getAccounts().add(this);
		this.setDateCreated(new Date());
	}

	public Account(String name, Currency currency, BigDecimal amount, Customer customer,
			CreditCardAccount creditCardAccount) {
		this(name, currency, amount, customer);
		this.creditCardAccount = creditCardAccount;
		creditCardAccount.setAccount(this);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return String.format("Account[name='%s']", name);
	}

	public CreditCardAccount getCreditCardAccount() {
		return creditCardAccount;
	}

	public void setCreditCardAccount(CreditCardAccount creditCardAccount) {
		this.creditCardAccount = creditCardAccount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Account other = (Account) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	public boolean isAutoClearTransaction() {
		return autoClearTransaction;
	}

	public void setAutoClearTransaction(boolean autoClearTransaction) {
		this.autoClearTransaction = autoClearTransaction;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

}
