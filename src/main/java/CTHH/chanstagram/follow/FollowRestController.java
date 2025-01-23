package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.follow.followDTO.CreateFollow;
import CTHH.chanstagram.follow.followDTO.FollowResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FollowRestController {

    private final FollowService followService;
    private final UserService userService;

    public FollowRestController(FollowService followService, UserService userService) {
        this.followService = followService;
        this.userService = userService;
    }

    @PostMapping("/follows/{nickName}")
    public void follow(@PathVariable String nickName,
                                 @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userId = userService.getProfile(authorization);
        followService.follow(userId, nickName);
    }
}
