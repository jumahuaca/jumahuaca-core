package org.jumahuaca.examples.jdbc.dao;

import java.io.File;

import javax.sql.DataSource;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.h2.jdbcx.JdbcDataSource;
import org.h2.tools.RunScript;

public abstract class JdbcCrudDaoIntegrationTests {
	
	private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
	private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static void createSchema(String schemaFileName) throws Exception {
		RunScript.execute(JDBC_URL, USER, PASSWORD, schemaFileName, null, false);
	}
	
	public void prepareDataset(String modelFileName) throws Exception {
		IDataSet dataSet = readDataSet(modelFileName);
		cleanlyInsert(dataSet);
	}
	
	public DataSource buildDataSource() {
		JdbcDataSource dataSource = new JdbcDataSource();
		dataSource.setURL(JDBC_URL);
		dataSource.setUser(USER);
		dataSource.setPassword(PASSWORD);
		return dataSource;
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
	

}
