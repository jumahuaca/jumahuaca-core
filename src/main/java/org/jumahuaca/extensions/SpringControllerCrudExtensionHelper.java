package org.jumahuaca.extensions;

import java.util.List;

public interface SpringControllerCrudExtensionHelper<T> {
	
	List<T> mockSelectAllResult();
	
	void stubRepositoryFindAllOk(List<T> mockedResult);

	void stubRepositoryFindAllNotFound();

	void stubRepositoryFindAllError();

	T mockOne();

	void stubRepositoryFindByIdOk(T mockedResult);

	void stubRepositoryFindByIdNotFound(T mockedResult);

	void stubRepositoryUpdateOk(T mockedResult);

	void stubRepositoryUpdateError(T mockedResult);

	void stubRepositoryDeleteOk(T mockedResult);

	void stubRepositoryDeleteError(T mockedResult);

}
