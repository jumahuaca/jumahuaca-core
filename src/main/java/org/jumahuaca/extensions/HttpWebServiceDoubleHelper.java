package org.jumahuaca.extensions;

import java.util.List;

public interface HttpWebServiceDoubleHelper<T> {
	
	List<T> mockSelectAllResult();
	
	void stubRepositoryFindAllOk(List<T> mockedResult);

	void stubRepositoryFindAllNotFound();

	void stubRepositoryFindAllError();

	T mockOne();

	void stubRepositoryFindByIdOk(T mockedResult);

	void stubRepositoryFindByIdNotFound(T mockedResult);

	void stubRepositoryFindByIdError(T mockedResult);

	void stubRepositoryUpdateOk(T mockedResult);

	void stubRepositoryUpdateError(T mockedResult);

	void stubRepositoryDeleteOk(T mockedResult);

	void stubRepositoryDeleteError(T mockedResult);

	void stubRepositoryCreateOk(T mockedResult);
	
	void stubRepositoryCreateServerError(T mockedResult);

	void stubRepositoryCreateUknownError(T mockedResult);

	void stubRepositoryUpdateUknownError(T mockedResult);

	void stubRepositoryDeleteUknownError(T mockedResult);

	void stubRepositoryDeleteNotFoundError(T mockedResult);
}
