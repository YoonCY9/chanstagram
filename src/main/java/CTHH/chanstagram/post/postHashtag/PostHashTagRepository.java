package CTHH.chanstagram.post.postHashtag;

import CTHH.chanstagram.post.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {

    // 특정 해시태그 ID와 연결된 모든 Post 반환
    @Query("SELECT ph.post FROM Post_HashTag ph WHERE ph.hashTag.id = :hashTagId")
    List<Post> findPostsByHashTagId(@Param("hashTagId") Long hashTagId);
}
