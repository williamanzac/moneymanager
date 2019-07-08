package nz.co.anzac.moneymanager.service;

import java.io.Serializable;
import java.util.ArrayList;
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
		if (entry != null && entry.getCategory() != null) {
			final double actual;
			if (entry.getForMonth() >= 0) {
				actual = statementEntryService.entrySumByMonth(entry.getForMonth(), entry.getCategory().getId());
			} else {
				actual = statementEntryService.entryMonthAvg(entry.getCategory().getId());
			}
			entry.setActualAmount(actual);
		}
		return entry;
	}

	@Override
	public List<BudgetEntry> list() {
		final List<BudgetEntry> list = super.list();
		list.forEach(e -> {
			if (e.getCategory() != null) {
				final double actual;
				if (e.getForMonth() >= 0) {
					actual = statementEntryService.entrySumByMonth(e.getForMonth(), e.getCategory().getId());
				} else {
					actual = statementEntryService.entryMonthAvg(e.getCategory().getId());
				}
				e.setActualAmount(actual);
			}
		});
		return list;
	}

	public List<BudgetEntry> listBaseEntries() {
		return listMonthEntries(-1, -1);
	}

	public List<BudgetEntry> listMonthEntries(final int year, final int month) {
		return ((BudgetEntryDAO) dao).listByMonth(year, month);
	}

	public List<BudgetEntry> createMonthEntries(final int year, final int month) {
		final List<BudgetEntry> entries = new ArrayList<>();
		final List<Category> categories = categoryService.list();
		categories.forEach(c -> {
			final BudgetEntry entry = new BudgetEntry();
			entry.setForMonth(month);
			entry.setForYear(year);
			entry.setCategory(c);
			final BudgetEntry baseEntry = ((BudgetEntryDAO) dao).getEntryByMonthAndCategory(c.getId(), -1, -1);
			entry.setBudgetAmount(baseEntry.getBudgetAmount());
			((BudgetEntryDAO) dao).persist(entry);
			entries.add(entry);
		});
		return entries;
	}

	@Override
	public BudgetEntry update(final BudgetEntry entity) {
		final BudgetEntry newEntry = read(entity.getId());
		if (entity.getCategory() != null) {
			final Category category = categoryService.read(entity.getCategory().getId());
			entity.setCategory(category);
		}
		newEntry.update(entity);
		final BudgetEntry updatedEntry = super.update(newEntry);
		Category parent = updatedEntry.getCategory().getParent();
		while (parent != null) {
			updateParent(parent);
			parent = parent.getParent();
		}
		return updatedEntry;
	}

	private void updateParent(final Category category) {
		if (category.getSubCategories().size() == 0) {
			return;
		}
		final BudgetEntry entry = ((BudgetEntryDAO) dao).getEntryByCategoryId(category.getId());
		double sum = 0;
		for (final Category c : category.getSubCategories()) {
			final BudgetEntry budgetEntry = ((BudgetEntryDAO) dao).getEntryByCategoryId(c.getId());
			sum += budgetEntry.getBudgetAmount();
		}
		entry.setBudgetAmount(sum);
		super.update(entry);
	}
}
