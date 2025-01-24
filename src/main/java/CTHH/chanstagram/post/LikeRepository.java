package CTHH.chanstagram.post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like,Long> {
    Like findByUser_LoginIdAndPost_Id(String loginId, Long postId);
    Like findByUser_LoginIdAndComment_Id(String loginId, Long commentId);
}
