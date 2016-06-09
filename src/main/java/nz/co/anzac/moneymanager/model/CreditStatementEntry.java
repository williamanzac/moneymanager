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
}
