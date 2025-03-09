package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import utilits.BrowserUtilits;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_ACCEPTED;
import static org.apache.http.HttpStatus.SC_OK;

public class UserApi {
    BrowserUtilits browserUtilits = new BrowserUtilits();

    public final String URL = browserUtilits.getURL("HOME_PAGE");

    private static final String CREATE_USER = "api/auth/register";
    private static final String LOGIN_USER = "api/auth/login";
    private static final String DELETE_USER = "api/auth/user";


    @Step("Создание пользователя")
    public Response createUser(User user) {
        return given().header("Content-type", "application/json").and().body(user).when().post(CREATE_USER);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(User user) {
        return given().header("Content-type", "application/json").and().body(user).when().post(LOGIN_USER);
    }

    @Step("Проверка успешной авторизации зарегистрированного пользователя")
    public void checkedSuccessLoginResponse(Response response) {
        response.then().statusCode(SC_OK);
    }

    @Step("Получение accessToken")
    public String getAccessToken(Response response) {
        return response.jsonPath().getString("accessToken");
    }

    @Step("Удаление пользователя")
    public void deleteUser(String accessToken) {
        given().header("Authorization", accessToken).header("Content-type", "application/json").delete(DELETE_USER).then().statusCode(SC_ACCEPTED);
    }
}
