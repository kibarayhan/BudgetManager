package com.budget.model.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "creditCardAccount")
public class CreditCardAccount{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne
	@JoinColumn(name = "account_id", nullable = false)
	private Account account;

	private BigDecimal limitAmount;

	@Column(name = "cut_off_date", length = 2)
	private int cutOffDate;

	public CreditCardAccount() {
	}

	public CreditCardAccount(BigDecimal limitAmount, int cutOffDate) {
		this.limitAmount = limitAmount;
		this.cutOffDate = cutOffDate;
	}

	public BigDecimal getLimitAmount() {
		return limitAmount;
	}

	public void setLimitAmount(BigDecimal limitAmount) {
		this.limitAmount = limitAmount;
	}

	public int getCutOffDate() {
		return cutOffDate;
	}

	public void setCutOffDate(int cutOffDate) {
		this.cutOffDate = cutOffDate;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return getId() + ":" + account.getName();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((account == null) ? 0 : account.hashCode());
		result = prime * result + id;
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
		CreditCardAccount other = (CreditCardAccount) obj;
		if (account == null) {
			if (other.account != null)
				return false;
		} else if (!account.equals(other.account))
			return false;
		if (id != other.id)
			return false;
		return true;
	}

}
