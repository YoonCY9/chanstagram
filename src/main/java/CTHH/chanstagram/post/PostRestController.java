package CTHH.chanstagram.post;

import CTHH.chanstagram.LoginMemberResolver;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.post.DTO.*;

import CTHH.chanstagram.post.postHashTag.PostHashTagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<PostResponse> findAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, value = "orderby") String criteria) {

        // http://localhost:8080/posts?page=0&size=5&orderby=like (예시)

        Sort createdTime = Sort.by(Sort.Direction.DESC, "createdTime");
        Pageable createdTimePage = PageRequest.of(page, size, createdTime);

        Sort likeCount = Sort.by(Sort.Direction.DESC, "likeCount");
        Pageable likeCountPage = PageRequest.of(page, size, likeCount);

        if ("like".equals(criteria)) {
            return postService.findAll(likeCountPage);
        } else {
            return postService.findAll(createdTimePage);
        }

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

}
