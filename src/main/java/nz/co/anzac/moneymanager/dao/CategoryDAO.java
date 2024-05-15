package nz.co.anzac.moneymanager.dao;

import nz.co.anzac.moneymanager.model.Category;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

@SuppressWarnings({ "unchecked", "deprecation" })
public class CategoryDAO extends AbstractCRUDDAO<Category> {
	public CategoryDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	@Override
	public Category persist(final Category entity) throws HibernateException {
		if (entity.getParent() != null && entity.getParent().getSubCategories() != null
				&& !entity.getParent().getSubCategories().contains(entity)) {
			entity.getParent().getSubCategories().add(entity);
			persist(entity.getParent());
		}
		if (entity.getSubCategories() != null && !entity.getSubCategories().isEmpty()) {
			entity.getSubCategories().forEach(c -> {
				if (c.getParent() != entity) {
					c.setParent(entity);
				}
				persist(c);
			});
		}
		return super.persist(entity);
	}

	public Category getRootCategory() {
		return uniqueResult(currentSession().createQuery("from Category as cat where cat.parent = null"));
	}

	public Category findCategoryByName(final String name) {
		return uniqueResult(currentSession().createQuery("from Category as cat where cat.name = :name").setString(
				"name", name));
	}
}
