package nz.co.anzac.moneymanager.representation;

import com.fasterxml.jackson.annotation.JsonProperty;

import nz.co.anzac.moneymanager.model.Account;

public class AccountName {
	private long id;
	private String name;

	public AccountName(final Account account) {
		id = account.getId();
		name = account.getName();
	}

	@JsonProperty
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	@JsonProperty
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
