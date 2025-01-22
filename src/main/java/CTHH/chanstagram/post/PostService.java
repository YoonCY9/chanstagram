package CTHH.chanstagram.post;

import CTHH.chanstagram.Comment.CommentRepository;
import CTHH.chanstagram.User.JwtProvider;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.post.DTO.CreatePost;
import CTHH.chanstagram.post.DTO.PostResponse;
import CTHH.chanstagram.post.DTO.PostsByNickName;
import CTHH.chanstagram.post.DTO.UpdatePost;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional
    public PostResponse create(CreatePost dto, String userName) {
        User user = userRepository.findByLoginId(userName).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저" + userName));

        Post post = new Post(dto.content(), dto.imageUrl(), user);
        postRepository.save(post);
        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getCommentCount(),
                post.getImageUrl(),
                post.getUser(),
                post.getCreatedTime(),
                post.getUpdatedTime());
    }

    public List<PostsByNickName> findByNickName(String nickName) {
        List<Post> posts = postRepository.findByUser_NickName(nickName);
        return posts.stream()
                .map(p -> new PostsByNickName(
                        p.getId(),
                        p.getContent(),
                        p.getCommentCount(),
                        p.getImageUrl(),
                        p.getUser(),
                        p.getCreatedTime(),
                        p.getUpdatedTime()
                )).toList();
    }
    public List<PostResponse> findAll() { // 게시글 전체조회
        List<Post> posts = postRepository.findAll();

        return posts.stream()
                .map(p -> new PostResponse(
                p.getId(),
                p.getContent(),
                p.getCommentCount(),
                p.getImageUrl(),
                p.getUser(),
                p.getCreatedTime(),
                p.getUpdatedTime()
        )).toList();
    }


    @Transactional
    public void update(Long postId, UpdatePost dto, String userName) {
        User user = userRepository.findByLoginId(userName).orElseThrow();

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 게시글" + postId));

        if (post.getUser().getUserName().equals(user.getUserName())) {
            post.setPost(dto.content(), dto.imageUrl());
        }
    }


    @Transactional
    // 댓글 지우는 기능 나중에 추가해야함
    public void delete(Long postId, String userName) {
        User user = userRepository.findByLoginId(userName).orElseThrow();

        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저 게시글" + postId));
        if (post.getUser().getUserName().equals(user.getUserName())) {
            commentRepository.deleteAllByPostId(postId);
            postRepository.delete(post);
        }
    }
}
