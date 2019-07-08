package nz.co.anzac.moneymanager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class CreditStatementEntry extends StatementEntry {
	@Column
	private Date processDate;
	@Column
	private double amount;
	@Column
	private String creditPlanName;
	@Column
	private String foreignDetails;
	@Column
	private String city;
	@Column
	private String countryCode;

	public Date getProcessDate() {
		return processDate;
	}

	public void setProcessDate(final Date processDate) {
		this.processDate = processDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	public String getCreditPlanName() {
		return creditPlanName;
	}

	public void setCreditPlanName(final String creditPlanName) {
		this.creditPlanName = creditPlanName;
	}

	public String getForeignDetails() {
		return foreignDetails;
	}

	public void setForeignDetails(final String foreignDetails) {
		this.foreignDetails = foreignDetails;
	}

	public String getCity() {
		return city;
	}

	public void setCity(final String city) {
		this.city = city;
	}

	public String getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ temp >>> 32);
		result = prime * result + (city == null ? 0 : city.hashCode());
		result = prime * result + (countryCode == null ? 0 : countryCode.hashCode());
		result = prime * result + (creditPlanName == null ? 0 : creditPlanName.hashCode());
		result = prime * result + (foreignDetails == null ? 0 : foreignDetails.hashCode());
		result = prime * result + (processDate == null ? 0 : processDate.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CreditStatementEntry other = (CreditStatementEntry) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
			return false;
		}
		if (city == null) {
			if (other.city != null) {
				return false;
			}
		} else if (!city.equals(other.city)) {
			return false;
		}
		if (countryCode == null) {
			if (other.countryCode != null) {
				return false;
			}
		} else if (!countryCode.equals(other.countryCode)) {
			return false;
		}
		if (creditPlanName == null) {
			if (other.creditPlanName != null) {
				return false;
			}
		} else if (!creditPlanName.equals(other.creditPlanName)) {
			return false;
		}
		if (foreignDetails == null) {
			if (other.foreignDetails != null) {
				return false;
			}
		} else if (!foreignDetails.equals(other.foreignDetails)) {
			return false;
		}
		if (processDate == null) {
			if (other.processDate != null) {
				return false;
			}
		} else if (!processDate.equals(other.processDate)) {
			return false;
		}
		return true;
	}
}
