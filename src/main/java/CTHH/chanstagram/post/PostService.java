package CTHH.chanstagram.post;

import CTHH.chanstagram.Comment.Comment;
import CTHH.chanstagram.Comment.CommentDetailedResponse;
import CTHH.chanstagram.Comment.CommentRepository;
import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.JwtProvider;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.hashTag.HashTagService;
import CTHH.chanstagram.post.DTO.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final HashTagService hashTagService;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, LikeRepository likeRepository, HashTagService hashTagService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.hashTagService = hashTagService;
    }

    @Transactional
    public PostResponse create(CreatePost dto, String userName) {
        User user = userRepository.findByLoginId(userName).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저" + userName));

        Post post = new Post(dto.content(), dto.imageUrl(), user);

        for (int i = 0; i < dto.content().length(); i++) {
            if (dto.content().charAt(i) == '#' && dto.content().charAt(i + 1) != ' ') {
                hashTagService.create(dto.content().substring(i + 1, dto.content().indexOf(" ", i)));
            }
        }

        postRepository.save(post);
        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getCommentCount(),
                post.getImageUrl(),
                new UserResponse(user.getNickName(), user.getProfileImage()),
                post.getCreatedTime(),
                post.getUpdatedTime());
    }

    public List<PostsByNickName> findByNickName(String nickName) { // 닉네임으로 게시글조회
        List<Post> posts = postRepository.findByUser_NickName(nickName);
        return posts.stream()
                .map(p -> new PostsByNickName(
                        p.getId(),
                        p.getContent(),
                        p.getCommentCount(),
                        p.getImageUrl(),
                        new UserResponse(p.getUser().getNickName(), p.getUser().getProfileImage()),
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
                        new UserResponse(p.getUser().getNickName(), p.getUser().getProfileImage()),
                        p.getCreatedTime(),
                        p.getUpdatedTime()
                )).toList();
    }

    public PostDetailedResponse findByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        List<CommentDetailedResponse> commentDetailedResponses =
                comments.stream()
                        .map(c -> new CommentDetailedResponse(
                                c.getId(),
                                c.getContent(),
                                c.getUser().getNickName()
                        )).toList();
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 postId" + postId));

        User user = postRepository.findUserByPostId(postId);

        UserResponse userResponse = new UserResponse(user.getNickName(), user.getProfileImage());

        return new PostDetailedResponse(
                post.getId(),
                post.getContent(),
                post.getCommentCount(),
                post.getImageUrl(),
                userResponse,
                post.getCreatedTime(),
                post.getUpdatedTime(),
                commentDetailedResponses
        );
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
