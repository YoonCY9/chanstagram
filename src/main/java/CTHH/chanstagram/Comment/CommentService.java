package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public void create(CreateCommentRequest request,String userId) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NoSuchElementException("postId를 찾을 수 없습니다.:" + request.postId()));
        User user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new NoSuchElementException("userId를 찾을 수 없습니다.:" + userId));
        ;
        commentRepository.save(new Comment(request.content(),user,post));
    }
    @Transactional
    public void update(Long commentId, UpdateCommentRequest request, String userId) {
        User user = userRepository.findByLoginId(userId)
                .orElseThrow(() -> new NoSuchElementException("userId를 찾을 수 없습니다.:" + userId));
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NoSuchElementException("commentId를 찾을 수 없습니다.:" + commentId));
        if (comment.getUser().equals(user)) {
            comment.updateContent(request.content());
        } else throw new RuntimeException("작성자가 일치하지 않습니다.");
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
}
