package files;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
public class DynamicJson {

	@Test(dataProvider="BooksData")
	public void addBook(String isbn,String aisle)
	{
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response=given().header("content-type","application/json")
		.body(payload.Addbook(isbn,aisle))
		.when()
		.post("/Library/Addbook.php")
		.then().assertThat().statusCode(200)
		.extract().response().asString();
		JsonPath js=ReUsableMethods.rawToJson(response);
		String id=js.getString("ID");
		System.out.println(id);
	}
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		return new Object[][] {{"assd","1234"},{"afra","11223"}};
	}
}
