package CTHH.chanstagram.post;

import CTHH.chanstagram.Comment.CreateCommentRequest;
import CTHH.chanstagram.DatabaseCleanup;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.post.DTO.CreatePost;
import CTHH.chanstagram.post.DTO.PostDetailedResponse;
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
public class postTest {

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
    void post생성() {

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

        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new CreatePost(imageUrl, "테스트입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);
    }

    @Test
    void post삭제() {
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

        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new CreatePost(imageUrl, "테스트입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.",postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .pathParam("postId",postResponse.postId())
                .when()
                .delete("/posts/{postId}")
                .then()
                .statusCode(200);

    }

    @Test
    void post전체조회() {

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

        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new CreatePost(imageUrl, "윤태우입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("윤찬영", "chan", "yoonId", "1234", Gender.Man,
                        LocalDate.parse("1998-04-25"), "안녕하세요", "ImageUrl", "01043998684"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse token2 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("yoonId", "1234"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        List<String> imageUrl2 = List.of("https://example.com/image1.jpg111",
                "https://example.com/image2.jpg");

        PostResponse postResponse2 = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token2.token())
                .body(new CreatePost(imageUrl2, "윤찬영입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);


        List<PostResponse> posts = RestAssured
                .given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .jsonPath()
                .getList(".", PostResponse.class);

        System.out.println(posts.get(0).content());
        System.out.println(posts.get(1).content());
    }

    @Test
    void 게시글상세조회() {

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

        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .body(new CreatePost(imageUrl, "윤태우입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);

        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.",postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200);

        RestAssured
                .given().log().all()
                .pathParam("postId", postResponse.postId())
                .when().log().all()
                .get("/posts/detailed/{postId}")
                .then().log().all()
                .statusCode(200);


    }
}
