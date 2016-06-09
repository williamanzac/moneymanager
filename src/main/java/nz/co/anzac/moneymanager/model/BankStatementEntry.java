package nz.co.anzac.moneymanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class BankStatementEntry extends StatementEntry {
	@Column
	private double amount;
	@Column
	private String description;
	@Column
	private String reference;
	@Column
	private String particulars;
	@Column
	private String analysisCode;

	public double getAmount() {
		return amount;
	}

	public void setAmount(final double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(final String reference) {
		this.reference = reference;
	}

	public String getParticulars() {
		return particulars;
	}

	public void setParticulars(final String particulars) {
		this.particulars = particulars;
	}

	public String getAnalysisCode() {
		return analysisCode;
	}

	public void setAnalysisCode(final String analysisCode) {
		this.analysisCode = analysisCode;
	}
}
