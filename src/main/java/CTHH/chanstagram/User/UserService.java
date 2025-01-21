package CTHH.chanstagram.User;

import CTHH.chanstagram.SecurityUtils;
import CTHH.chanstagram.User.DTO.LoginResponse;
import CTHH.chanstagram.User.DTO.UserRequest;
import CTHH.chanstagram.User.DTO.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //회원가입
    public UserRequest signUp(UserResponse userResponse) {
        String hashedPassword = SecurityUtils.sha256EncryptHex2(userResponse.password());
        User user = new User(userResponse.userName(),
                userResponse.nickName(),
                userResponse.loginId(),
                hashedPassword,
                userResponse.gender(),
                userResponse.birth(),
                userResponse.content(),
                userResponse.profileImage(),
                userResponse.phoneNumber());
        userRepository.save(user);
        return new UserRequest(user.getUserName(),
                user.getNickName(),
                user.getLoginId(),
                user.getPassword(),
                user.getGender(),
                user.getBirth(),
                user.getContent(),
                user.getProfileImage(),
                user.getPhoneNumber());
    }

}
