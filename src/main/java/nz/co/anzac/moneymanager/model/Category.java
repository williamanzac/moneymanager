package nz.co.anzac.moneymanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
public class Category {
	@Id
	@GeneratedValue
	private long id;
	@Column
	private String name;
	@Column
	private String colour;
	@OneToMany
	private List<Category> subCategories;
	@ManyToOne
	private Category parent;

	@JsonProperty
	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(final List<Category> subCategories) {
		this.subCategories = subCategories;
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	public void setId(final long id) {
		this.id = id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@JsonProperty
	public String getColour() {
		return colour;
	}

	public void setColour(final String colour) {
		this.colour = colour;
	}

	@JsonIgnore
	// we need to ignore this as it would cause recursion
	public Category getParent() {
		return parent;
	}

	public void setParent(final Category parent) {
		this.parent = parent;
	}
}
