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
}
