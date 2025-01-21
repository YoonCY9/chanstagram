package CTHH.chanstagram.Comment;

import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService service;

    public CommentController(CommentService service) {
        this.service = service;
    }
    @PostMapping("/comment")
    public void create(@RequestBody CreateCommentRequest request){
        service.create(request);
    }
    @PutMapping("/comment/{commnetId}")
    public void update(@PathVariable Long commnetId,@RequestBody UpdateCommentRequest request){
        service.update(commnetId,request);
    }
    @DeleteMapping("/comment/{commentId}")
    public void delete(@PathVariable Long commentId){
        service.delete(commentId);
    }

}
