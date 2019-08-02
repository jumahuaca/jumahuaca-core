package org.jumahuaca.extensions;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.jumahuaca.examples.jdbc.dao.MockedConnection;
import org.jumahuaca.examples.jdbc.dao.MockedDatasource;
import org.jumahuaca.examples.jdbc.dao.MockedPreparedStatement;
import org.jumahuaca.examples.jdbc.dao.SpiedResultSet;
import org.jumahuaca.exceptions.MoreThanOneMockToInjectException;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;
import org.junit.platform.commons.support.AnnotationSupport;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class JdbcExtension implements TestInstancePostProcessor {

	public static class Builder {

		public JdbcExtension build() {
			return new JdbcExtension();
		}
	}

	public static Builder builder() {
		return new Builder();
	}

	@Override
	public void postProcessTestInstance(Object testInstance, ExtensionContext context) throws Exception {
		injectMock(testInstance, DataSource.class, MockedDatasource.class);
		injectMock(testInstance, Connection.class, MockedConnection.class);
		injectMock(testInstance, PreparedStatement.class, MockedPreparedStatement.class);
		injectSpy(testInstance, ResultSet.class, SpiedResultSet.class);
	}

	private void injectMock(Object testInstance, Class<?> fieldType, Class<? extends Annotation> annotationType)
			throws IllegalAccessException {
		List<Field> fields = AnnotationSupport.findPublicAnnotatedFields(testInstance.getClass(), fieldType,
				annotationType);
		if (fields.size() > 1) {
			throw new MoreThanOneMockToInjectException();
		}
		if (fields != null && !fields.isEmpty()) {
			Field field = fields.iterator().next();
			field.set(testInstance, Mockito.mock(fieldType));
		}
	}

	private void injectSpy(Object testInstance, Class<?> fieldType, Class<? extends Annotation> annotationType)
			throws IllegalAccessException {
		List<Field> fields = AnnotationSupport.findPublicAnnotatedFields(testInstance.getClass(), fieldType,
				annotationType);
		if (fields.size() > 1) {
			throw new MoreThanOneMockToInjectException();
		}
		if (fields != null && !fields.isEmpty()) {
			Field field = fields.iterator().next();
			field.set(testInstance, Mockito.spy(fieldType));
		}
	}
	
	public void mockDatasource(DataSource ds, Connection connection) throws SQLException {
		when(ds.getConnection()).thenReturn(connection);
	}
	
	public void mockSelectOneQueryOk(PreparedStatement ps, ResultSet rs, Connection connection, Map<String, Object> mockedSelectOneResult) throws SQLException {
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true, false);

		for (Entry<String, Object> entry : mockedSelectOneResult.entrySet()) {
			String k = entry.getKey();
			Object v = entry.getValue();
			if (v instanceof String) {
				when(rs.getString(k)).thenReturn((String) v);
			} else if (v instanceof Integer) {
				when(rs.getInt(k)).thenReturn((Integer) v);
			} else if (v instanceof Long) {
				when(rs.getLong(k)).thenReturn((Long) v);
			} else if (v instanceof Double) {
				when(rs.getDouble(k)).thenReturn((Double) v);
			} else if (v instanceof Date) {
				when(rs.getDate(k)).thenReturn((Date) v);
			}
		}
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}

	public void verifyResourceClose(PreparedStatement ps, Connection connection) throws SQLException {
		verify(connection).close();
		verify(ps).close();
	}

	public void verifyConnectionClose(Connection connection) throws SQLException {
		verify(connection).close();
	}

	public void verifyExecuteUpdate(PreparedStatement ps) throws SQLException {
		verify(ps).executeUpdate();
	}

	public void mockSelectOneQueryNotFound(PreparedStatement ps, Connection connection, ResultSet rs) throws SQLException {
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false, false);
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}

	public void mockSelectOneQueryError(PreparedStatement ps, Connection connection, ResultSet rs) throws SQLException {
		when(ps.executeQuery()).thenThrow(SQLException.class);
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}

	public void mockSelectAllQueryOk(PreparedStatement ps, Connection connection, ResultSet rs, Map<String, List<Object>> mockedSelectAll) throws SQLException {
		when(rs.next()).thenReturn(true, true, false);
		when(ps.executeQuery()).thenReturn(rs);
		for (Entry<String, List<Object>> entryResult : mockedSelectAll.entrySet()) {
			String k = entryResult.getKey();
			final List<Object> v = entryResult.getValue();

			if (v != null && !v.isEmpty() && v.iterator().next() instanceof String) {
				doAnswer(new Answer<String>() {
					private int count = 0;

					public String answer(InvocationOnMock invocation) {
						String result = (String) v.get(count);
						count++;
						return result;
					}
				}).when(rs).getString(k);
			} else if (v != null && !v.isEmpty() && v.iterator().next() instanceof Double) {
				doAnswer(new Answer<Double>() {
					private int count = 0;

					public Double answer(InvocationOnMock invocation) {
						Double result = (Double) v.get(count);
						count++;
						return result;
					}
				}).when(rs).getDouble(k);
			} else if (v != null && !v.isEmpty() && v.iterator().next() instanceof Integer) {
				doAnswer(new Answer<Integer>() {
					private int count = 0;

					public Integer answer(InvocationOnMock invocation) {
						Integer result = (Integer) v.get(count);
						count++;
						return result;
					}
				}).when(rs).getInt(k);
			} else if (v != null && !v.isEmpty() && v.iterator().next() instanceof Long) {
				doAnswer(new Answer<Long>() {
					private int count = 0;

					public Long answer(InvocationOnMock invocation) {
						Long result = (Long) v.get(count);
						count++;
						return result;
					}
				}).when(rs).getLong(k);
			} else if (v != null && !v.isEmpty() && v.iterator().next() instanceof Date) {
				doAnswer(new Answer<Date>() {
					private int count = 0;

					public Date answer(InvocationOnMock invocation) {
						Date result = (Date) v.get(count);
						count++;
						return result;
					}
				}).when(rs).getDate(k);
			}

		}
		when(connection.prepareStatement(anyString())).thenReturn(ps);

	}

	public void mockConnectionError(Connection connection) throws SQLException {
		when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
	}

	public void mockQueryOk(Connection connection,PreparedStatement ps) throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}
	
	public void mockSelectAllNotFound(PreparedStatement ps, Connection connection, ResultSet rs) throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);
	}


}
