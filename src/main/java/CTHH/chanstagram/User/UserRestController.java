package CTHH.chanstagram.User;

import CTHH.chanstagram.User.DTO.UserRequest;
import CTHH.chanstagram.User.DTO.UserResponse;
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
    public UserRequest signUp(@Valid @RequestBody UserResponse userResponse) {
        return userService.signUp(userResponse);
    }
}
