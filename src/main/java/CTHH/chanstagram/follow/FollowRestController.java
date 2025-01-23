package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.UserService;
import CTHH.chanstagram.follow.followDTO.FollowResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // get 요청으로 줘야할것 프로필이미지, nickname , username

    // {nickName}의 팔로워 리스트
    @GetMapping("/follows/followers/{nickName}")
    public List<FollowResponse> findAllFollowers(@PathVariable String nickName) {
        return followService.findAllFollowers(nickName);
    }
    // {nickName}의 팔로우 리스트
    @GetMapping("/follows/followees/{nickName}")
    public List<FollowResponse> findAllFollowees(@PathVariable String nickName) {
        return followService.findAllFollowees(nickName);
    }


}
