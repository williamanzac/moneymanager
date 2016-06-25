package nz.co.anzac.moneymanager.dao;

import nz.co.anzac.moneymanager.model.Account;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;

public class AccountDAO extends AbstractCRUDDAO<Account> {
	final StatementEntryDAO statementEntryDAO;

	public AccountDAO(final SessionFactory sessionFactory, final StatementEntryDAO statementEntryDAO) {
		super(sessionFactory);
		this.statementEntryDAO = statementEntryDAO;
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
}
