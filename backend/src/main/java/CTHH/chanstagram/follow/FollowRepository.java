package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow,Long> {

    // Follow 엔티티에서 follower와 followee가 모두 일치하는 데이터를 찾는 쿼리 메서드
    boolean existsByFollowerAndFollowee(User follower, User followee);

    // nickName으로 follower 리스트 반환
    @Query("SELECT f.follower FROM Follow f WHERE f.followee.nickName = :nickName")
    List<User> findFollowersByFolloweeNickName(@Param("nickName") String nickName);

    // nickName으로 followee 리스트 반환
    @Query("SELECT f.followee FROM Follow f WHERE f.follower.nickName = :nickName")
    List<User> findFolloweesByFollowerNickName(@Param("nickName") String nickName);

    // follower의 nickName과 followee의 nickName이 모두 일치하는 단일 Follow 객체의 id를 반환하는 쿼리 메서드
    Optional<Follow> findByFollower_NickNameAndFollowee_NickName(String followerNickName, String followeeNickName);
}
