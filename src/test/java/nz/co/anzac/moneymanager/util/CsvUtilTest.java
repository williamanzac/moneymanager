package nz.co.anzac.moneymanager.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import nz.co.anzac.moneymanager.model.BankStatementEntry;
import nz.co.anzac.moneymanager.model.CreditStatementEntry;
import nz.co.anzac.moneymanager.model.StatementEntry;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CsvUtilTest {

	private CsvUtil cut;

	@Before
	public void setup() {
		cut = new CsvUtil();
	}

	@Test
	public void testPasingBankCSV() throws IOException {
		final InputStream inputStream = CsvUtilTest.class.getResourceAsStream("/bank.csv");
		final String string = IOUtils.toString(inputStream);
		final List<StatementEntry> list = cut.readStatement(string);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
		Assert.assertTrue(list.get(0) instanceof BankStatementEntry);
		final Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(2016, 2, 29, 0, 0, 0);
		final Date date = calendar.getTime();
		Assert.assertEquals(date.getTime(), list.get(0).getDate().getTime());
	}

	@Test
	public void testPasingCreditCSV() throws IOException {
		final InputStream inputStream = CsvUtilTest.class.getResourceAsStream("/credit.csv");
		final String string = IOUtils.toString(inputStream);
		final List<StatementEntry> list = cut.readStatement(string);
		Assert.assertNotNull(list);
		Assert.assertFalse(list.isEmpty());
		Assert.assertTrue(list.get(0) instanceof CreditStatementEntry);
	}
}
