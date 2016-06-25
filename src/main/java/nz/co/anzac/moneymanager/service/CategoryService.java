package nz.co.anzac.moneymanager.service;

import java.util.List;

import nz.co.anzac.moneymanager.dao.CategoryDAO;
import nz.co.anzac.moneymanager.dao.StatementEntryDAO;
import nz.co.anzac.moneymanager.model.Category;
import nz.co.anzac.moneymanager.model.StatementEntry;

public class CategoryService extends AbstractCRUDService<Category> {
	private final StatementEntryDAO statementEntryDAO;

	public CategoryService(final CategoryDAO categoryDAO, final StatementEntryDAO statementEntryDAO) {
		super(categoryDAO);
		this.statementEntryDAO = statementEntryDAO;
	}

	public Category getRootCategory() throws ServiceException {
		try {
			return ((CategoryDAO) dao).getRootCategory();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}

	public List<StatementEntry> getTransactions(long id) {
		return statementEntryDAO.getEntriesByCategoryId(id);
	}
}
