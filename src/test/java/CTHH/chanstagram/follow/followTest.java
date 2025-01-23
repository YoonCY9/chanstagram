package CTHH.chanstagram.follow;


import CTHH.chanstagram.DatabaseCleanup;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.follow.followDTO.FollowResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class followTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

    @Test
    void follow요청() {

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);


        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("이호연", "lee", "lee", "1234", Gender.Woman,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01042468432"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);


        LoginResponse loginResponse1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("younId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse1.token())
                .pathParam("nickName", "lee")
                .when()
                .post("/follows/{nickName}")
                .then()
                .statusCode(200);
    }

    @Test
    void follower조회() {

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);


        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("이호연", "lee", "lee", "1234", Gender.Woman,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01042468432"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);


        LoginResponse loginResponse1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("younId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse1.token())
                .pathParam("nickName", "lee")
                .when()
                .post("/follows/{nickName}")
                .then()
                .statusCode(200);

        List<FollowResponse> list = RestAssured
                .given()
                .pathParam("nickName", "lee")
                .when()
                .get("/follows/followers/{nickName}")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", FollowResponse.class);

        assertThat(list.get(0).nickName()).isEqualTo("youn");
    }

    @Test
    void followee조회() {

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);


        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("이호연", "lee", "lee", "1234", Gender.Woman,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01042468432"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);


        LoginResponse loginResponse1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("younId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse1.token())
                .pathParam("nickName", "lee")
                .when()
                .post("/follows/{nickName}")
                .then()
                .statusCode(200);

        List<FollowResponse> list = RestAssured
                .given()
                .pathParam("nickName", "youn")
                .when()
                .get("/follows/followees/{nickName}")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", FollowResponse.class);

        assertThat(list.get(0).nickName()).isEqualTo("lee");

    }
}