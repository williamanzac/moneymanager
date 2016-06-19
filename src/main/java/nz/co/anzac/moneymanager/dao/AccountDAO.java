package nz.co.anzac.moneymanager.dao;

import io.dropwizard.hibernate.AbstractDAO;

import java.io.Serializable;
import java.util.List;

import nz.co.anzac.moneymanager.model.Account;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

public class AccountDAO extends AbstractDAO<Account> {
	private final StatementEntryDAO statementEntryDAO;

	public AccountDAO(final SessionFactory sessionFactory, final StatementEntryDAO statementEntryDAO) {
		super(sessionFactory);
		this.statementEntryDAO = statementEntryDAO;
	}

	public List<Account> list() {
		return list(currentSession().createCriteria(getEntityClass()));
	}

	@Override
	public Account persist(final Account entity) throws HibernateException {
		if (entity.getEntries() != null && !entity.getEntries().isEmpty()) {
			entity.getEntries().forEach(e -> {
				e.setAccount(entity);
				statementEntryDAO.persist(e);
			});
		}
		return super.persist(entity);
	}

	@Override
	public Account get(final Serializable id) {
		return super.get(id);
	}
}
