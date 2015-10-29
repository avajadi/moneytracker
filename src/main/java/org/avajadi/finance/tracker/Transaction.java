package org.avajadi.finance.tracker;

import java.time.LocalDate;

public class Transaction {

	private String accountNumber;
	private double amount;
	private double balance;
	private LocalDate bookingDate;
	private String comment;
	private LocalDate currencyDate;
	private Integer id;
	private String verification;

	/**
	 * @param accountNumber
	 * @param bookingDate
	 * @param currencyDate
	 * @param verification
	 * @param comment
	 * @param amount
	 * @param balance
	 */
	public Transaction(String accountNumber, LocalDate bookingDate, LocalDate currencyDate, String verification,
			String comment, double amount, double balance) {
		this(null, accountNumber, bookingDate, currencyDate, verification, comment, amount, balance);
	}

	public Transaction(Integer id, String accountNumber, LocalDate bookingDate, LocalDate currencyDate,
			String verification, String comment, double amount, double balance) {
		this.id = id;
		this.accountNumber = accountNumber;
		this.bookingDate = bookingDate;
		this.currencyDate = currencyDate;
		this.verification = verification;
		this.comment = comment;
		this.amount = amount;
		this.balance = balance;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public double getBalance() {
		return balance;
	}

	public LocalDate getBookingDate() {
		return bookingDate;
	}

	public String getComment() {
		return comment;
	}

	public LocalDate getCurrencyDate() {
		return currencyDate;
	}

	public Integer getId() {
		return id;
	}

	public String getVerification() {
		return verification;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transaction [id=");
		builder.append(id);
		builder.append(", accountNumber=");
		builder.append(accountNumber);
		builder.append(", bookingDate=");
		builder.append(bookingDate);
		builder.append(", currencyDate=");
		builder.append(currencyDate);
		builder.append(", verification=");
		builder.append(verification);
		builder.append(", comment=");
		builder.append(comment);
		builder.append(", amount=");
		builder.append(amount);
		builder.append(", balance=");
		builder.append(balance);
		builder.append("]");
		return builder.toString();
	}

}
