package com.apps.utils;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiValidations {
	
	public static void main(String[] args)  {
		String res=get("https://jsonplaceholder.typicode.com/users");
		parseJson(res,"$.length()");
		parseJson(res,"$.[*].name");
		parseJson(res,"$.[?(@['name']=='Ervin Howell')].email");
		
		
		post("https://jsonplaceholder.typicode.com/posts","{id: 1,title: 'Harry123',body: 'Test',userId: 1234}");
		
	}
	
	
	public static String get(String baseUrl) {
		String response="";
		Response res=RestAssured.given().get(baseUrl);
		response=res.getBody().asString();
		System.out.println(response);
		return response;
	}
	
	public static int post(String baseUrl,String jsonBody) {
		String response="";
		Response res=RestAssured.given().body(jsonBody).post(baseUrl);
		response=res.getHeaders().toString();
		res.then().assertThat().statusCode(201);
		System.out.println("Status Code: "+res.statusCode()+" | headers\n"+response);
		return res.statusCode();
	}
	
	public static String parseJson(String json,String key){
		DocumentContext context = JsonPath.parse(json);
		String res=context.read(key).toString();
		System.out.println(key+":\t"+res);
		return res;
	}
}
