package CTHH.chanstagram.post;

import CTHH.chanstagram.User.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;
    private final QLike like =QLike.like;
    private final QUser user=QUser.user;

    public PostQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

// user가 좋아요한 포스트만 filter하기
    public List<Post> getLikedPostsByUser(String loginId) {
        return queryFactory
                .selectFrom(post)
                .join(like)
                .on(post.id.eq(like.post.id))
                .where(like.user.loginId.eq(loginId))
                .fetch();
    }
    //좋아요순으로 포스트정렬



}
