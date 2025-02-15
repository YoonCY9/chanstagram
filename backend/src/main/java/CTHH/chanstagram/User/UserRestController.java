package CTHH.chanstagram.User;

import CTHH.chanstagram.LoginMemberResolver;
import CTHH.chanstagram.User.DTO.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    private final UserService userService;
    private final LoginMemberResolver loginMemberResolver;

    public UserRestController(UserService userService, LoginMemberResolver loginMemberResolver) {
        this.userService = userService;
        this.loginMemberResolver = loginMemberResolver;
    }

    //회원가입
    @PostMapping("/users")
    public UserDetailResponse signUp(@Valid @RequestBody UserDetailRequest userDetailRequest) {
        return userService.signUp(userDetailRequest);
    }

    //로그인
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    //회원 탈퇴
    @DeleteMapping("/users")
    public void deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        userService.deleteUser(user);
    }

    //회원 수정
    @PutMapping("/users")
    public void updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                           @RequestBody UserRequest userRequest) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        userService.updateUser(user, userRequest);
    }

    //내 프로필 조회
    @GetMapping("/me")
    public UserDetailResponse getUserInfo(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        User user = loginMemberResolver.resolveUserFromToken(authorization);
        return userService.getUserInfo(user);
    }

    //프로필 조회
    @GetMapping("/users/nickName/{nickName}")
    public UserfollowResponse getUserInfoByNickname(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization, @PathVariable String nickName) {
        User me = loginMemberResolver.resolveUserFromToken(authorization);
        return userService.getUserInfoByNickname(nickName, me);
    }

    @GetMapping("/users/loginId/{loginId}")
    public UserResponse getUserInfoByloginId(@PathVariable String loginId) {
        return userService.getUserInfoByloginId(loginId);
    }

    @GetMapping("/users")
    public UserListResponse findAll(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false, value = "searchby") String keyword) {
        return userService.findByNickName(page, size, keyword);
    }
}

