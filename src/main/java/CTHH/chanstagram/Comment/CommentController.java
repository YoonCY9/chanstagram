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
    public void create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody CreateCommentRequest request){
        String userid = userService.getProfile(authorization);
        service.create(request,userid);
    }

    //댓글수정
    @PutMapping("/comment/{commentId}")
    public void update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                       @PathVariable Long commentId,
                       @RequestBody UpdateCommentRequest request){
        String userid = userService.getProfile(authorization);
        service.update(commentId,request,userid);
    }
    //댓글 삭제
    @DeleteMapping("/comment/{commentId}")
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                       @PathVariable Long commentId){
        String userid = userService.getProfile(authorization);
        service.deleteById(commentId,userid);
    }


}
