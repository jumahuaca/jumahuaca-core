package org.jumahuaca.examples.jdbc.dao;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

@ExtendWith(MockitoExtension.class)
public abstract class JdbcCrudDaoTests<T,ID> {

	@Mock
	protected DataSource ds;

	@Mock
	protected Connection connection;

	@Mock
	protected PreparedStatement ps;

	@Spy
	protected ResultSet rs;
	
	public abstract List<T> invokeDaoSearchAll();
	
	public abstract T invokeDaoFindById(ID id);
	
	public abstract void invokeDaoCreate(T entity);
	
	public abstract void invokeDaoUpdate(T entity);
	
	public abstract void invokeDaoDelete(T entity);

	public abstract void setup() throws SQLException;

	public abstract Map<String, Object> stubSelectOneResultSet();

	public abstract Map<String, List<Object>> stubSelectAllResultSet();

	public void stubDatasource() throws SQLException {
		when(ds.getConnection()).thenReturn(connection);
	}
	
	public void stubSelectOneQueryOk() throws SQLException {
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(true, false);
		Map<String, Object> resultSet = stubSelectOneResultSet();

		for (Entry<String, Object> entry : resultSet.entrySet()) {
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

	public void verifyResourceClose() throws SQLException {
		verify(connection).close();
		verify(ps).close();
	}

	public void verifyConnectionClose() throws SQLException {
		verify(connection).close();
	}

	public void verifyExecuteUpdate() throws SQLException {
		verify(ps).executeUpdate();
	}

	public void stubSelectOneQueryNotFound() throws SQLException {
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false, false);
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}

	public void stubSelectOneQueryError() throws SQLException {
		when(ps.executeQuery()).thenThrow(SQLException.class);
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}

	public void stubSelectAllQueryOk() throws SQLException {
		Map<String, List<Object>> results = stubSelectAllResultSet();
		when(rs.next()).thenReturn(true, true, false);
		when(ps.executeQuery()).thenReturn(rs);
		for (Entry<String, List<Object>> entryResult : results.entrySet()) {
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

	public void stubConnectionError() throws SQLException {
		when(connection.prepareStatement(anyString())).thenThrow(SQLException.class);
	}

	public void stubQueryOk() throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(ps);
	}
	
	public void stubSelectAllNotFound() throws SQLException {
		when(connection.prepareStatement(anyString())).thenReturn(ps);
		when(ps.executeQuery()).thenReturn(rs);
		when(rs.next()).thenReturn(false);
	}

}
