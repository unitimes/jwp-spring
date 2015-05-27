package next.dao;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import core.jdbc.AbstractJdbcDaoSupport;

public class DBInitializer extends AbstractJdbcDaoSupport {

	private static final Logger logger = LoggerFactory.getLogger(DBInitializer.class);
	
	public DBInitializer () {
	}
	
	@PostConstruct
	public void initDB() {
		ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
		populator.addScript(new ClassPathResource("jwp.sql"));
		DatabasePopulatorUtils.execute(populator, getDataSource());
		
		logger.info("Initialized Database Schema!");
	}
}
