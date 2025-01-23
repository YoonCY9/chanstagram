package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    // Follow 엔티티에서 follower와 followee가 모두 일치하는 데이터를 찾는 쿼리 메서드
    boolean existsByFollowerAndFollowee(User follower, User followee);

    // follower의 nickName과 followee의 nickName이 모두 일치하는 단일 Follow 객체의 id를 반환하는 쿼리 메서드
    Optional<Follow> findByFollower_NickNameAndFollowee_NickName(String followerNickName, String followeeNickName);
}
