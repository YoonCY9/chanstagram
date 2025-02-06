package CTHH.chanstagram;

import CTHH.chanstagram.Comment.CommentResponse;
import CTHH.chanstagram.Comment.CreateCommentRequest;
import CTHH.chanstagram.Comment.UpdateCommentRequest;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.post.DTO.CreatePost;
import CTHH.chanstagram.post.DTO.PostResponse;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentTest {

    @LocalServerPort
    int port;

    @Autowired
    DatabaseCleanup databaseCleanup;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseCleanup.execute();
    }

    @DisplayName("댓글 생성")
    @Test
    void createCommentTest() {
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
        List<String> imageUrl = List.of("https://example.com/image1.jpg111",
                "https://example.com/image2.jpg");
        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
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
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.", postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        assertThat(commentResponse.id()).isEqualTo(1);
        assertThat(commentResponse.content()).isEqualTo("댓글 생성 테스트 중입니다.");
    }
    @DisplayName("댓글 수정")
    @Test
    void updateCommentTest() {
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
        List<String> imageUrl = List.of("https://example.com/image1.jpg111",
                "https://example.com/image2.jpg");
        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .body(new CreatePost(imageUrl, "테스트입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);
        //댓글 생성
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.",postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200);
        //댓글 수정
        CommentResponse commentResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .pathParam("commentId", 1)
                .contentType(ContentType.JSON)
                .body(new UpdateCommentRequest("댓글 수정 테스트 중입니다."))
                .when()
                .put("/comments/{commentId}")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);
        assertThat(commentResponse.content()).isEqualTo("댓글 수정 테스트 중입니다.");
    }
    @DisplayName("댓글 삭제")
    @Test
    void deleteCommentTest() {
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
        List<String> imageUrl = List.of("https://example.com/image1.jpg111",
                "https://example.com/image2.jpg");
        PostResponse postResponse = RestAssured
                .given()
                .contentType(ContentType.JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .body(new CreatePost(imageUrl, "테스트입니다"))
                .when()
                .post("/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(PostResponse.class);
        //댓글 생성
        CommentResponse commentResponse = RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 삭제 테스트 중입니다.", postResponse.postId()))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as(CommentResponse.class);

        System.out.println("============\n"+commentResponse.id());
        System.out.println(commentResponse.content());
        System.out.println(commentResponse.loginId());
        System.out.println(commentResponse.postId());

        //댓글 삭제
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .pathParam("commentId", 1)
                .when()
                .delete("/comments/{commentId}")
                .then().log().all()
                .statusCode(200);
    }
}
