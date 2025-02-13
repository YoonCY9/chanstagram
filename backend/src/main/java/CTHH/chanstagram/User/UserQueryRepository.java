package CTHH.chanstagram.User;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserQueryRepository {
    private final JPAQueryFactory queryFactory;
    private final QUser user = QUser.user;

    public UserQueryRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public List<User> findUser(int page, int size, String keyword) {
        return queryFactory.selectFrom(user)
                .where(findByKeyword(keyword))
                .offset(size * (page - 1))
                .limit(size)
                .fetch();
    }


    private BooleanExpression findByKeyword(String keyword) {
        if (keyword == null) {
            return null;
        }
        return user.nickName.contains(keyword);
    }


}
