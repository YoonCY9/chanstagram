package CTHH.chanstagram.Comment;

import CTHH.chanstagram.LoginMemberResolver;
import CTHH.chanstagram.User.User;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class CommentController {
    private final CommentService service;
    private final LoginMemberResolver loginMemberResolver;

    public CommentController(CommentService service, LoginMemberResolver loginMemberResolver) {
        this.service = service;
        this.loginMemberResolver = loginMemberResolver;
    }

    //댓글생성
    @PostMapping("/comments")
    public CommentResponse create(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @RequestBody CreateCommentRequest request) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        return service.create(request, user);
    }

    //댓글수정
    @PutMapping("/comments/{commentId}")
    public CommentResponse update(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                                  @PathVariable Long commentId,
                                  @RequestBody UpdateCommentRequest request) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        return service.update(commentId, request, user);
    }

    //댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public void delete(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                       @PathVariable Long commentId) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        service.deleteById(commentId, user);
    }

    //댓글좋아요
    @PostMapping("/comments/{commentId}")
    public void like(@PathVariable Long commentId,
                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        service.like(commentId, user);
    }
}
