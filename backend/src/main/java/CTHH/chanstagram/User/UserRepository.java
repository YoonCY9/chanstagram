package CTHH.chanstagram.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository
        extends JpaRepository<User, String> {
    Optional<User> findByLoginId(String loginId);

    User findByNickName(String nickName); // 닉네임으로 유저 찾기
}
