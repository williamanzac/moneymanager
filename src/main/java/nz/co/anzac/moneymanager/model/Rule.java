package nz.co.anzac.moneymanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Rule {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private String name;
	@Column
	private String description;
	@Column
	private RuleType type;
	@OneToMany
	private List<Condition> conditions;
	@OneToOne
	private Category category;

	public Category getCategory() {
		return category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

	public List<Condition> getConditions() {
		return conditions;
	}

	public void setConditions(final List<Condition> conditions) {
		this.conditions = conditions;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public RuleType getType() {
		return type;
	}

	public void setType(final RuleType type) {
		this.type = type;
	}
}
