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
	
	public Response requestAllShouldWork(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources){
		List<T> mockedResult = helper.mockSelectAllResult();
		helper.mockRepositoryFindAllOk(mockedResult);		
		Response response = resources.target(url).request().get(Response.class);
		return response;
	}
	
	public Response requestAllShouldNotWorkBecauseOfServerError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		helper.mockRepositoryFindAllError();		
		Response response = resources.target(url).request().get(Response.class);
		return response;
	}
	
	public Response requestAllShouldNotWorkBecauseOfUnknownError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) throws SQLException {
		helper.mockRepositoryFindAllError();		
		Response response = resources.target(url).request().get(Response.class);
		return response;		
	}
	
	public Response requestOneShouldWork(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdOk(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().get(Response.class);
		return response;
	}
	
	public Response requestOneShouldNotWorkBecauseOfServerError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdError(mockedResult);
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().get(Response.class);
		return response;
	}
	
	public Response requestOneShouldNotWorkBecauseOfNotFoundError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryFindByIdNotFound(mockedResult);
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().get(Response.class);
		return response;
	}
	
	public Response requestOneShouldNotWorkBecauseOfUknownError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		return requestOneShouldNotWorkBecauseOfServerError(helper, url, resources,templateResolver);
	}
	
	public Response postShouldWork(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryCreateOk(mockedResult);
		Response response = resources.target(url).request().post(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response postShouldNotWorkBecauseOfServerError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryCreateServerError(mockedResult);
		Response response = resources.target(url).request().post(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response postShouldNotWorkBecauseOfUknownError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryCreateUknownError(mockedResult);
		Response response = resources.target(url).request().post(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response putShouldWork(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateOk(mockedResult);
		Response response = resources.target(url).request().put(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response putShouldNotWorkBecauseOfServerError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateError(mockedResult);
		Response response = resources.target(url).request().put(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response putShouldNotWorkBecauseOfUknownError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources) {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryUpdateUknownError(mockedResult);
		Response response = resources.target(url).request().put(Entity.entity(mockedResult, MediaType.APPLICATION_JSON));
		return response;
	}
	
	public Response deleteShouldWork(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteOk(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	
	public Response deleteShouldNotWorkBecauseOfServerError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteError(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	
	public Response deleteShouldNotWorkBecauseOfUknownError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
		T mockedResult = helper.mockOne();
		helper.mockRepositoryDeleteUknownError(mockedResult);	
		WebTarget webTarget = resources.target(url);
		for (Entry<String,String> entry : templateResolver.entrySet()) {
			webTarget = webTarget.resolveTemplate(entry.getKey(), entry.getValue());
		}
		Response response = webTarget.request().delete();
		return response;
	}
	
	public Response deleteShouldNotWorkBecauseOfNotFoundError(HttpWebServiceDoubleHelper<T> helper, String url, ResourceExtension resources, Map<String,String> templateResolver) throws SQLException {
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
