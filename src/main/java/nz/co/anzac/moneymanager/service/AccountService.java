package nz.co.anzac.moneymanager.service;

import java.io.IOException;
import java.util.List;

import nz.co.anzac.moneymanager.dao.AccountDAO;
import nz.co.anzac.moneymanager.dao.StatementEntryDAO;
import nz.co.anzac.moneymanager.model.Account;
import nz.co.anzac.moneymanager.model.StatementEntry;
import nz.co.anzac.moneymanager.util.CsvUtil;

public class AccountService extends AbstractCRUDService<Account> {
	private final StatementEntryDAO statementEntryDAO;

	public AccountService(final AccountDAO accountDAO, final StatementEntryDAO statementEntryDAO) {
		super(accountDAO);
		this.statementEntryDAO = statementEntryDAO;
	}

	public void processTransactions(final long id, final String transactions) throws ServiceException {
		final Account account = read(id);
		try {
			final List<StatementEntry> list = new CsvUtil().readStatement(transactions);
			account.getEntries().addAll(list);
			update(account);
		} catch (final IOException e) {
			throw new ServiceException(e);
		}
	}

	public List<StatementEntry> getTransactions(final long id) {
		return statementEntryDAO.getEntriesByAccountId(id);
	}
}
