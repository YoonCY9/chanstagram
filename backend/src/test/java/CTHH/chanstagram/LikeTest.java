package CTHH.chanstagram;

import CTHH.chanstagram.Comment.CommentResponse;
import CTHH.chanstagram.Comment.CreateCommentRequest;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LikeTest {

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
    void post생성시좋아요수체크() {

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
        assertThat(postResponse.likeCount()).isEqualTo(0);
    }

    @Test
    void 게시글좋아요하기() {
        //사람1 회원가입,로그인,사람1이 게시글생성
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



        //사람2 회원가입, 로그인
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("이호연", "hoho", "yeonId", "11111", Gender.Woman,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!!", "ImageUrl", "01074877706"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse token1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("yeonId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        //좋아요하기
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1.token())
                .pathParam("postId", postResponse.postId())
                .when()
                .post("/posts/{postId}")
                .then()
                .statusCode(200);

        PostDetailedResponse postId = RestAssured
                .given().log().all()
                .pathParam("postId", postResponse.postId())
                .when().log().all()
                .get("/posts/detailed/{postId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostDetailedResponse.class);

        assertThat(postId.likeCount()).isEqualTo(1);
    }
    @Test
    void 게시글좋취() {
        //사람1 회원가입,로그인,사람1이 게시글생성
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



        //사람2 회원가입, 로그인
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("이호연", "hoho", "yeonId", "11111", Gender.Woman,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!!", "ImageUrl", "01074877706"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse token1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("yeonId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        //좋아요하기
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1.token())
                .pathParam("postId", postResponse.postId())
                .when()
                .post("/posts/{postId}")
                .then()
                .statusCode(200);

        //좋아요취소
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1.token())
                .pathParam("postId", postResponse.postId())
                .when()
                .post("/posts/{postId}")
                .then()
                .statusCode(200);

        //게시글 조회
        PostDetailedResponse postId = RestAssured
                .given().log().all()
                .pathParam("postId", postResponse.postId())
                .when().log().all()
                .get("/posts/detailed/{postId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(PostDetailedResponse.class);

        assertThat(postId.likeCount()).isEqualTo(0);
    }
    @Test
    void 댓글좋아요하기() {
        //사람1 회원가입,로그인,사람1이 게시글생성,댓글생성
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
        //댓글생성
        CommentResponse commentResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.", postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);


        //사람2 회원가입, 로그인
        RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new UserDetailRequest("이호연", "hoho", "yeonId", "11111", Gender.Woman,
                        LocalDate.parse("2001-08-08"), "잘부탁드립니다!!", "ImageUrl", "01074877706"))
                .when()
                .post("/users")
                .then().log().all()
                .statusCode(200);

        LoginResponse token1 = RestAssured
                .given().log().all()
                .contentType(ContentType.JSON)
                .body(new LoginRequest("yeonId", "11111"))
                .when()
                .post("/login")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);

        //좋아요하기
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1.token())
                .pathParam("commentId", 1)
                .when()
                .post("/comments/{commentId}")
                .then()
                .statusCode(200);

        //좋아요하기
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + token1.token())
                .pathParam("commentId", 1)
                .when()
                .post("/comments/{commentId}")
                .then()
                .statusCode(200);


    }
}

