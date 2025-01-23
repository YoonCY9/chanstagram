package CTHH.chanstagram.hashTag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    // 해시태그 이름으로 ID를 조회하는 메서드
    Optional<Long> findIdByName(String name);
}
