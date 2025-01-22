package CTHH.chanstagram.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Transactional
    @Modifying
    void deleteByUser_LoginId(String loginId);

    List<Post> findByUser_NickName(String nickName); // 작성자(User)의 닉네임으로 게시글 조회
}
