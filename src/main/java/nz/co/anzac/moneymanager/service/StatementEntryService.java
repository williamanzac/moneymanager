package nz.co.anzac.moneymanager.service;

import java.util.Calendar;
import java.util.Date;

import nz.co.anzac.moneymanager.dao.StatementEntryDAO;
import nz.co.anzac.moneymanager.model.Category;
import nz.co.anzac.moneymanager.model.StatementEntry;

public class StatementEntryService extends AbstractCRUDService<StatementEntry> {
	private final CategoryService categoryService;

	public StatementEntryService(final StatementEntryDAO entryDAO, final CategoryService categoryService) {
		super(entryDAO);
		this.categoryService = categoryService;
	}

	public StatementEntry updateCategory(final long id, final String name) {
		final Category category = categoryService.findCategoryByName(name);
		final StatementEntry entry = read(id);
		entry.setCategory(category);
		return update(entry);
	}

	public double entrySumByMonth(final int forMonth, final long catId) {
		final Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MONTH, forMonth);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		final Date startDate = calendar.getTime();
		calendar.add(Calendar.MONTH, 1);
		final Date endDate = calendar.getTime();
		return ((StatementEntryDAO) dao).entrySumForMonth(startDate, endDate, catId);
	}

	public double entryMonthAvg(final long catId) {
		return ((StatementEntryDAO) dao).entryAverage(catId);
	}
}
