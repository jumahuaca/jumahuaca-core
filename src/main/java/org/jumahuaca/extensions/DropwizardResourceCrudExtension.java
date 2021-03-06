package org.jumahuaca.extensions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.junit.jupiter.api.extension.Extension;

import io.dropwizard.testing.junit5.ResourceExtension;

public class DropwizardResourceCrudExtension <T> implements Extension{
	
	public Response requestAllShouldWork(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources){
		List<T> mockedResult = helper.mockSelectAllResult();
		helper.mockRepositoryFindAllOk(mockedResult);		
		Response response = resources.target(url).request().get(Response.class);
		return response;
	}
	
	public Response requestAllShouldNotWorkBecauseOfServerError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		helper.mockRepositoryFindAllError();		
		Response response = resources.target(url).request().get(Response.class);
		return response;
	}
	
	public Response requestAllShouldNotWorkBecauseOfUnknownError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) throws SQLException {
		helper.mockRepositoryFindAllError();		
		Response response = resources.target(url).request().get(Response.class);
		return response;		
	}
	
	public Response requestOneShouldWork(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdOk(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().get(Response.class);
		return response;
	}
	
	public Response requestOneShouldNotWorkBecauseOfServerError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdError(mockedResult);
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().get(Response.class);
		return response;
	}
	
	public Response requestOneShouldNotWorkBecauseOfNotFoundError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdNotFound(mockedResult);
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().get(Response.class);
		return response;
	}
	
	public Response requestOneShouldNotWorkBecauseOfUknownError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		return requestOneShouldNotWorkBecauseOfServerError(helper, url, resources,templateResolver);
	}
	
	public Response postShouldWork(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryCreateOk(mockedResult);
		Response response = resources.target(url).request().post(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response postShouldNotWorkBecauseOfServerError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryCreateServerError(mockedResult);
		Response response = resources.target(url).request().post(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response postShouldNotWorkBecauseOfUknownError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryCreateUknownError(mockedResult);
		Response response = resources.target(url).request().post(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response putShouldWork(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateOk(mockedResult);
		Response response = resources.target(url).request().put(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response putShouldNotWorkBecauseOfServerError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateError(mockedResult);
		Response response = resources.target(url).request().put(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response putShouldNotWorkBecauseOfUknownError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateUknownError(mockedResult);
		Response response = resources.target(url).request().put(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response deleteShouldWork(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteOk(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	
	public Response deleteShouldNotWorkBecauseOfServerError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteError(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	
	public Response deleteShouldNotWorkBecauseOfUknownError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteUknownError(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	
	public Response deleteShouldNotWorkBecauseOfNotFoundError(HttpWebServiceTestDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteNotFoundError(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	


	
	
	
	
	

}
