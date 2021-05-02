package TestClasses;

import static org.junit.Assert.*;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONStreamAware;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import Links.FilesPaths;
import Links.URLs;
import Utils.JSONUtils;
import enums.HTTPMethod;
import enums.HTTPRequestsContentTypes;
import requestHandling.HandleRequestReponse;
import requestHandling.RestClientHandler;
import java.io.FileNotFoundException;

public class TestGetRestAPI {

	
	//test Get method return all the ids in the database
	@Test
	public void TestGETResponse() throws IOException {
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URLs.BOOKING, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		// 2. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		// 3. reading response using input stream
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		assertTrue("Data is empty", !response.equals(""));

	}
	//testing GET method that return info for one existing id
	@Test
	public void TestGETResponse_oneID() throws Exception {
		
	String URL=URLs.BOOKING + "8";
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer( URL, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		
		connection.addRequestProperty("Content-Type", "application/json");
        connection.addRequestProperty("Content-Length", "<calculated when request is sent>");
        connection.addRequestProperty("Host", "<calculated when request is sent>");
        connection.addRequestProperty("User-Agent", "PostmanRuntime/7.26.8");
        connection.addRequestProperty("Accept", "*/*");
        connection.addRequestProperty("Cookie", "token=cc5c32e4009c762");
        
		// 2. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 200);
		// 3. reading response using input stream
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		assertTrue("Data is empty", !response.equals(""));

	}
	
	//Testing GET method that return nout found when sending not existing id
	@Test(expected = FileNotFoundException.class)
	public void TestGETResponse_not_found_id() throws IOException {
		String URL= URLs.BOOKING + "667";
		// 1. connect to server and open connection (get HttpURLConnection object)
		HttpURLConnection connection = RestClientHandler.connectServer(URL, HTTPMethod.GET,
				HTTPRequestsContentTypes.JSON);
		// 2. validate if the connection is successfully openned
		System.out.println("connection.getResponseCode() : " + connection.getResponseCode());
		assertTrue("unable to connect to webservice", connection.getResponseCode() == 404);
		// 3. reading response using input stream
		String response = RestClientHandler.readResponse(connection);
		System.out.println(response);
		assertTrue("Data is empty", response.equals("Not Found"));

	}
	






}
