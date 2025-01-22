package CTHH.chanstagram;

import CTHH.chanstagram.Comment.CreateCommentRequest;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.DTO.UserRequest;
import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.post.DTO.CreatePost;
import CTHH.chanstagram.post.DTO.PostResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void signUpTest() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void loginTest() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("younId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);


    }

    @Test
    void updateUserTest() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse loginResponse = RestAssured
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new UserRequest("윤태우", "youn", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .put("/users")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    void deleteUserTest() {
        //회원가입
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);
        //로그인
        LoginResponse loginResponse = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("younId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
        //게시글 생성
        PostResponse postResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreatePost(List.of(), ""))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);
        //댓글생성
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.", postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .when()
                .delete("/users")
                .then().log().all()
                .statusCode(200);
    }
}

