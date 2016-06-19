package nz.co.anzac.moneymanager.resource;

import io.dropwizard.hibernate.UnitOfWork;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nz.co.anzac.moneymanager.model.Account;
import nz.co.anzac.moneymanager.model.StatementEntry;
import nz.co.anzac.moneymanager.representation.AccountDetail;
import nz.co.anzac.moneymanager.representation.AccountName;
import nz.co.anzac.moneymanager.service.AccountService;
import nz.co.anzac.moneymanager.service.ServiceException;

@Path("/accounts")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AccountResource {
	private final AccountService accountService;

	public AccountResource(final AccountService accountService) {
		this.accountService = accountService;
	}

	@GET
	@UnitOfWork
	public Response listAccounts() {
		final List<Account> list = accountService.listAccounts();
		final List<AccountName> names = new ArrayList<>();
		list.forEach(a -> {
			names.add(new AccountName(a));
		});
		return Response.ok(names).build();
	}

	@POST
	@UnitOfWork
	public Response newAccount(final Account account) {
		final Account account2 = accountService.newAccount(account);
		final AccountDetail accountDetail = new AccountDetail(account2);
		return Response.ok(accountDetail).build();
	}

	@PUT
	@UnitOfWork
	public Response updateAccount(final Account account) {
		final Account account2 = accountService.updateAccount(account);
		final AccountDetail accountDetail = new AccountDetail(account2);
		return Response.ok(accountDetail).build();
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getAccount(final @PathParam("id") long id) {
		final Account account = accountService.getAccount(id);
		final AccountDetail accountDetail = new AccountDetail(account);
		return Response.ok(accountDetail).build();
	}

	@PUT
	@Path("/{id}/transactions")
	@Consumes("text/csv")
	@UnitOfWork
	public Response postTransactions(final @PathParam("id") long id, final String transactions) throws ServiceException {
		accountService.processTransactions(id, transactions);
		return Response.ok().build();
	}

	@GET
	@Path("/{id}/transactions")
	@UnitOfWork
	public Response getTransactions(final @PathParam("id") long id) throws ServiceException {
		final List<StatementEntry> transactions = accountService.getTransactions(id);
		return Response.ok(transactions).build();
	}
}
