package CTHH.chanstagram.post;

import CTHH.chanstagram.User.QUser;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PostQueryRepository {

    private final JPAQueryFactory queryFactory;
    private final QPost post = QPost.post;
    private final QLike like = QLike.like;
    private final QUser user = QUser.user;

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

    public List<Post> findAll(int page, int size, String criteria, String keyword) {
        return queryFactory
                .selectFrom(post)
                .where(findByKeyword(keyword))
                .orderBy(findLikedPost(criteria))
                .offset(size * (page - 1))
                .limit(size)
                .fetch();
    }

    private OrderSpecifier findLikedPost(String criteria) {
        if (criteria == null||!criteria.equals("like")) {
            return post.createdTime.desc();
        }
        return post.likeCount.desc();

    }

    private BooleanExpression findByKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        return post.content.contains(keyword);
    }

}




