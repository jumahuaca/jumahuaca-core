package org.jumahuaca.extensions;

import java.util.List;

public interface HttpWebServiceTestDoubleHelper<T> {
	
	List<T> mockSelectAllResult();
	
	void mockRepositoryFindAllOk(List<T> mockedResult);

	void mockRepositoryFindAllNotFound();

	void mockRepositoryFindAllError();

	T mockOne();

	void mockRepositoryFindByIdOk(T mockedResult);

	void mockRepositoryFindByIdNotFound(T mockedResult);

	void mockRepositoryFindByIdError(T mockedResult);

	void mockRepositoryUpdateOk(T mockedResult);

	void mockRepositoryUpdateError(T mockedResult);

	void mockRepositoryDeleteOk(T mockedResult);

	void mockRepositoryDeleteError(T mockedResult);

	void mockRepositoryCreateOk(T mockedResult);
	
	void mockRepositoryCreateServerError(T mockedResult);

	void mockRepositoryCreateUknownError(T mockedResult);

	void mockRepositoryUpdateUknownError(T mockedResult);

	void mockRepositoryDeleteUknownError(T mockedResult);

	void mockRepositoryDeleteNotFoundError(T mockedResult);
}
