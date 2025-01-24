package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.post.Like;
import CTHH.chanstagram.post.LikeRepository;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository, LikeRepository likeRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
    }

    public CommentResponse create(CreateCommentRequest request,String userId) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NoSuchElementException("postId를 찾을 수 없습니다.:" + request.postId()));
        User user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new NoSuchElementException("userId를 찾을 수 없습니다.:" + userId));
        Comment comment = new Comment(request.content(), user, post);
        commentRepository.save(comment);
        post.increaseCommentCount();
        return new CommentResponse(comment.id,comment.getContent(),comment.getPost().getId(),comment.getUser().getLoginId());

    }
    @Transactional
    public CommentResponse update(Long commentId, UpdateCommentRequest request, String userId) {
        User user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new NoSuchElementException("userId를 찾을 수 없습니다.:" + userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("commentId를 찾을 수 없습니다.:" + commentId));
        if (comment.getUser().equals(user)) {
            comment.updateContent(request.content());
        } else throw new RuntimeException("작성자가 일치하지 않습니다.");

        return new CommentResponse(comment.id,comment.getContent(),comment.getPost().getId(),comment.getUser().getLoginId());
    }
    @Transactional
    public void deleteById(Long commentId, String userId) {
        User user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new NoSuchElementException("userId를 찾을 수 없습니다.:" + userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("commentId를 찾을 수 없습니다.:" + commentId));
        if (comment.getUser().equals(user)) {
            commentRepository.deleteById(commentId);
        } else throw new RuntimeException("작성자가 일치하지 않습니다.");
    }


    @Transactional
    public void like(Long commentId, String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저" + loginId));
        Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저 게시글" + commentId));

        Like like = likeRepository.findByUser_LoginIdAndComment_Id(loginId, commentId);
        if (like == null) {
            likeRepository.save(new Like(user, comment));

        } else {
            likeRepository.delete(like);
        }
    }
}
