package nz.co.anzac.moneymanager.resource;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nz.co.anzac.moneymanager.model.StatementEntry;
import nz.co.anzac.moneymanager.service.StatementEntryService;

@Path("/transactions")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TransactionResource {
	private final StatementEntryService statementEntryService;

	public TransactionResource(final StatementEntryService statementEntryService) {
		this.statementEntryService = statementEntryService;
	}

	@PUT
	@Path("/{id}/category/{name}")
	@UnitOfWork
	public Response updateCaregory(final @PathParam("id") long id, final @PathParam("name") String name) {
		final StatementEntry statementEntry = statementEntryService.updateCategory(id, name);
		return Response.ok(statementEntry).build();
	}

	@GET
	@Path("/actual/{month}/{category}")
	@UnitOfWork
	public Response getActualAmount(final @PathParam("month") int month, final @PathParam("category") long catId) {
		final double actual = statementEntryService.entrySumByMonth(month, catId);
		return Response.ok(actual).build();
	}

	@GET
	@Path("/average/{category}")
	@UnitOfWork
	public Response getAverageAmount(final @PathParam("category") long catId) {
		final double actual = statementEntryService.entryMonthAvg(catId);
		return Response.ok(actual).build();
	}
}
