package nz.co.anzac.moneymanager.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Condition {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private String field;
	@Column
	private ConditionType type;
	@Column
	private String value;

	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	public String getField() {
		return field;
	}

	public void setField(final String field) {
		this.field = field;
	}

	public ConditionType getType() {
		return type;
	}

	public void setType(final ConditionType type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(final String value) {
		this.value = value;
	}
}
