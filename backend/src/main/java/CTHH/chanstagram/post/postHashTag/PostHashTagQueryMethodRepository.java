package CTHH.chanstagram.post.postHashTag;

import CTHH.chanstagram.hashTag.QHashTag;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.QPost;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostHashTagQueryMethodRepository {

    private final JPAQueryFactory jpaQueryFactory;
    private final QPost post = QPost.post;
    private final QPostHashTag postHashTag = QPostHashTag.postHashTag;
    private final QHashTag hashTag = QHashTag.hashTag;

    public PostHashTagQueryMethodRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Post> findPostsByHashTagId(Long hashTagId) {
        return jpaQueryFactory
                .selectFrom(post)
                .join(postHashTag).on(postHashTag.post.eq(post))
                .join(postHashTag.hashTag).on(postHashTag.hashTag.id.eq(hashTagId))
                .fetch();
    }
}
