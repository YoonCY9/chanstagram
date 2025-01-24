package CTHH.chanstagram.post.postHashTag;

import CTHH.chanstagram.post.Post;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostHashTagRepository extends JpaRepository<PostHashTag, Long> {


}

