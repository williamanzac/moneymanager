package nz.co.anzac.moneymanager.util;

import java.lang.reflect.Field;
import java.util.List;

import nz.co.anzac.moneymanager.model.Condition;
import nz.co.anzac.moneymanager.model.Rule;
import nz.co.anzac.moneymanager.model.RuleType;
import nz.co.anzac.moneymanager.model.StatementEntry;

public class RuleProcessor {

	public static RuleProcessor INSTANCE = new RuleProcessor();

	public static void processRules(final List<Rule> rules, final StatementEntry entry) {
		rules.forEach(r -> {
			INSTANCE.processRule(r, entry);
		});
	}

	public void processRule(final Rule rule, final StatementEntry entry) {
		boolean matched = false;
		final List<Condition> conditions = rule.getConditions();
		for (final Condition condition : conditions) {
			matched = evaluateCondition(condition, entry);
			if (matched && rule.getType() == RuleType.OR || !matched && rule.getType() == RuleType.AND) {
				break;
			}
		}
		if (matched) {
			entry.setCategory(rule.getCategory());
		}
	}

	private boolean evaluateCondition(final Condition condition, final StatementEntry entry) {
		try {
			final Field field = entry.getClass().getDeclaredField(condition.getField());
			field.setAccessible(true);
			switch (condition.getType()) {
			case CONTAINS:
				if (((String) field.get(entry)).contains(condition.getValue())) {
					return true;
				}
				break;
			default:
				break;
			}
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
}
