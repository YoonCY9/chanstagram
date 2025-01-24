package CTHH.chanstagram;

import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.hashTag.HashTagRepository;
import CTHH.chanstagram.post.DTO.CreatePost;
import CTHH.chanstagram.post.DTO.PostResponse;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HashTagTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    HashTagRepository hashTagRepository;

    @Test
    void hashTagTest() {
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤태우", "youn", "younId", "11111", Gender.Man,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse token = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("younId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        List<String> imageUrl = List.of("https://example.com/image1.jpg111",
                "https://example.com/image2.jpg");

        PostResponse postResponse =
                RestAssured
                        .given().log().all()
                        .contentType(ContentType.JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                        .body(new CreatePost(imageUrl, "테스트입니다"))
                        .when()
                        .post("/posts")
                        .then().log().all()
                        .statusCode(200)
                        .extract()
                        .as(PostResponse.class);


    }
}