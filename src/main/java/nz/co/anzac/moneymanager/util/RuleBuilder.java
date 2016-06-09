package nz.co.anzac.moneymanager.util;

import nz.co.anzac.moneymanager.model.RuleType;

public class RuleBuilder {
	private RuleType type;

	public RuleBuilder type(final RuleType type) {
		this.type = type;
		return this;
	}
}
