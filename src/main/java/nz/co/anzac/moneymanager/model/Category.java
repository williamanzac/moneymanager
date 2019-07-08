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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (colour == null ? 0 : colour.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		result = prime * result + (parent == null ? 0 : parent.hashCode());
		result = prime * result + (subCategories == null ? 0 : subCategories.hashCode());
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
		final Category other = (Category) obj;
		if (colour == null) {
			if (other.colour != null) {
				return false;
			}
		} else if (!colour.equals(other.colour)) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (parent == null) {
			if (other.parent != null) {
				return false;
			}
		} else if (!parent.equals(other.parent)) {
			return false;
		}
		if (subCategories == null) {
			if (other.subCategories != null) {
				return false;
			}
		} else if (!subCategories.equals(other.subCategories)) {
			return false;
		}
		return true;
	}
}
