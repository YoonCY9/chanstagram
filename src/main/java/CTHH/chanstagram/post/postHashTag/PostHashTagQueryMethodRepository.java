package CTHH.chanstagram.post.postHashTag;

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

    public PostHashTagQueryMethodRepository(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    public List<Post> findPostsByHashTagId(Long hashTagId) {
        List<Post> posts = jpaQueryFactory
                .select(post)
                .from(postHashTag)
                .join(postHashTag.post, post)
                .where(postHashTag.hashTag.id.eq(hashTagId))
                .fetch();

        posts.forEach(post -> System.out.println("Post ID: " + post.getId()));
        return posts;
    }

}
