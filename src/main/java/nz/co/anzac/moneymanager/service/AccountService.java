package nz.co.anzac.moneymanager.service;

import java.io.IOException;
import java.util.List;

import nz.co.anzac.moneymanager.dao.AccountDAO;
import nz.co.anzac.moneymanager.dao.StatementEntryDAO;
import nz.co.anzac.moneymanager.model.Account;
import nz.co.anzac.moneymanager.model.StatementEntry;
import nz.co.anzac.moneymanager.util.CsvUtil;

public class AccountService {
	private final AccountDAO accountDAO;
	private final StatementEntryDAO statementEntryDAO;

	public AccountService(final AccountDAO accountDAO, final StatementEntryDAO statementEntryDAO) {
		this.accountDAO = accountDAO;
		this.statementEntryDAO = statementEntryDAO;
	}

	public List<Account> listAccounts() {
		return accountDAO.list();
	}

	public Account newAccount(final Account account) {
		final Account persist = accountDAO.persist(account);
		return persist;
	}

	public Account getAccount(final long id) {
		return accountDAO.get(id);
	}

	public Account updateAccount(final Account account) {
		return accountDAO.persist(account);
	}

	public void processTransactions(final long id, final String transactions) throws ServiceException {
		final Account account = getAccount(id);
		try {
			final List<StatementEntry> list = new CsvUtil().readStatement(transactions);
			account.getEntries().addAll(list);
			updateAccount(account);
		} catch (final IOException e) {
			throw new ServiceException(e);
		}
	}

	public List<StatementEntry> getTransactions(long id) {
		return statementEntryDAO.getEntriesByAccountId(id);
	}
}
