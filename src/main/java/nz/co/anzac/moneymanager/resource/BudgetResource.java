package nz.co.anzac.moneymanager.resource;

import io.dropwizard.hibernate.UnitOfWork;

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

import nz.co.anzac.moneymanager.model.BudgetEntry;
import nz.co.anzac.moneymanager.service.BudgetEntryService;

@Path("/budget")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class BudgetResource {
	private final BudgetEntryService budgetEntryService;

	public BudgetResource(final BudgetEntryService budgetEntryService) {
		super();
		this.budgetEntryService = budgetEntryService;
	}

	@GET
	@Path("/base")
	@UnitOfWork
	public Response listBaseBudget() {
		final List<BudgetEntry> entries = budgetEntryService.listBaseEntries();
		return Response.ok(entries).build();
	}

	@GET
	@Path("/{year}/{month}")
	@UnitOfWork
	public Response listMonthBudget(final @PathParam("year") int year, final @PathParam("month") int month) {
		final List<BudgetEntry> entries = budgetEntryService.listMonthEntries(year, month);
		return Response.ok(entries).build();
	}

	@GET
	@Path("/entries")
	@UnitOfWork
	public Response getEntries() {
		final List<BudgetEntry> entries = budgetEntryService.list();
		return Response.ok(entries).build();
	}

	@GET
	@Path("/entries/{id}")
	@UnitOfWork
	public Response getEntry(final @PathParam("id") long id) {
		final BudgetEntry entry = budgetEntryService.read(id);
		return Response.ok(entry).build();
	}

	@POST
	@Path("/entries")
	@UnitOfWork
	public Response createEntry(final BudgetEntry budgetEntry) {
		final BudgetEntry entry = budgetEntryService.create(budgetEntry);
		return Response.ok(entry).build();
	}

	@PUT
	@Path("/entries")
	@UnitOfWork
	public Response updateEntry(final BudgetEntry budgetEntry) {
		final BudgetEntry entry = budgetEntryService.update(budgetEntry);
		return Response.ok(entry).build();
	}
}
