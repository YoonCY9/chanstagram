package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    // Follow 엔티티에서 follower와 followee가 모두 일치하는 데이터를 찾는 쿼리 메서드
    boolean existsByFollowerAndFollowee(User follower, User followee);
}
