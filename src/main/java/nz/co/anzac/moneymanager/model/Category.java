package nz.co.anzac.moneymanager.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

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

	public List<Category> getSubCategories() {
		return subCategories;
	}

	public void setSubCategories(final List<Category> subCategories) {
		this.subCategories = subCategories;
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

	public String getColour() {
		return colour;
	}

	public void setColour(final String colour) {
		this.colour = colour;
	}
}
