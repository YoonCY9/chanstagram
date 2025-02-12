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

    @GetMapping("/users/{nickName}")
    public UserResponse getUserInfoByNickname(@PathVariable String nickName) {
        return userService.getUserInfoByNickname(nickName);
    }
}

