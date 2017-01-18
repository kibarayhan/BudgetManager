package com.budget.model.dto;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author a579295
 */
@Entity
@Table(name = "transaction")
public class Transaction {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@ManyToOne
	@JoinColumn(name = "from_customer_id")
	private Customer fromCustomer;

	@ManyToOne
	@JoinColumn(name = "from_account_id")
	private Account fromAccount;

	@ManyToOne
	@JoinColumn(name = "to_customer_id")
	private Customer toCustomer;

	@ManyToOne
	@JoinColumn(name = "to_account_id")
	private Account toAccount;

	@Column(name = "amount", nullable = false)
	private BigDecimal amount;

	@Column(name = "date_created", nullable = false)
	private Date dateCreated;

	@Column(name = "description", length = 300)
	private String description;

	public Transaction() {
	}
	
	public Transaction(Customer fromCustomer, Account fromAccount, Customer toCustomer, Account toAccount,
			BigDecimal amount) {
		this.fromCustomer = fromCustomer;
		this.fromAccount = fromAccount;
		this.toCustomer = toCustomer;
		this.toAccount = toAccount;
		this.amount = amount;
	}

	public Customer getFromCustomer() {
		return fromCustomer;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public Customer getToCustomer() {
		return toCustomer;
	}

	public Account getToAccount() {
		return toAccount;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setFromCustomer(Customer fromCustomer) {
		this.fromCustomer = fromCustomer;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public void setToCustomer(Customer toCustomer) {
		this.toCustomer = toCustomer;
	}

	public void setToAccount(Account toAccount) {
		this.toAccount = toAccount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
