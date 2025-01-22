package CTHH.chanstagram.User;

import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.UserDetailResponse;
import CTHH.chanstagram.User.DTO.UserDetailRequest;
import CTHH.chanstagram.User.DTO.UserRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/users")
    public UserDetailResponse signUp(@Valid @RequestBody UserDetailRequest userDetailRequest) {
        return userService.signUp(userDetailRequest);
    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }

    //회원 탈퇴
    @DeleteMapping("/users")
    public void deleteUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String userid = userService.getProfile(authorization);
        userService.deleteUser(userid);
    }

    //회원 수정
    @PutMapping("/users")
    public void updateUser(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
                           @RequestBody UserRequest userRequest) {
        String userid = userService.getProfile(authorization);
        userService.updateUser(userid, userRequest);
    }
}
