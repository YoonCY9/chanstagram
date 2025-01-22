package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService service;
    private final UserService userService;

    public CommentController(CommentService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    //댓글생성
    @PostMapping("/comment")
    public void create(@RequestBody CreateCommentRequest request){
        service.create(request);
    }




}
