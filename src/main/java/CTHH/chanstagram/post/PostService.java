package CTHH.chanstagram.post;

import CTHH.chanstagram.User.JwtProvider;
import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.post.DTO.CreatePost;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public void create(CreatePost dto, String authorization) {
        String userId = userService.
    }


}
