package nz.co.anzac.moneymanager;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;
import nz.co.anzac.moneymanager.config.AppConfiguration;
import nz.co.anzac.moneymanager.model.BankStatementEntry;
import nz.co.anzac.moneymanager.model.Category;
import nz.co.anzac.moneymanager.model.Condition;
import nz.co.anzac.moneymanager.model.CreditStatementEntry;
import nz.co.anzac.moneymanager.model.Rule;
import nz.co.anzac.moneymanager.model.StatementEntry;

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
			Condition.class, Rule.class) {
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
	}

	@Override
	public String getName() {
		return "Money Manager";
	}

	@Override
	public void run(final AppConfiguration configuration, final Environment environment) throws Exception {
		// TODO Auto-generated method stub

	}
}
