package nz.co.anzac.moneymanager.dao;

import java.util.List;

import nz.co.anzac.moneymanager.model.BudgetEntry;

import org.hibernate.SessionFactory;

public class BudgetEntryDAO extends AbstractCRUDDAO<BudgetEntry> {
	public BudgetEntryDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public List<BudgetEntry> getEntriesByCategoryId(final long id) {
		return list(currentSession().createQuery("from BudgetEntry as entry where entry.category.id = :id").setLong(
				"id", id));
	}

	public List<BudgetEntry> listByMonth(final int year, final int month) {
		return list(currentSession()
				.createQuery("from BudgetEntry as entry where entry.month = :month and entry.year = :year")
				.setInteger("month", month).setInteger("year", year));
	}
}
