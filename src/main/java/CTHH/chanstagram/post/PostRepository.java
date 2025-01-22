package CTHH.chanstagram.post;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

public interface PostRepository extends JpaRepository<Post, Long> {
    @Transactional
    @Modifying
    void deleteByUser_LoginId(String loginId);
}
