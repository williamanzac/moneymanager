package nz.co.anzac.moneymanager.resource;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import nz.co.anzac.moneymanager.view.UIView;

@Path("/ui")
@Produces(MediaType.TEXT_HTML)
public class UIResource {

	@GET
	public Response getUI() {
		return Response.ok(new UIView()).build();
	}
}
