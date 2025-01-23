package CTHH.chanstagram.post.post_hashtag;

import CTHH.chanstagram.hashTag.HashTag;
import CTHH.chanstagram.hashTag.HashTagRepository;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class Post_HashTagService {
    private final Post_HashTagRepository postHashTagRepository;
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    public Post_HashTagService(Post_HashTagRepository postHashTagRepository, PostRepository postRepository, HashTagRepository hashTagRepository) {
        this.postHashTagRepository = postHashTagRepository;
        this.postRepository = postRepository;
        this.hashTagRepository = hashTagRepository;
    }

    public void create(Long postId, Long hashTagId) {
        Post findPost = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("해당하는 게시글이 없습니다."));
        HashTag findHasTag = hashTagRepository.findById(hashTagId).orElseThrow(
                () -> new NoSuchElementException("해당하는 해시테그가 없습니다."));
        postHashTagRepository.save(new Post_HashTag(findHasTag, findPost));
    }
}
