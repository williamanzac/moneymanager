package nz.co.anzac.moneymanager.dao;

import java.util.List;

import nz.co.anzac.moneymanager.model.BudgetEntry;

import org.hibernate.SessionFactory;

public class BudgetEntryDAO extends AbstractCRUDDAO<BudgetEntry> {
	public BudgetEntryDAO(final SessionFactory sessionFactory) {
		super(sessionFactory);
	}

	public BudgetEntry getEntryByCategoryId(final long id) {
		return uniqueResult(currentSession().createQuery("from BudgetEntry as entry where entry.category.id = :id")
				.setLong("id", id));
	}

	public List<BudgetEntry> listByMonth(final int year, final int month) {
		return list(currentSession()
				.createQuery("from BudgetEntry as entry where entry.forMonth = :month and entry.forYear = :year")
				.setInteger("month", month).setInteger("year", year));
	}

	public BudgetEntry getEntryByMonthAndCategory(final long id, final int year, final int month) {
		return uniqueResult(currentSession()
				.createQuery(
						"from BudgetEntry as entry where entry.category.id = :id and entry.forMonth = :month and entry.forYear = :year")
						.setLong("id", id).setInteger("month", month).setInteger("year", year));
	}
}
