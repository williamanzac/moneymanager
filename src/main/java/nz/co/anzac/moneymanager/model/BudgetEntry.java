package nz.co.anzac.moneymanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "budgetentry")
public class BudgetEntry {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private double budgetAmount;

	private double actualAmount;
	@Column
	private int forMonth;
	@Column
	private int forYear;
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

	public double getBudgetAmount() {
		return budgetAmount;
	}

	public void setBudgetAmount(final double budgetedAmount) {
		budgetAmount = budgetedAmount;
	}

	public double getActualAmount() {
		return actualAmount;
	}

	public void setActualAmount(final double actualAmount) {
		this.actualAmount = actualAmount;
	}

	public int getForMonth() {
		return forMonth;
	}

	public void setForMonth(final int forMonth) {
		this.forMonth = forMonth;
	}

	public int getForYear() {
		return forYear;
	}

	public void setForYear(final int forYear) {
		this.forYear = forYear;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(actualAmount);
		result = prime * result + (int) (temp ^ temp >>> 32);
		temp = Double.doubleToLongBits(budgetAmount);
		result = prime * result + (int) (temp ^ temp >>> 32);
		result = prime * result + (category == null ? 0 : category.hashCode());
		result = prime * result + forMonth;
		result = prime * result + forYear;
		result = prime * result + (int) (id ^ id >>> 32);
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
		final BudgetEntry other = (BudgetEntry) obj;
		if (Double.doubleToLongBits(actualAmount) != Double.doubleToLongBits(other.actualAmount)) {
			return false;
		}
		if (Double.doubleToLongBits(budgetAmount) != Double.doubleToLongBits(other.budgetAmount)) {
			return false;
		}
		if (category == null) {
			if (other.category != null) {
				return false;
			}
		} else if (!category.equals(other.category)) {
			return false;
		}
		if (forMonth != other.forMonth) {
			return false;
		}
		if (forYear != other.forYear) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
