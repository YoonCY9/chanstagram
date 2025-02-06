package CTHH.chanstagram.hashTag;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HashTagRepository extends JpaRepository<HashTag, Long> {

    // name으로 HashTag를 조회하는 쿼리 메서드
    Optional<HashTag> findByName(String name);
}
