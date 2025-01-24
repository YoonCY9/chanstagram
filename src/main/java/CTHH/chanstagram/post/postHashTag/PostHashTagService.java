package CTHH.chanstagram.post.postHashTag;

import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.hashTag.HashTag;
import CTHH.chanstagram.hashTag.HashTagRepository;
import CTHH.chanstagram.post.DTO.PostListResponse;
import CTHH.chanstagram.post.DTO.PostResponse;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostHashTagService {
    private final PostHashTagRepository postHashTagRepository;
    private final PostRepository postRepository;
    private final HashTagRepository hashTagRepository;

    public PostHashTagService(PostHashTagRepository postHashTagRepository, PostRepository postRepository, HashTagRepository hashTagRepository) {
        this.postHashTagRepository = postHashTagRepository;
        this.postRepository = postRepository;
        this.hashTagRepository = hashTagRepository;
    }

    public void create(Long postId, Long hashTagId) {
        Post findPost = postRepository.findById(postId).orElseThrow(
                () -> new NoSuchElementException("해당하는 게시글이 없습니다."));
        HashTag findHasTag = hashTagRepository.findById(hashTagId).orElseThrow(
                () -> new NoSuchElementException("해당하는 해시테그가 없습니다."));
        postHashTagRepository.save(new postHashTag(findHasTag, findPost));
    }

    public PostListResponse findByHashTagName(String hashTagName) {
        Long hashTagId = hashTagRepository.findIdByName(hashTagName).orElseThrow(
                () -> new NoSuchElementException("해당하는 해시테그가 없습니다"));
        List<Post> findPosts = postHashTagRepository.findPostsByHashTagId(hashTagId);
        return new PostListResponse(findPosts.stream()
                .map(post -> new PostResponse(post.getId(),
                        post.getContent(),
                        post.getCommentCount(),
                        post.getImageUrl(),
                        new UserResponse(post.getUser().getNickName(), post.getUser().getProfileImage())
                        , post.getCreatedTime(),
                        post.getUpdatedTime(),
                        post.getLikeCount()))
                .toList()
        );
    }
}
