package nz.co.anzac.moneymanager.representation;

import nz.co.anzac.moneymanager.model.Account;

public class AccountDetail extends AccountName {
	private String number;
	private double available;
	private double balance;
	private String type;

	public AccountDetail(final Account account) {
		super(account);
		number = account.getNumber();
		available = account.getAvailable();
		balance = account.getBalance();
		type = account.getType();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(double available) {
		this.available = available;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
