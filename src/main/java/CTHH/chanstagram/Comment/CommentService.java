package CTHH.chanstagram.Comment;

import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    public CommentService(CommentRepository commentRepository, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public void create(CreateCommentRequest request) {
        Post post = postRepository.findById(request.postId())
                .orElseThrow(() -> new NoSuchElementException("id를 찾을 수 없습니다.:" + request.postId()));
        commentRepository.save(new Comment(request.content(),post));
    }

    public void update(Long commentId, UpdateCommentRequest request, String userid) {

    }

    public void delete(Long commentId, String userid) {
    }
}
