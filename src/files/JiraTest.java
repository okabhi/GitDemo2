package files;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;

import static io.restassured.RestAssured.*;

import java.io.File;

public class JiraTest {
public static void main(String[] args) {
	RestAssured.baseURI="http://localhost:8100";
	//login scenrio
	SessionFilter session=new SessionFilter();//this session object will hold the whole response
	String response=given().header("content-Type","application/json").body("{\r\n"
			+ "    \"username\": \"abhishek\",\r\n"
			+ "    \"password\": \"Abhi@1234\"\r\n"
			+ "}").log().all().filter(session).when().post("rest/auth/1/session").then().extract().response().asString();
	System.out.println("yayayay"+response);
	
	
	given().pathParam("id","10100").log().all().header("Content-Type","application/json").body("{\r\n"
			+ "    \"body\": \"This is my first comment \",\r\n"
			+ "    \"visibility\": {\r\n"
			+ "        \"type\": \"role\",\r\n"
			+ "        \"value\": \"Administrators\"\r\n"
			+ "    }\r\n"
			+ "}").filter(session).when().post("/rest/api/2/issue/{id}/comment") //here id is taken from path param
                  .then().log().all().assertThat().statusCode(201);

	//add attachment
	/*given().header("X-Atlasssian-Token","no-check").filter(session).pathParam("id", "10100")
	.header("Content-Type","multipart/form-data")
	.multiPart("file", new File("jira.txt")).when().
	post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(200);*/
	
	
	//get issue
	String issuedetails=given().filter(session).pathParam("id", "10100")
			.queryParam("fields", "comment").log().all().when().get("/rest/api/2/issue/{id}").then()
	.log().all().extract().response().asString();
	System.out.println(issuedetails);
}
}
