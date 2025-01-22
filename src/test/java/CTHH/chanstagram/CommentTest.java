package CTHH.chanstagram;

import CTHH.chanstagram.Comment.Comment;
import CTHH.chanstagram.Comment.CreateCommentRequest;
import CTHH.chanstagram.Comment.UpdateCommentRequest;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.DTO.UserRequest;
import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.post.DTO.CreatePost;
import CTHH.chanstagram.post.Post;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CommentTest extends UserTest {

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
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
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreatePost(List.of(),""))
                .when()
                .post("/posts")
                .then().log().all()
                .statusCode(200)
                .extract()
                .as();
        //댓글생성
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.",postId))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200);
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

        //댓글 생성
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .contentType(ContentType.JSON)
                .body(new CreateCommentRequest("댓글 생성 테스트 중입니다.",postId))
                .when()
                .post("/comments")
                .then().log().all()
                .statusCode(200);
        //댓글 수정
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .pathParam("commentId", "1")
                .contentType(ContentType.JSON)
                .body(new UpdateCommentRequest("댓글 수정 테스트 중입니다."))
                .when()
                .put("/comments/{commentId}")
                .then().log().all()
                .statusCode(200);
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

        //댓글 생성

        //댓글 삭제
        RestAssured
                .given().log().all()
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + loginResponse.token())
                .pathParam("commentId", "1")
                .when()
                .delete("/comments/{commentId}")
                .then().log().all()
                .statusCode(200);
    }
}
