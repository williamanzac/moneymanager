package nz.co.anzac.moneymanager.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import nz.co.anzac.moneymanager.model.BankStatementEntry;
import nz.co.anzac.moneymanager.model.CreditStatementEntry;
import nz.co.anzac.moneymanager.model.StatementEntry;

import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CsvUtil {
	private static final Logger LOGGER =  LogManager.getLogger(CsvUtil.class);
	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@SuppressWarnings("unchecked")
	public List<StatementEntry> readStatement(final String data) throws IOException {
		final List<StatementEntry> entries = new ArrayList<>();
		final BufferedReader reader = new BufferedReader(new StringReader(data));
		final List<String> lines = IOUtils.readLines(reader);
		final boolean bank = lines.remove(0).split(",").length == 7;
		lines.forEach(l -> {
			final String[] columns = l.replaceAll("\"", "").split(",");
			if (bank) {
				final BankStatementEntry entry = new BankStatementEntry();
				try {
					final String dateStr = columns[0];
					final Date date = dateFormat.parse(dateStr);
					entry.setDate(date);
				} catch (final Exception e) {
					LOGGER.error(e);
				}
				entry.setAmount(Double.parseDouble(columns[1]));
				entry.setOtherParty(columns[2]);
				entry.setDescription(columns[3]);
				if (columns.length > 4) {
					entry.setReference(columns[4]);
				}
				if (columns.length > 5) {
					entry.setParticulars(columns[5]);
				}
				if (columns.length > 6) {
					entry.setAnalysisCode(columns[6]);
				}

				entries.add(entry);
			} else {
				final CreditStatementEntry entry = new CreditStatementEntry();
				try {
					entry.setProcessDate(dateFormat.parse(columns[0]));
				} catch (final Exception e) {
					LOGGER.error(e);
				}
				entry.setAmount(Double.parseDouble(columns[1]));
				entry.setOtherParty(columns[2]);
				entry.setCreditPlanName(columns[3]);
				try {
					entry.setDate(dateFormat.parse(columns[4]));
				} catch (final Exception e) {
					LOGGER.error(e);
				}
				if (columns.length > 5) {
					entry.setForeignDetails(columns[5]);
				}
				if (columns.length > 6) {
					entry.setCity(columns[6]);
				}
				if (columns.length > 7) {
					entry.setCountryCode(columns[7]);
				}

				entries.add(entry);
			}
		});
		return entries;
	}
}
