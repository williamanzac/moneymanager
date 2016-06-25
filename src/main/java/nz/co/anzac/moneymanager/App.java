package nz.co.anzac.moneymanager;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import nz.co.anzac.moneymanager.config.AppConfiguration;
import nz.co.anzac.moneymanager.dao.AccountDAO;
import nz.co.anzac.moneymanager.dao.CategoryDAO;
import nz.co.anzac.moneymanager.dao.StatementEntryDAO;
import nz.co.anzac.moneymanager.model.Account;
import nz.co.anzac.moneymanager.model.BankStatementEntry;
import nz.co.anzac.moneymanager.model.Category;
import nz.co.anzac.moneymanager.model.Condition;
import nz.co.anzac.moneymanager.model.CreditStatementEntry;
import nz.co.anzac.moneymanager.model.Rule;
import nz.co.anzac.moneymanager.model.StatementEntry;
import nz.co.anzac.moneymanager.resource.AccountResource;
import nz.co.anzac.moneymanager.resource.CategoryResource;
import nz.co.anzac.moneymanager.resource.UIResource;
import nz.co.anzac.moneymanager.service.AccountService;
import nz.co.anzac.moneymanager.service.CategoryService;

import org.hibernate.SessionFactory;

/**
 * Hello world!
 *
 */
public class App extends Application<AppConfiguration> {
	public static void main(final String[] args) throws Exception {
		new App().run(args);
	}

	private final HibernateBundle<AppConfiguration> hibernate = new HibernateBundle<AppConfiguration>(
			StatementEntry.class, BankStatementEntry.class, CreditStatementEntry.class, Category.class,
			Condition.class, Rule.class, Account.class) {
		@Override
		public DataSourceFactory getDataSourceFactory(final AppConfiguration configuration) {
			return configuration.getDataSourceFactory();
		}
	};

	@Override
	public void initialize(final Bootstrap<AppConfiguration> bootstrap) {
		super.initialize(bootstrap);
		bootstrap.addBundle(hibernate);
		bootstrap.addBundle(new ViewBundle<AppConfiguration>());
		bootstrap.addBundle(new AssetsBundle("/js", "/js", null, "js"));
		bootstrap.addBundle(new AssetsBundle("/css", "/css", null, "css"));
	}

	@Override
	public String getName() {
		return "Money Manager";
	}

	@Override
	public void run(final AppConfiguration configuration, final Environment environment) throws Exception {
		final SessionFactory sessionFactory = hibernate.getSessionFactory();

		final StatementEntryDAO statementEntryDAO = new StatementEntryDAO(sessionFactory);
		final AccountDAO accountDAO = new AccountDAO(sessionFactory, statementEntryDAO);
		final CategoryDAO categoryDAO = new CategoryDAO(sessionFactory);

		final AccountService accountService = new AccountService(accountDAO, statementEntryDAO);
		final CategoryService categoryService = new CategoryService(categoryDAO, statementEntryDAO);

		final AccountResource accountResource = new AccountResource(accountService);
		final UIResource uiResource = new UIResource();
		final CategoryResource categoryResource = new CategoryResource(categoryService);

		environment.jersey().register(accountResource);
		environment.jersey().register(uiResource);
		environment.jersey().register(categoryResource);
		// environment.jersey().register(AccountResource.class);
		// environment.jersey().packages("nz.co.anzac.moneymanager.service", "nz.co.anzac.moneymanager.resource",
		// "nz.co.anzac.moneymanager.dao");
	}
}
