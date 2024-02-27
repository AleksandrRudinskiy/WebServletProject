import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class GetRequestTest {
    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void getRequestCheckStatusCode() {
        RestAssured.given()
                .baseUri("http://localhost:8080/WebServletProject/")//---> Cтартовая URL
                .relaxedHTTPSValidation()
                .get("/users/")//---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_OK);//---> Проверка статус код
    }

}
