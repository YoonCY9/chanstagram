package CTHH.chanstagram.Comment;

import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository repository;

    public CommentService(CommentRepository repository) {
        this.repository = repository;
    }

    public void create(CreateCommentRequest request) {

    }

    public void update(Long commnetId, UpdateCommentRequest request) {

    }

    public void delete(Long commentId) {

    }
}
