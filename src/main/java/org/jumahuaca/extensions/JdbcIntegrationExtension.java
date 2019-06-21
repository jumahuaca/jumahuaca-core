package org.jumahuaca.extensions;

import java.io.File;
import java.lang.reflect.Field;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;
import org.jumahuaca.examples.jdbc.dao.InMemoryDatasource;
import org.jumahuaca.exceptions.MoreThanOneMockToInjectException;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;

public class JdbcIntegrationExtension implements TestInstancePostProcessor, BeforeAllCallback, BeforeEachCallback{
	
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";
	private final String schemaFileName ;
	private final String modelFileName;

	
	
	public JdbcIntegrationExtension(String schemaFileName, String modelFileName) {
		this.schemaFileName = schemaFileName;
		this.modelFileName = modelFileName;
	}
	
	public static class Builder {

		public JdbcIntegrationExtension build(String schemaFileName, String modelFileName) {
			return new JdbcIntegrationExtension(schemaFileName,modelFileName);
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		List<Field> fields = AnnotationSupport.findPublicAnnotatedFields(testInstance.getClass(), DataSource.class,
				InMemoryDatasource.class);
		if (fields.size() > 1) {
			throw new MoreThanOneMockToInjectException();
		}
		if (fields != null && !fields.isEmpty()) {
			Field field = fields.iterator().next();
			field.set(testInstance, buildDataSource());
		}
		
	}
	
	private DataSource buildDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(JDBC_URL);
		dataSource.setUser(USER);
		dataSource.setPassword(PASSWORD);
		return dataSource;
	}
	
	public void prepareDataset(String modelFileName) throws Exception {
		IDataSet dataSet = readDataSet(modelFileName);
		cleanlyInsert(dataSet);
	}
	
	private IDataSet readDataSet(String modelFileName) throws Exception {
		return new FlatXmlDataSetBuilder().build(new File(modelFileName));
	}

	private void cleanlyInsert(IDataSet dataSet) throws Exception {
		IDatabaseTester databaseTester = new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
		databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
		databaseTester.setDataSet(dataSet);
		databaseTester.onSetup();
	}

	@Override
	public void beforeAll(ExtensionContext context) throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, schemaFileName, null, false);
		
	}

	@Override
	public void beforeEach(ExtensionContext context) throws Exception {
		prepareDataset(modelFileName);		
	}
	

}
