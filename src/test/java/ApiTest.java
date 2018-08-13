import com.google.common.io.Resources;
import io.restassured.RestAssured;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;


import static io.restassured.RestAssured.given;

public class ApiTest {
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void createPetandQuery() throws IOException {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        URL file = Resources.getResource("pet.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject( myJson );
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long id = timestamp.getTime();

        json.put("id", id);
        json.getJSONObject("category").put("id", id);

        given()
            .contentType("application/json")
            .body(json.toString())
        .when()
            .post("/pet")
        .then()
            .statusCode(200);

        String responseBody =
        given()
            .contentType("application/json")
        .when()
            .get("/pet/{newId}", id)
        .then()
            .statusCode(200)
        .extract()
            .body().asString();

        System.out.println(responseBody);
    }
}
