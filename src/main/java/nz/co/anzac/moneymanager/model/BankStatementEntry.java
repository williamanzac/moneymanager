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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ temp >>> 32);
		result = prime * result + (analysisCode == null ? 0 : analysisCode.hashCode());
		result = prime * result + (description == null ? 0 : description.hashCode());
		result = prime * result + (particulars == null ? 0 : particulars.hashCode());
		result = prime * result + (reference == null ? 0 : reference.hashCode());
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
		final BankStatementEntry other = (BankStatementEntry) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount)) {
			return false;
		}
		if (analysisCode == null) {
			if (other.analysisCode != null) {
				return false;
			}
		} else if (!analysisCode.equals(other.analysisCode)) {
			return false;
		}
		if (description == null) {
			if (other.description != null) {
				return false;
			}
		} else if (!description.equals(other.description)) {
			return false;
		}
		if (particulars == null) {
			if (other.particulars != null) {
				return false;
			}
		} else if (!particulars.equals(other.particulars)) {
			return false;
		}
		if (reference == null) {
			if (other.reference != null) {
				return false;
			}
		} else if (!reference.equals(other.reference)) {
			return false;
		}
		return true;
	}
}
