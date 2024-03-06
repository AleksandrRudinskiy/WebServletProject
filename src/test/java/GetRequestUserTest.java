import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class GetRequestUserTest {
    private final String BASE_URL = "http://localhost:8080/WebServletProject";

    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void shouldReturn200WhenGetAllUsers() {
        RestAssured.given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void getRequestCheckStatusCode() {
        RestAssured.given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .get("/users?tom")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }
   @Test
   @DisplayName("Тестирование запроса Get по id c проверкой status code = 400")
   public void shouldReturn400WhenUserNotFound() {
           RestAssured.given()
                   .baseUri(BASE_URL)
                   .relaxedHTTPSValidation()
                   .get("/users?unknown")
                   .then()
                   .statusCode(HttpStatus.SC_BAD_REQUEST);
   }

    @Test
    @DisplayName("Тестирование запроса Get по id c проверкой status code = 200")
    public void shouldReturn200WhenUserFound() {
        RestAssured.given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .get("/users?tom")
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

    @Test
    @DisplayName("Тестирование запроса Get по id c проверкой status code = 400")
    public void shouldReturn400WhenDeleteUserNotFound() {
        RestAssured.given()
                .baseUri(BASE_URL)
                .relaxedHTTPSValidation()
                .delete("/users?unknown")
                .then()
                .statusCode(HttpStatus.SC_BAD_REQUEST);
    }

    @Test
    @DisplayName("Тестирование запроса Get followers userId = \"tom\" c проверкой status code = 200")
    public void shouldReturn200WhenGetUserFollowers() {
        RestAssured.given()
                .baseUri(BASE_URL + "/followers/posts/user?tom")
                .relaxedHTTPSValidation()
                .then()
                .statusCode(HttpStatus.SC_OK);
    }

}
