package TestClasses;

import static org.junit.Assert.*;

import org.junit.Test;
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
public class TestPUTRestAPI {
	
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
			
			String url = URLs.BOOKING+"6";
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
		
	//test updating information into an exist user 	
	@Test
	public void testUpdateUser() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		String url = URLs.BOOKING+"6";
		
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.UpdateBookingJSONFile);
		connection.setRequestProperty("Cookie", tokenSender());

		// 3. Post Request
		RestClientHandler.sendPut(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		
		// 4. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		
		// 5. Reading Response
		System.out.println(connection.getResponseCode());
		String response = RestClientHandler.readResponse(connection);
		
		// 6. convert String to JSON
		JSONObject responsejsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		responsejsonObject.remove("updatedAt");
		// 7. Test the response data changed into the data in the request
		JSONObject request = (JSONObject) JSONUtils.convertStringToJSON(resquestJSONObject);

		System.out.println(responsejsonObject);
		System.out.println(request);

		assertEquals("",request , responsejsonObject );
		
		// 8. validation that the request has been edited successfully and added
		HttpURLConnection connectionget = getnewbooking();
		String getresponse = RestClientHandler.readResponse(connectionget);
		JSONObject getjsonObject = (JSONObject) JSONUtils.convertStringToJSON(getresponse);
		System.out.println(getjsonObject);

		assertEquals("", getjsonObject, responsejsonObject );
		
	}
	
	//test that it can't update with missing values
	@Test
	public void testUpdateWithMissings() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		String url = URLs.BOOKING+"2";
		
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.missingJSONFile);
		connection.setRequestProperty("Cookie", tokenSender());

		// 3. Post Request
		RestClientHandler.sendPut(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		
		// 4. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("connection succefully", connection.getResponseCode() == 400);
		
	}
	
	//test that it can't update while sending false data types
	@Test
	public void testUpdateWithWrongdataType() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		String url = URLs.BOOKING+"2";
		
		HttpURLConnection connection = RestClientHandler.connectServer(url, HTTPMethod.PUT,
				HTTPRequestsContentTypes.JSON);
		
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.WrongDataTpesJSONFile);
		connection.setRequestProperty("Cookie", tokenSender());

		// 3. Post Request
		RestClientHandler.sendPut(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		
		// 4. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("connection succefully", connection.getResponseCode() == 500);
		
	}
	
}
