package restAssured;

import static io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import java.util.HashMap;
import java.util.Map;

public class demo{
    public static void main(String[] args) {
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("name", "John");
        requestBody.put("job", "Developer");

        given()
            .baseUri("https://reqres.in/api")
            .contentType(ContentType.JSON)
            //.body(requestBody)
            
           // .header("Content-Type", "application/json")
            .body("{\"title\": \"foo\", \"body\": \"bar\", \"userId\": 1}")

            
            .log().all()
        .when()
            .post("/users")
        .then()
            .statusCode(201)
            .log().body();
    }
}

