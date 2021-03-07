import com.google.common.io.Resources;
import extensions.RetryAnalyzer;
import io.restassured.RestAssured;
import io.restassured.internal.http.HttpResponseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.Test;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;


import static io.restassured.RestAssured.given;

public class ApiTests {
    private void createPet(Long id) throws IOException{
        URL file = Resources.getResource("pet.json");
        String myJson = Resources.toString(file, Charset.defaultCharset());
        JSONObject json = new JSONObject( myJson );

        json.put("id", id);
        json.getJSONObject("category").put("id", id);

        given()
            .contentType("application/json; charset=UTF-8")
            .body(json.toString())
        .when()
            .post("/pet")
        .then()
            .statusCode(200);
    }
    private void createOrder(Long id) throws IOException{
        URL orderFile = Resources.getResource("order.json");
        String orderJson = Resources.toString(orderFile, Charset.defaultCharset());
        JSONObject orderObject = new JSONObject( orderJson );
        long orderId = id;
        orderObject.put("id", orderId);

        orderObject.put("petId", id);
        orderObject.put("quantity", 1);
        given() //create order
            .contentType("application/json; charset=UTF-8")
            .body(orderObject.toString())
        .when()
            .post("store/order")
        .then()
            .statusCode(200);
    }
    private void createUser(Long id) throws IOException{
        URL userFile = Resources.getResource("user.json");
        String userJson = Resources.toString(userFile, Charset.defaultCharset());
        JSONObject userObject = new JSONObject( userJson );
        long userId = id;

        String username = "testuser" + String.valueOf(userId);
        System.out.println(username);
        userObject.put("id", userId);
        userObject.put("username", username);

        given() //create user
            .contentType("application/json; charset=UTF-8")
            .body(userObject.toString())
        .when()
            .post("/user")
        .then()
            .statusCode(200);
    }
    private void loginUser(String username, String password) throws IOException{
        given() //login user
            .contentType("application/json; charset=UTF-8")
            .queryParam("username", username)
            .queryParam("password", password)
        .when()
            .get("/user/login")
        .then()
            .statusCode(200);
    }
    private void updateUser(long id) throws  IOException{
        URL userFile = Resources.getResource("user.json");
        String userJson = Resources.toString(userFile, Charset.defaultCharset());
        JSONObject userObject = new JSONObject( userJson );
        long userId = id;
        String username = "testuser" + String.valueOf(userId);

        userObject.put("id", userId);
        userObject.put("username", username);
        userObject.put("firstName", "Ahmet");
        userObject.put("lastName", "Yilmaz");
        given() //update user
            .contentType("application/json; charset=UTF-8")
            .body(userObject.toString())
        .when()
            .put("/user/{username}", username)
        .then()
            .statusCode(200);
    }
    private void deleteUser(String username) throws  IOException{
        given() //delete user
            .contentType("application/json; charset=UTF-8")
        .when()
            .delete("/user/{username}", username)
        .then()
            .statusCode(200);
    }
    private void getUserThatDoesNotExist(String username) throws  IOException{
        given()
            .contentType("application/json; charset=UTF-8")
        .when()
            .get("/user/{username}", username)
        .then()
            .statusCode(404);
    }
    private void validateUserDeletion(String username) throws  IOException{
        try{
            getUserThatDoesNotExist(username);
        }
        catch (HttpResponseException ex)
        {
            assert ex.getStatusCode() == 404;
        }
    }

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
            .contentType("application/json; charset=UTF-8")
        .when()
            .get("/pet/{newId}", id)
        .then()
            .statusCode(200)
        .extract()
            .body().asString();

        System.out.println(responseBody);
    }
    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void orderJourney() throws IOException {
        RestAssured.baseURI = "https://petstore.swagger.io/v2";

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long id = timestamp.getTime();
        String username = "testuser" + id;
        System.out.println(id);

        createPet(id);
        createUser(id);
        loginUser(username , "Asdf1234");
        createOrder(id);
        updateUser(id);
        deleteUser(username);
        validateUserDeletion(username);
    }

    @Test(retryAnalyzer = RetryAnalyzer.class)
    public void tcmbRate() throws IOException {
        RestAssured.baseURI = "https://evds2.tcmb.gov.tr/service/evds/series=TP.DK.USD.A-TP.DK.USD.S&startDate=28-02-2021&endDate=06-03-2021&key=wTpQNIJhRp&type=json";

        String responseBody =
        given()
            .contentType("application/json; charset=UTF-8")
        .when()
            .get("")
        .then()
            .statusCode(200)
            .extract()
            .body().asString();

        JSONObject json = new JSONObject(responseBody);
        JSONArray items = new JSONArray(json.get("items").toString());

        for(int i=0; i<items.length(); i++)
        {

            if(items.getJSONObject(i).get("TP_DK_USD_S").toString().equals("null")){
                System.out.println("Tarih: " + items.getJSONObject(i).get("Tarih") + " Tatil Gunleri borsa kapalı");
            }
            else{
                float temp = Float.parseFloat(items.getJSONObject(i).getString("TP_DK_USD_S")) - Float.parseFloat(items.getJSONObject(i).getString("TP_DK_USD_A"));
                System.out.println("Tarih: " + items.getJSONObject(i).get("Tarih") + " Satis-Alis Farki: " + temp + " TRY");
            }
        }
    }
}
