package nz.co.anzac.moneymanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "statemententry")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class StatementEntry {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private String otherParty;
	@Column
	private Date date;
	@ManyToOne
	private Category category;
	@ManyToOne
	private Account account;

	public Category getCategory() {
		return category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(final Date date) {
		this.date = date;
	}

	public String getOtherParty() {
		return otherParty;
	}

	public void setOtherParty(final String otherParty) {
		this.otherParty = otherParty;
	}

	@JsonIgnore
	// we need to ignore this as it would cause recursion
	public Account getAccount() {
		return account;
	}

	public void setAccount(final Account account) {
		this.account = account;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (account == null ? 0 : account.hashCode());
		result = prime * result + (date == null ? 0 : date.hashCode());
		result = prime * result + (otherParty == null ? 0 : otherParty.hashCode());
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
		final StatementEntry other = (StatementEntry) obj;
		if (account == null) {
			if (other.account != null) {
				return false;
			}
		} else if (!account.equals(other.account)) {
			return false;
		}
		if (date == null) {
			if (other.date != null) {
				return false;
			}
		} else if (!date.equals(other.date)) {
			return false;
		}
		if (otherParty == null) {
			if (other.otherParty != null) {
				return false;
			}
		} else if (!otherParty.equals(other.otherParty)) {
			return false;
		}
		return true;
	}
}
