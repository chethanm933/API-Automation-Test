package com.spotify;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class SpotifyApi {
	
	public String token;
	public String userId;
	String playlistId = "2jsGYfnlSk5xt21hfAlabX";
	
	
@BeforeTest
	public void getToken() {
		token = "Bearer BQCT0ryjJakPcjPM7yFfVQbkjn9V3yKiOsxwMLXqDviQXKjMsY-eXLa5TGboASKIfbPmXKeXfQTyQxtr2PLWQM69RpraOUf4WJrLKGj4NxPo5n0jp2PhO28ixIm0w_IZcbLJLDJTGhBuTmdnb5_zeqnTg43jMU9Pc5FQ7F8wETlGmS8omU08mqQkQ1SXgbbcyUmrQGe-HQpaL421XHJX0ujqTn5hd-p502eVNXv5dNEvB5Vbl9qlq7Cs8ok-MK7IU2uw8s-fPYklV0M0Xh0tZLx5x0ZHeR8PhsqIOpNVMuknEGWulLTwDA3WG-G6Ar2eMj8g3no59Hq6";	
	}


	@Test (priority = 0)
	public void Get_Current_User_Profile() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).get("https://api.spotify.com/v1/me");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(statusCode, 200);
		userId = response.path("id");
		System.out.println("User Id" +userId);
	}

	@Test (priority = 1)
	public void Get_User_Profile() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token).get("https://api.spotify.com/v1/users/31s3qmi2lxhpjruvzfvxye7cloey");
		response.prettyPrint();
		int statusCode = response.getStatusCode();
		System.out.println("Status Code" + statusCode);
		Assert.assertEquals(statusCode, 200);
	}
	
	@Test (priority = 5)
	public void addItemsToPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization",token)
							.queryParam("uris","spotify:track:2pAiuBdD0w12lOT8c3YbjY")
							
							.when()
							.post("	https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	
	@Test (priority = 2)
	public void createPlayList() {
		Response response =  RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\n" +
						            "  \"name\": \"Automated New Playlist\",\n" +
						            "  \"description\": \"New Playlist Description\",\n" +
						            "  \"public\": \"false\" \n}").when()
							.post("https://api.spotify.com/v1/users/"+userId+"/playlists");
		playlistId = response.path("Id");
		response.prettyPrint();
		response.then().assertThat().statusCode(201);
	}
	
	@Test (priority = 4)
	public void searchForAnItem() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParam("q", "shivatandavastotram")
							.queryParam("type", "track")
							.get("https://api.spotify.com/v1/search");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
 
	@Test (priority = 3)
	public void getPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.get("https://api.spotify.com/v1/users/"+userId+"/playlists");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 7)
	public void getOnePlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.get("https://api.spotify.com/v1/playlists/"+playlistId);
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 8)
	public void removePlaylistsItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{ \"tracks\": [{ \"uri\": \"spotify:track:2lZPFiW9dmTCyYnDhPngy1\" },{ \"uri\": \"spotify:track:2pAiuBdD0w12lOT8c3YbjY\" }] }")
							.delete("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 6)
	public void getPlaylistsItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.get("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test (priority = 7)
	public void updatePlaylistsItems() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.body("{\n" +
						            "  \"range_start\": 0,\n" +
						            "  \"insert_before\": 3,\n" +
						            "  \"range_length\": 1 \n}")
							.put("https://api.spotify.com/v1/playlists/"+playlistId+"/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test 
	public void getCurrentUsersPlaylists() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON).header("Authorization",token)
							.get("	https://api.spotify.com/v1/me/playlists");
	
	response.prettyPrint();	
	response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void changePlaylistDetails() {
		Response response =  RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.body("{\n" +
			            "  \"name\": \"Automated Playlist updated\",\n" +
			            "  \"description\": \"This playlist is updated with new name\",\n" +
			            "  \"public\": true \n}").when()
				.put("	https://api.spotify.com/v1/playlists/"+playlistId);
		
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
		
		
	}
	
	@Test
	public void getTracksAudioAnalysis() {
		Response response = RestAssured.given().contentType(ContentType.JSON).accept(ContentType.JSON)
				.header("Authorization", token)
				.get("https://api.spotify.com/v1/audio-analysis/6H7fLdt0AeWpuxUKXuXWrx");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void getTracksAudioFeatures() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.get("https://api.spotify.com/v1/audio-features/2pAiuBdD0w12lOT8c3YbjY");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void getTracksAudioFeaturesUsingId() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							
							.get("https://api.spotify.com/v1/audio-analysis/6H7fLdt0AeWpuxUKXuXWrx");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void getSeveralTracks() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParam("ids", "6H7fLdt0AeWpuxUKXuXWrx")
							.get("https://api.spotify.com/v1/tracks");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void getTrack() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
							.accept(ContentType.JSON)
							.header("Authorization", token)
							.queryParam("ids", "6H7fLdt0AeWpuxUKXuXWrx")
							.get("https://api.spotify.com/v1/tracks/6H7fLdt0AeWpuxUKXuXWrx");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
	@Test
	public void getPlaylistCoverImage() {
		Response response = RestAssured.given().contentType(ContentType.JSON)
				.accept(ContentType.JSON)
				.header("Authorization", token)
				.get("	https://api.spotify.com/v1/playlists/"+playlistId+"/images");
		response.prettyPrint();
		response.then().assertThat().statusCode(200);
	}
	
}
