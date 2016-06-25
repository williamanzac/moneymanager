package nz.co.anzac.moneymanager.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

public abstract class AbstractCRUDDAO<T> extends AbstractDAO<T> {

	public AbstractCRUDDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<T> list() {
		return list(currentSession().createCriteria(getEntityClass()));
	}

	@Override
	public T persist(final T entity) throws HibernateException {
		return super.persist(entity);
	}

	@Override
	public T get(final Serializable id) {
		return super.get(id);
	}

	public void delete(final Serializable id) {
		final T entity = get(id);
		currentSession().delete(entity);
	}
}