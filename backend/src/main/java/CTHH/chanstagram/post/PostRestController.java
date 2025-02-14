package CTHH.chanstagram.post;

import CTHH.chanstagram.Comment.CommentListResponse;
import CTHH.chanstagram.LoginMemberResolver;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.post.DTO.*;

import CTHH.chanstagram.post.postHashTag.PostHashTagService;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {

    private final PostService postService;
    private final LoginMemberResolver loginMemberResolver;
    private final PostHashTagService postHashTagService;


    public PostRestController(PostService postService, LoginMemberResolver loginMemberResolver, PostHashTagService postHashTagService) {

        this.postService = postService;
        this.loginMemberResolver = loginMemberResolver;
        this.postHashTagService = postHashTagService;
    }

    @PostMapping("/posts")
    public PostResponse create(@RequestBody CreatePost post,
                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        return postService.create(post, user);
    }

    @GetMapping("/posts/{nickName}") // 닉네임으로 게시글 조회
    public List<PostsByNickName> findByNickName(@PathVariable String nickName) {
        return postService.findByNickName(nickName);
    }

    @GetMapping("/posts") // 모든 게시글 조회
    public List<PostResponse> findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, value = "orderby") String criteria,
            @RequestParam(required = false, value = "searchby") String keyword) {

        return postService.findAll(page, size, criteria, keyword);
    }

    @GetMapping("/posts/detailed/{postId}")
    public PostDetailedResponse findByPostId(@PathVariable Long postId) {
        return postService.findByPostId(postId);
    }

    @PutMapping("/posts/{postId}")
    public void update(@PathVariable Long postId,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                       @RequestBody UpdatePost post) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        postService.update(postId, post, user);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        postService.delete(postId, user);
    }

    @PostMapping("/posts/{postId}")
    public void like(@PathVariable Long postId,
                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        postService.like(postId, user);
    }

    @GetMapping("/likedPosts/{nickname}") // user_id으로 좋아요한 게시글 조회
    public List<PostResponse> likedPostByUserId(@PathVariable String nickname) {
        return postService.likedPostByUserId(nickname);
    }

    @GetMapping("/hashtagposts/{hashtagname}")
    public PostListResponse findByHashTagName(@PathVariable(name = "hashtagname") String hashTagName) {
        return postHashTagService.findByHashTagName(hashTagName);
    }

    @GetMapping("/comments/{postId}")
    public CommentListResponse findByPostIdComments(@PathVariable Long postId) {
        return postService.findByPostIdComments(postId);
    }

}
