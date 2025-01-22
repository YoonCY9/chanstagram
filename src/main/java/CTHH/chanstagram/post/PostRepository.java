package CTHH.chanstagram.post;

import CTHH.chanstagram.User.User;
import jakarta.transaction.Transactional;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Modifying
    void deleteByUser_LoginId(String loginId);

    List<Post> findByUser_NickName(String nickName); // 작성자(User)의 닉네임으로 게시글 조회

    List<Post> findIdByUser_LoginId(String loginId);

    @Query("SELECT p.user FROM Post p WHERE p.id = :postId") // 게시글 id로 유저정보 반환
    User findUserByPostId(@Param("postId") Long postId);
}

