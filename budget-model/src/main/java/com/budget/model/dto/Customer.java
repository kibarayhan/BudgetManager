package com.budget.model.dto;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Created by a579295 on 10.09.2016.
 */
@Entity
@Table(name = "customer")
public class Customer{

	/** The id. */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	/** The name. */
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	/** The password. */
	@Column(name = "password", nullable = false, length = 200)
	private String password;

	/** The email address. */
	@Column(name = "email_address", length = 100)
	private String emailAddress;

	/** The date created. */
	@Column(name = "date_created", nullable = false)
	private Date dateCreated;

	/** The accounts. */
	@OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
	private Set<Account> accounts;

	// @Column(name = "modification_time")
	// @Type(type =
	// "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	// private ZonedDateTime modificationTime;

	/**
	 * Instantiates a new customer for testing purposes.
	 */
	public Customer() {
	}

	/**
	 * Instantiates a new customer.
	 *
	 * @param name
	 *            the name
	 * @param password
	 *            the password
	 * @param emailAddress
	 *            the email address
	 */
	public Customer(String name, String password, String emailAddress) {
		this.name = name;
		this.password = password;
		this.emailAddress = emailAddress;
		this.setDateCreated(new Date());
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name
	 *            the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Gets the password.
	 *
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Sets the password.
	 *
	 * @param password
	 *            the new password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * Gets the email address.
	 *
	 * @return the email address
	 */
	public String getEmailAddress() {
		return emailAddress;
	}

	/**
	 * Sets the email address.
	 *
	 * @param emailAddress
	 *            the new email address
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	/**
	 * Gets the date created.
	 *
	 * @return the date created
	 */
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	 * Sets the date created.
	 *
	 * @param dateCreated
	 *            the new date created
	 */
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	 * Gets the accounts.
	 *
	 * @return the accounts
	 */
	public Set<Account> getAccounts() {
		if (accounts == null) {
			accounts = new HashSet<>();
		}
		return accounts;
	}

	/**
	 * Sets the accounts.
	 *
	 * @param accounts
	 *            the new accounts
	 */
	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("Customer[name='%s', eMail='%s']", name, emailAddress);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o){
			return true;
		}
		
		if (o == null || getClass() != o.getClass()){
			return false;
		}

		Customer customer = (Customer) o;

		if (!name.equals(customer.name)){
			return false;
		}
		return emailAddress.equals(customer.emailAddress);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + emailAddress.hashCode();
		return result;
	}
}
