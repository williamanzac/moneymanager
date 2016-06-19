package nz.co.anzac.moneymanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Account {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private String name;
	@Column
	private String number;
	@Column
	private double available;
	@Column
	private double balance;
	@Column
	private String type;
	@OneToMany
	private List<StatementEntry> entries;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}

	public double getAvailable() {
		return available;
	}

	public void setAvailable(final double available) {
		this.available = available;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(final double balance) {
		this.balance = balance;
	}

	public String getType() {
		return type;
	}

	public void setType(final String type) {
		this.type = type;
	}

	public List<StatementEntry> getEntries() {
		return entries;
	}

	public void setEntries(final List<StatementEntry> entires) {
		entries = entires;
	}
}
