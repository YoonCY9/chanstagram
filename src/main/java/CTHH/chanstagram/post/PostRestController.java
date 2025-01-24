package CTHH.chanstagram.post;

import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.post.DTO.*;

import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PostRestController {

    private final PostService postService;
    private final UserService userService;
    private final PostHashTagService postHashTagService;

    public PostRestController(PostService postService, UserService userService, PostHashTagService postHashTagService) {
        this.postService = postService;
        this.userService = userService;
        this.postHashTagService = postHashTagService;
    }

    @PostMapping("/posts")
    public PostResponse create(@RequestBody CreatePost post,
                               @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userId = userService.getProfile(authorization);
        return postService.create(post, userId);
    }

    @GetMapping("/posts/{nickName}") // 닉네임으로 게시글 조회
    public List<PostsByNickName> findByNickName(@PathVariable String nickName) {
        return postService.findByNickName(nickName);
    }

    @GetMapping("/posts") // 모든 게시글 조회
    public List<PostResponse> findAll(@RequestParam(required = false, value = "orderby") String criteria) {
        if ("like".equals(criteria)) return postService.findAll(criteria);
       else return postService.findAll();

    }

    @GetMapping("/posts/detailed/{postId}")
    public PostDetailedResponse findByPostId(@PathVariable Long postId) {
        return postService.findByPostId(postId);
    }

    @PutMapping("/posts/{postId}")
    public void update(@PathVariable Long postId,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                       @RequestBody UpdatePost post) {
        String userId = userService.getProfile(authorization);
        postService.update(postId, post, userId);
    }

    @DeleteMapping("/posts/{postId}")
    public void delete(@PathVariable Long postId,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userId = userService.getProfile(authorization);
        postService.delete(postId, userId);
    }

    @PostMapping("/posts/{postId}")
    public void like(@PathVariable Long postId,
                     @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userId = userService.getProfile(authorization);
        postService.like(postId, userId);
    }

    @GetMapping("/likedPosts/{loginId}") // user_id으로 좋아요한 게시글 조회
    public List<PostResponse> likedPostByUserId(@PathVariable String loginId) {
        return postService.likedPostByUserId(loginId);
    }

    @GetMapping("/hashtagposts/{hashtagname}")
    public PostListResponse findByHashTagName(@RequestParam(name = "hashtagname") String hashTagName) {
        return postHashTagService.findByHashTagName(hashTagName);
    }

}
