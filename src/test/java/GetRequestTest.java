import io.restassured.RestAssured;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

public class GetRequestTest {
    @Test
    @DisplayName("Тестирование запроса Get c проверкой status code = 200")
    public void getRequestCheckStatusCode() {
        RestAssured.given()
                .baseUri("https://reqres.in/")//---> Cтартовая URL
                .relaxedHTTPSValidation()
                .get("/api/users/2")//---> Endpoint для выполнения запроса GET
                .then()
                .statusCode(HttpStatus.SC_OK);//---> Проверка статус код
    }

}
