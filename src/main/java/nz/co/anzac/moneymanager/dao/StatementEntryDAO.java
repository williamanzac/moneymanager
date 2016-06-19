package nz.co.anzac.moneymanager.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.io.Serializable;
import java.util.List;

import nz.co.anzac.moneymanager.model.StatementEntry;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

public class StatementEntryDAO extends AbstractDAO<StatementEntry> {
	public StatementEntryDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<StatementEntry> list() {
		return list(currentSession().createCriteria(getEntityClass()));
	}

	@Override
	public StatementEntry persist(final StatementEntry entity) throws HibernateException {
		return super.persist(entity);
	}

	@Override
	public StatementEntry get(final Serializable id) {
		return super.get(id);
	}

	public List<StatementEntry> getEntriesByAccountId(long id) {
		return list(currentSession().createQuery("from StatementEntry as entry where entry.account.id = :id").setLong(
				"id", id));
	}
}
