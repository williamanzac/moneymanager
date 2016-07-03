package nz.co.anzac.moneymanager.service;

import java.io.Serializable;
import java.util.List;

import nz.co.anzac.moneymanager.dao.BudgetEntryDAO;
import nz.co.anzac.moneymanager.model.BudgetEntry;
import nz.co.anzac.moneymanager.model.Category;

public class BudgetEntryService extends AbstractCRUDService<BudgetEntry> {
	private final CategoryService categoryService;
	private final StatementEntryService statementEntryService;

	public BudgetEntryService(final BudgetEntryDAO entryDAO, final CategoryService categoryService,
			final StatementEntryService statementEntryService) {
		super(entryDAO);
		this.categoryService = categoryService;
		this.statementEntryService = statementEntryService;
	}

	public BudgetEntry updateCategory(final long id, final String name) {
		final Category category = categoryService.findCategoryByName(name);
		final BudgetEntry entry = read(id);
		entry.setCategory(category);
		return update(entry);
	}

	@Override
	public BudgetEntry read(final Serializable id) {
		final BudgetEntry entry = super.read(id);
		if (entry != null && entry.getForMonth() >= 0 && entry.getCategory() != null) {
			final double actual = statementEntryService.entrySumByMonth(entry.getForMonth(), entry.getCategory()
					.getId());
			entry.setActualAmount(actual);
		}
		return entry;
	}

	public List<BudgetEntry> listBaseEntries() {
		return listMonthEntries(-1, -1);
	}

	public List<BudgetEntry> listMonthEntries(final int year, final int month) {
		return ((BudgetEntryDAO) dao).listByMonth(year, month);
	}
}
