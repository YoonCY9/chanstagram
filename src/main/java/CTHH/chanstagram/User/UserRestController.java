package CTHH.chanstagram.User;

import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.DTO.UserRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    //회원가입
    @PostMapping("/users")
    public UserResponse signUp(@Valid @RequestBody UserRequest userRequest) {
        return userService.signUp(userRequest);
    }

    //로그인
    @PostMapping("/login")
    public String login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest);
    }
}
