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

import nz.co.anzac.moneymanager.model.Category;
import nz.co.anzac.moneymanager.model.StatementEntry;
import nz.co.anzac.moneymanager.representation.TreeNode;
import nz.co.anzac.moneymanager.service.CategoryService;
import nz.co.anzac.moneymanager.service.ServiceException;
import nz.co.anzac.moneymanager.util.TreeNodeUtil;

@Path("/categories")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class CategoryResource {
	private final CategoryService categoryService;

	public CategoryResource(final CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GET
	@UnitOfWork
	public Response listCategories() {
		if (categoryService.list().isEmpty()) {
			Category root = new Category();
			root.setName("root");
			categoryService.create(root);
		}

		final List<Category> list = categoryService.list();
		return Response.ok(list).build();
	}

	@POST
	@Path("/{id}")
	@UnitOfWork
	public Response newCategory(final @PathParam("id") long id, final Category category) {
		final Category parent = categoryService.read(id);
		category.setParent(parent);
		parent.getSubCategories().add(category);
		final Category category2 = categoryService.create(category);
		return Response.ok(category2).build();
	}

	@PUT
	@UnitOfWork
	public Response updateCategory(final Category category) {
		final Category category2 = categoryService.update(category);
		return Response.ok(category2).build();
	}

	@GET
	@Path("/{id}")
	@UnitOfWork
	public Response getCategory(final @PathParam("id") long id) {
		final Category category = categoryService.read(id);
		return Response.ok(category).build();
	}

	@GET
	@Path("/tree")
	@UnitOfWork
	public Response getCategoryTree() throws ServiceException {
		final Category root = categoryService.getRootCategory();
		final TreeNode node = TreeNodeUtil.toTreeNode(root);
		final List<TreeNode> nodes = new ArrayList<>();
		nodes.add(node);
		return Response.ok(nodes).build();
	}

	@GET
	@Path("/{id}/transactions")
	@UnitOfWork
	public Response getTransactions(final @PathParam("id") long id) throws ServiceException {
		final List<StatementEntry> transactions = categoryService.getTransactions(id);
		return Response.ok(transactions).build();
	}
}
