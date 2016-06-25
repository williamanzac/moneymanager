package nz.co.anzac.moneymanager.service;

import java.io.Serializable;
import java.util.List;

import nz.co.anzac.moneymanager.dao.AbstractCRUDDAO;

public abstract class AbstractCRUDService<T> {
	protected final AbstractCRUDDAO<T> dao;

	public AbstractCRUDService(final AbstractCRUDDAO<T> dao) {
		this.dao = dao;
	}

	public T create(final T entity) {
		final T persist = dao.persist(entity);
		return persist;
	}

	public T read(final Serializable id) {
		return dao.get(id);
	}

	public T update(final T entity) {
		return dao.persist(entity);
	}

	public void delete(final Serializable id) {
		dao.delete(id);
	}

	public List<T> list() {
		return dao.list();
	}
}
