package CTHH.chanstagram.post;

import CTHH.chanstagram.Comment.Comment;
import CTHH.chanstagram.Comment.CommentDetailedResponse;
import CTHH.chanstagram.Comment.CommentRepository;
import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.hashTag.HashTagResponse;
import CTHH.chanstagram.hashTag.HashTagService;
import CTHH.chanstagram.post.DTO.*;
import CTHH.chanstagram.post.postHashTag.PostHashTagService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final LikeRepository likeRepository;
    private final HashTagService hashTagService;
    private final PostHashTagService postHashTagService;
    private final PostQueryRepository postQueryRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository, CommentRepository commentRepository, LikeRepository likeRepository, HashTagService hashTagService, PostHashTagService postHashTagService, PostQueryRepository postQueryRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentRepository = commentRepository;
        this.likeRepository = likeRepository;
        this.hashTagService = hashTagService;
        this.postHashTagService = postHashTagService;
        this.postQueryRepository = postQueryRepository;
    }

    @Transactional
    public PostResponse create(CreatePost dto, String userName) {
        User user = userRepository.findByLoginId(userName).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저" + userName));

        Post post = new Post(dto.content(), dto.imageUrl(), user);

        postRepository.save(post);

         /*
         #으로 헤시테그를 나누고 해당 헤시테그가 없다면 해시테그 생성 & postHashTag 생성
         있다면 해시테그 이름으로 해시테그 ID 찾고 중간 postHashTag 생성
         */

        for (int i = 0; i < dto.content().length(); i++) {
            if (dto.content().charAt(i) == '#') {
                // 해시태그의 끝 위치 계산
                int endIndex = dto.content().indexOf(" ", i);
                if (endIndex == -1) {
                    endIndex = dto.content().length();
                }

                // 해시태그 추출
                String hashTagName = dto.content().substring(i + 1, endIndex).trim();

                // 빈 해시태그는 건너뜀
                if (hashTagName.isEmpty()) {
                    continue;
                }

                // 해시태그 ID 조회 및 생성
                if (hashTagService.findIdByName(hashTagName) == null) {
                    HashTagResponse createdHashTag = hashTagService.create(hashTagName);
                    postHashTagService.create(post.getId(), createdHashTag.id());
                } else {
                    postHashTagService.create(post.getId(), hashTagService.findIdByName(hashTagName));
                }

            }
        }


        return new PostResponse(
                post.getId(),
                post.getContent(),
                post.getCommentCount(),
                post.getImageUrl(),
                new UserResponse(user.getNickName(), user.getProfileImage()),
                post.getCreatedTime(),
                post.getUpdatedTime(),
                post.getLikeCount());
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
                .sorted(Comparator.comparing(Post::getCreatedTime).reversed()) // 최신순으로 정렬
                .map(p -> new PostResponse(
                        p.getId(),
                        p.getContent(),
                        p.getCommentCount(),
                        p.getImageUrl(),
                        new UserResponse(p.getUser().getNickName(), p.getUser().getProfileImage()),
                        p.getCreatedTime(),
                        p.getUpdatedTime(),
                        p.getLikeCount()
                )).toList();
    }

    public List<PostResponse> findAll(String criteria) { // 게시글 전체조회
        List<Post> byLikeCountDesc = postRepository.findAllByOrderByLikeCountDesc();

        return byLikeCountDesc.stream()
                .map(p -> new PostResponse(
                        p.getId(),
                        p.getContent(),
                        p.getCommentCount(),
                        p.getImageUrl(),
                        new UserResponse(p.getUser().getNickName(), p.getUser().getProfileImage()),
                        p.getCreatedTime(),
                        p.getUpdatedTime(),
                        p.getLikeCount()
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
                commentDetailedResponses,
                post.getLikeCount()
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

    @Transactional
    public void like(Long postId, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저" + loginId));
        Post post = postRepository.findById(postId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저 게시글" + postId));

        Like like = likeRepository.findByUser_LoginIdAndPost_Id(loginId, postId);
        if (like == null) {
            post.upLikeCount();
            likeRepository.save(new Like(user, post));

        } else {
            post.downLikeCount();
            likeRepository.delete(like);
        }
    }

    @Transactional
    public List<PostResponse> likedPostByUserId(String userId) {
        List<Post> likedPostsByUser = postQueryRepository.getLikedPostsByUser(userId);
        return likedPostsByUser.stream()
                .map(p -> new PostResponse(
                        p.getId(),
                        p.getContent(),
                        p.getCommentCount(),
                        p.getImageUrl(),
                        new UserResponse(p.getUser().getNickName(), p.getUser().getProfileImage()),
                        p.getCreatedTime(),
                        p.getUpdatedTime(),
                        p.getLikeCount()
                )).toList();
    }
}
