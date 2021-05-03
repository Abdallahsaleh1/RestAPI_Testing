package TestClasses;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;

import org.json.simple.JSONObject;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.RestClientHandler;

public class TestDeleteRestAPI {
	
	public static String tokenSender() throws Exception {
    	HttpURLConnection connection = RestClientHandler.connectServer("https://restful-booker.herokuapp.com/auth", HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.authJSONFile);
		// 3. Post Request
		RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. Reading Response
		String response = RestClientHandler.readResponse(connection);
		JSONObject responseJsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		String auth = "token =" + responseJsonObject.get("token").toString();
		
		return auth;
    }
	
	//method that accept id and return the connection from get method
			public HttpURLConnection getnewbooking() throws Exception{
				
				String url = URLs.BOOKING+"13";
				HttpURLConnection connectionget = RestClientHandler.connectServer(url, HTTPMethod.GET,
						HTTPRequestsContentTypes.JSON);
				
				connectionget.addRequestProperty("Content-Type", "application/json");
				connectionget.addRequestProperty("Content-Length", "<calculated when request is sent>");
				connectionget.addRequestProperty("Host", "<calculated when request is sent>");
				connectionget.addRequestProperty("User-Agent", "PostmanRuntime/7.26.8");
				connectionget.addRequestProperty("Accept", "*/*");
				connectionget.addRequestProperty("Cookie", "token=cc5c32e4009c762");

			return connectionget;
			}

	@Test
	public void TestDeleteExistId() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		String url = URLs.BOOKING+"13";
				
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.DELETE,
		HTTPRequestsContentTypes.JSON);
				
		connection.setRequestProperty("Cookie", tokenSender());

		// 2. Post Request
		RestClientHandler.sendDelete(connection, "", HTTPRequestsContentTypes.JSON);
				
		// 3. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("connection succefully", connection.getResponseCode() == 201);
		
		// 4. read response
		String response = RestClientHandler.readResponse(connection);
		
		// 5.testing the response
		assertEquals("",response,"Created" );
		
		// 6. make sure its deleted successfully and not found 
		HttpURLConnection connectionget = getnewbooking();
		System.out.println("connection.getResponseCode() : " + connectionget.getResponseCode());
		assertTrue("unable to connect to webservice", connectionget.getResponseCode() == 404);
		
	}
	
	@Test(expected = IOException.class)
	public void TestDeleteNotExistID() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		String url = URLs.BOOKING+"77";
					
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.DELETE,
		HTTPRequestsContentTypes.JSON);
		connection.setRequestProperty("Cookie", tokenSender());

		// 2. Post Request
		RestClientHandler.sendDelete(connection, "", HTTPRequestsContentTypes.JSON);
		
		// 3. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("connection succefully", connection.getResponseCode() == 405);
			
		// 4. read response
		String response = RestClientHandler.readResponse(connection);
			
		// 5.testing the response
		assertEquals("",response,"Method Not Allowed" );
	}

}
