package org.jumahuaca.extensions;

import java.util.List;

import org.junit.jupiter.api.extension.Extension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

public class SpringControllerCrudExtension<T> implements Extension{
	
	public MvcResult requestAllShouldWork(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url) throws Exception{
		List<T> mockedResult = helper.mockSelectAllResult();
		helper.stubRepositoryFindAllOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestAllShouldNotReturn(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url) throws Exception{
		helper.stubRepositoryFindAllNotFound();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestAllShouldFail(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url) throws Exception{
		helper.stubRepositoryFindAllError();
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestOneShouldWork(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, Object... uriVars) throws Exception{
		T mockedResult = helper.mockOne();
		helper.stubRepositoryFindByIdOk(mockedResult);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url,uriVars)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult requestOneShouldNotFound(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, Object... uriVars) throws Exception{
		T mockedResult = helper.mockOne();
		helper.stubRepositoryFindByIdNotFound(mockedResult);

		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(url,uriVars)
				.accept(MediaType.APPLICATION_JSON_UTF8)).andReturn();
		return result;
	}
	
	public MvcResult postShouldWork(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.stubRepositoryUpdateOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult postShouldFail(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.stubRepositoryUpdateError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult putShouldWork(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.stubRepositoryUpdateOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult putShouldFail(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.stubRepositoryUpdateError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult deleteShouldWork(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.stubRepositoryDeleteOk(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	public MvcResult deleteShouldFail(HttpWebServiceDoubleHelper<T> helper, MockMvc mockMvc, String url, String jsonEntity) throws Exception {
		T mockedResult = helper.mockOne();
		helper.stubRepositoryDeleteError(mockedResult);
		MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(url)
				.contentType(MediaType.APPLICATION_JSON_UTF8).accept(MediaType.APPLICATION_JSON_UTF8)
				.content(jsonEntity)).andReturn();
		return result;
	}
	
	
	
	

}
