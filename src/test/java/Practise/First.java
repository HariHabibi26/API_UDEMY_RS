package Practise;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import files.PayLoad;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

public class First
{

	public static void Demo()
	{

		RestAssured.baseURI = "https://rahulshettyacademy.com";
		RestAssured.baseURI = "80";
		RestAssured.requestSpecification = with().contentType(ContentType.JSON);

	}

	@Test
	public void name()
	{
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body(PayLoad.AddPlace()).when().post("/maps/api/place/add/json").then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP"))
			.header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");

		System.out.println(placeId);

		//		Update
		String newAddress = "Summer Walk, Africa";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json").body("{\r\n" + "    \"place_id\":\"" + placeId + "\",\r\n" + "    \"address\":\"" + newAddress + "\",\r\n" + "    \"key\":\"qaclick123\"\r\n" + "}").when().put("maps/api/place/update/json").then()
			.log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated")).header("server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();

		//		GET
		String getResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId).when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract().response().asString();

		JsonPath js1 = new JsonPath(getResponse);
		String updatedAddress = js1.getString("address");

		System.out.println(updatedAddress);

	}
}
