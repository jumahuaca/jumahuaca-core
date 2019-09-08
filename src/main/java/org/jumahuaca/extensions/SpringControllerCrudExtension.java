package org.jumahuaca.extensions;

import java.util.List;

import org.junit.jupiter.api.extension.Extension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SpringControllerCrudExtension<T> implements Extension{
	
	public MvcResult requestAllShouldWork(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url) throws Exception{
		List<T> mockedResult = helper.mockSelectAllResult();
		helper.mockRepositoryFindAllOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestAllShouldNotReturn(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url) throws Exception{
		helper.mockRepositoryFindAllNotFound();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestAllShouldFail(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url) throws Exception{
		helper.mockRepositoryFindAllError();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestOneShouldWork(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, Object... uriVars) throws Exception{
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdOk(mockedResult);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url,uriVars)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestOneShouldNotFound(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, Object... uriVars) throws Exception{
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdNotFound(mockedResult);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url,uriVars)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult postShouldWork(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult postShouldFail(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult putShouldWork(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult putShouldFail(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult deleteShouldWork(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult deleteShouldFail(HttpWebServiceTestDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	
	
	

}
