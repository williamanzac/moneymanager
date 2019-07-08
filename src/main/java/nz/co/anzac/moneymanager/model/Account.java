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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(available);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(balance);
		result = prime * result + (int) (temp ^ temp >>> 32);
		result = prime * result + (entries == null ? 0 : entries.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (number == null ? 0 : number.hashCode());
		result = prime * result + (type == null ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Account other = (Account) obj;
		if (Double.doubleToLongBits(available) != Double.doubleToLongBits(other.available)) {
			return false;
		}
		if (Double.doubleToLongBits(balance) != Double.doubleToLongBits(other.balance)) {
			return false;
		}
		if (entries == null) {
			if (other.entries != null) {
				return false;
			}
		} else if (!entries.equals(other.entries)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (number == null) {
			if (other.number != null) {
				return false;
			}
		} else if (!number.equals(other.number)) {
			return false;
		}
		if (type == null) {
			if (other.type != null) {
				return false;
			}
		} else if (!type.equals(other.type)) {
			return false;
		}
		return true;
	}
}
