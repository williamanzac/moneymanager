package nz.co.anzac.moneymanager.dao;

import java.util.List;

import nz.co.anzac.moneymanager.model.StatementEntry;

import org.hibernate.SessionFactory;

public class StatementEntryDAO extends AbstractCRUDDAO<StatementEntry> {
	public StatementEntryDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<StatementEntry> getEntriesByAccountId(final long id) {
		return list(currentSession().createQuery("from StatementEntry as entry where entry.account.id = :id").setLong(
				"id", id));
	}

	public List<StatementEntry> getEntriesByCategoryId(final long id) {
		return list(currentSession().createQuery("from StatementEntry as entry where entry.category.id = :id").setLong(
				"id", id));
	}
}
