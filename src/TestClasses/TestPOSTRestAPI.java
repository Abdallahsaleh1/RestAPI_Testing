package TestClasses;

import static org.junit.Assert.*;

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

public class TestPOSTRestAPI {
	
	//method that accept id and return the connection from get method
	public HttpURLConnection getnewbooking(String id) throws Exception{
		
		String url = URLs.BOOKING+id;
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
	
	
	//test send request and create new booking
	@Test
	public void testCreatBooking() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.BOOKING, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.NewBookingJSONFile);
		// 3. Post Request
		RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		
		// 4. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		// 5. Reading Response
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		// 6. convert String to JSON
		JSONObject jsonObject = (JSONObject) JSONUtils.convertStringToJSON(response);
		System.out.println(jsonObject);

		String bookingid = jsonObject.get("bookingid").toString();
		String booking = jsonObject.get("booking").toString();
		String firstname = ((JSONObject) jsonObject.get("booking")).get("firstname").toString();

		String lastname = ((JSONObject) jsonObject.get("booking")).get("lastname").toString();

		System.out.println("bookingid=" + bookingid);

		// 6. validation data that been sent == response
		String expected_firstname ="abdallah";
		String expected_lastname = "saleh";
		assertEquals("",expected_firstname , firstname );
		assertEquals("",expected_lastname , lastname );
		
		// 7. validation that the request has been post successfully and added
		HttpURLConnection connectionget = getnewbooking(bookingid);
		String getresponse = RestClientHandler.readResponse(connectionget);
		JSONObject getjsonObject = (JSONObject) JSONUtils.convertStringToJSON(getresponse);
		JSONObject requestJson = (JSONObject) JSONUtils.convertStringToJSON(resquestJSONObject);


		assertEquals("", getjsonObject, requestJson );
	}
	
	
	//Test that it can't post with missing info
	@Test 
	public void testCreatBookingWithMissings() throws Exception {
		// 1. Open Connection --- HttpURLConnection
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.BOOKING, HTTPMethod.POST,
				HTTPRequestsContentTypes.JSON);
		// 2. Prepare Json Object
		String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.NewMissingBookingJSONFile);
		// 3. Post Request
		RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
		// 4. validate that it receive an internal server error
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("connection is successfully openned", connection.getResponseCode() == 500);
		

	}
	//Test that it can't post with Wrong Data Types values
		@Test 
		public void testPostWithWrongDataTypes() throws Exception {
			// 1. Open Connection --- HttpURLConnection
			HttpURLConnection connection = RestClientHandler.connectServer(URLs.BOOKING, HTTPMethod.POST,
					HTTPRequestsContentTypes.JSON);
			// 2. Prepare Json Object
			String resquestJSONObject = JSONUtils.readJSONObjectFromFile(FilesPaths.WrongDataTpesJSONFile);
			// 3. Post Request
			RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
			// 4. validate that it receive an internal server error
			System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
			assertEquals("connection is successfully openned", connection.getResponseCode() , 500);
			
		}
		
		//test if it accept bulk insertion
		@Test 
		public void testPostWithArrayInsertion() throws Exception {
			// 1. Open Connection --- HttpURLConnection
			HttpURLConnection connection = RestClientHandler.connectServer(URLs.BOOKING, HTTPMethod.POST,
					HTTPRequestsContentTypes.JSON);
			// 2. Prepare Json Object
			String resquestJSONObject = JSONUtils.readJSONArrayFromFile(FilesPaths.ArrayJSONFile);
			// 3. Post Request
			RestClientHandler.sendPost(connection, resquestJSONObject, HTTPRequestsContentTypes.JSON);
			// 4. validate that it receive an internal server error
			System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
			assertEquals("internal server error", connection.getResponseCode() , 200);
			
		}

}
