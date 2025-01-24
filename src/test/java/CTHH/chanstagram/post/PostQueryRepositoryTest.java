package CTHH.chanstagram.post;

import CTHH.chanstagram.User.Gender;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.hashTag.HashTag;
import CTHH.chanstagram.hashTag.HashTagRepository;
import CTHH.chanstagram.post.postHashTag.PostHashTag;
import CTHH.chanstagram.post.postHashTag.PostHashTagQueryMethodRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
public class PostQueryRepositoryTest {
    @Autowired
    private PostQueryRepository postQueryRepository;
    @Autowired
    private PostHashTagQueryMethodRepository postHashTagQueryMethodRepository;
    @Autowired
    private HashTagRepository hashTagRepository;

    @PersistenceContext
    EntityManager em;

    @Test
    void 유저별로좋아요리스트() {
        User user = new User("윤태우", "you", "younId", "11111", Gender.Man,
                LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796");
        em.persist(user);
        Post post1 = new Post("", List.of(), user);
        Post post2 = new Post("", List.of(), user);
        Post post3 = new Post("", List.of(), user);
        Post post4 = new Post("", List.of(), user);
        Post post5 = new Post("", List.of(), user);
        em.persist(post1);
        em.persist(post2);
        em.persist(post3);
        em.persist(post4);
        em.persist(post5);

        User user1 = new User("이호연", "yeon", "yeonId", "11111", Gender.Woman,
                LocalDate.parse("2001-08-18"), "잘부탁드립니다!!!", "ImageUrl", "11074877796");
        User user2 = new User("이호", "yeo", "yeoId", "11111", Gender.Woman,
                LocalDate.parse("2001-08-18"), "잘부탁드립니다!!!", "ImageUrl", "11074877795");
        User user3 = new User("이", "ye", "yeId", "11111", Gender.Woman,
                LocalDate.parse("2001-08-18"), "잘부탁드립니다!!!", "ImageUrl", "11074877794");

        em.persist(user1);
        em.persist(user2);
        em.persist(user3);

        Like like1 = new Like(user1, post1);
        Like like2 = new Like(user2, post1);
        Like like3 = new Like(user3, post1);

        Like like4 = new Like(user1, post2);
        Like like5 = new Like(user2, post2);

        em.persist(like1);
        em.persist(like2);
        em.persist(like3);
        em.persist(like4);
        em.persist(like5);
        System.out.println(postQueryRepository.getLikedPostsByUser(user1.getLoginId()));
        System.out.println(postQueryRepository.getLikedPostsByUser(user2.getLoginId()));
        System.out.println(postQueryRepository.getLikedPostsByUser(user3.getLoginId()));
    }

    @Test
    @Transactional
    void name() {
        User user = new User("윤태우", "you", "younId", "11111", Gender.Man,
                LocalDate.parse("2001-08-08"), "잘부탁드립니다!", "ImageUrl", "01074877796");
        em.persist(user);

        Post post1 = new Post("askmh #Test ", List.of(), user);
        em.persist(post1);

        HashTag hashTag = new HashTag("Test");
        em.persist(hashTag);

        PostHashTag postHashTag = new PostHashTag(hashTag, post1);
        em.persist(postHashTag);

        System.out.println(postHashTag.getHashTag().getName());

        System.out.println(postHashTagQueryMethodRepository.findPostsByHashTagId(hashTag.getId()));
    }

    @Test
    void findIdByNameTest() {
        HashTag hashTag = new HashTag("Test");
        em.persist(hashTag);

        hashTagRepository.findByName("Test").orElseThrow();

    }
}
