package nz.co.anzac.moneymanager.dao;

import java.util.Date;
import java.util.List;

import nz.co.anzac.moneymanager.model.StatementEntry;

import org.hibernate.SessionFactory;

@SuppressWarnings({ "unchecked", "deprecation" })
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

	public double entrySumForMonth(final Date startDate, final Date endDate, final long id) {
		final Object value = currentSession()
				.createQuery(
						"select sum(entry.amount) from StatementEntry entry where entry.category.id = :id and entry.date between :startDate and :endDate")
				.setLong("id", id).setDate("startDate", startDate).setDate("endDate", endDate).uniqueResult();
		return value == null ? 0 : (Double) value;
	}

	public double entryAverage(long catId) {
		final Object value = currentSession()
				.createQuery("select avg(entry.amount) from StatementEntry entry where entry.category.id = :id")
				.setLong("id", catId).uniqueResult();
		return value == null ? 0 : (Double) value;
	}
}
