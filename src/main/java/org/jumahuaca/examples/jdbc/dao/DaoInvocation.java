package org.jumahuaca.examples.jdbc.dao;

import java.util.List;

public interface DaoInvocation<T,ID> {
	
	public abstract List<T> invokeDaoSearchAll();
	
	T invokeDaoFindById(ID id);
	
	void invokeDaoCreate(T entity);
	
	void invokeDaoUpdate(T entity);
	
	void invokeDaoDelete(T entity);
}
