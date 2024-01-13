package com.devsuperior.dsmovie.controllers;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.simple.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.devsuperior.dsmovie.tests.TokenUtil;

import io.restassured.http.ContentType;

public class ScoreControllerRA {
	private String adminUsername, adminPassword;
	private String adminToken;
	
	private Map<String, Object> putScoreInstance;
		
	@BeforeEach
	public void setup() throws JSONException {
		baseURI = "http://localhost:8080";
		
		adminUsername = "maria@gmail.com";
		adminPassword = "123456";
		
		adminToken = TokenUtil.obtainAccessToken(adminUsername, adminPassword);
			
		putScoreInstance = new HashMap<>();
		putScoreInstance.put("movieId", "1");
		putScoreInstance.put("score", 4);
	}
	
	@Test
	public void saveScoreShouldReturnNotFoundWhenMovieIdDoesNotExist() throws Exception {
		putScoreInstance.put("movieId",100);
		JSONObject score = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(score)
		.when()
			.put("/scores")
		.then()
			.statusCode(404);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenMissingMovieId() throws Exception {
		putScoreInstance.put("movieId",null);
		JSONObject score = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(score)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
	}
	
	@Test
	public void saveScoreShouldReturnUnprocessableEntityWhenScoreIsLessThanZero() throws Exception {
		putScoreInstance.put("score",-1);
		JSONObject score = new JSONObject(putScoreInstance);
		
		given()
			.header("Content-type", "application/json")
			.header("Authorization", "Bearer " + adminToken)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(score)
		.when()
			.put("/scores")
		.then()
			.statusCode(422);
	}
}
