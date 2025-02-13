package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.hashTag.HashTagResponse;
import CTHH.chanstagram.hashTag.HashTagService;
import CTHH.chanstagram.post.Like;
import CTHH.chanstagram.post.LikeRepository;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import CTHH.chanstagram.post.postHashTag.PostHashTagService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;
    private final HashTagService hashTagService;
    private final PostHashTagService postHashTagService;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository, HashTagService hashTagService, PostHashTagService postHashTagService) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.hashTagService = hashTagService;
        this.postHashTagService = postHashTagService;
    }

    @Transactional
    public CommentResponse create(CreateCommentRequest request, User user) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NoSuchElementException("postId를 찾을 수 없습니다.:" + request.postId()));
        Comment comment = new Comment(request.content(), user, post);
        commentRepository.save(comment);

        for (int i = 0; i < comment.getContent().length(); i++) {
            if (comment.getContent().charAt(i) == '#') {
                // 해시태그의 끝 위치 계산
                int endIndex = comment.getContent().indexOf(" ", i);
                if (endIndex == -1) {
                    endIndex = comment.getContent().length();
                }

                // 해시태그 추출
                String hashTagName = comment.getContent().substring(i + 1, endIndex).trim();

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
        post.increaseCommentCount();

        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getUser().getLoginId(),
                comment.getLikeCommentCount());


    }

    @Transactional
    public CommentResponse update(Long commentId, UpdateCommentRequest request, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("commentId를 찾을 수 없습니다.:" + commentId));
        if (comment.getUser().equals(user)) {
            comment.updateContent(request.content());
        } else throw new RuntimeException("작성자가 일치하지 않습니다.");


        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getUser().getLoginId(),
                comment.getLikeCommentCount());

    }

    @Transactional
    public void deleteById(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("commentId를 찾을 수 없습니다.:" + commentId));
        if (comment.getUser().equals(user)) {
            commentRepository.deleteById(commentId);
        } else throw new RuntimeException("작성자가 일치하지 않습니다.");
    }


    @Transactional
    public CommentResponse like(Long commentId, User user) {

        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저 게시글" + commentId));

        Like like = likeRepository.findByUser_LoginIdAndComment_Id(user.getLoginId(), commentId);
        if (like == null) {
            comment.upLikeCommentCount();
            likeRepository.save(new Like(user, comment));

        } else {
            comment.downLikeCommentCount();
            likeRepository.delete(like);
        }
        return new CommentResponse(
                comment.getId(),
                comment.getContent(),
                comment.getPost().getId(),
                comment.getUser().getLoginId(),
                comment.getLikeCommentCount());
    }
}
