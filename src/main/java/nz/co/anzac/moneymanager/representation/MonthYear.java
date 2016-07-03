package nz.co.anzac.moneymanager.representation;

public class MonthYear implements Comparable<MonthYear> {
	private int month;
	private int year;

	public MonthYear(int month, int year) {
		super();
		this.month = month;
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + month;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonthYear other = (MonthYear) obj;
		if (month != other.month)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public int compareTo(MonthYear o) {
		int compare = Integer.compare(year, o.year);
		if (compare != 0) {
			return compare;
		}
		return Integer.compare(month, o.month);
	}
}
