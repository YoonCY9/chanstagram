package CTHH.chanstagram.User;

import CTHH.chanstagram.SecurityUtils;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.DTO.UserRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public UserService(UserRepository userRepository, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    //회원가입
    public UserResponse signUp(UserRequest userRequest) {
        String hashedPassword = SecurityUtils.sha256EncryptHex2(userRequest.password());
        User user = new User(userRequest.userName(),
                userRequest.nickName(),
                userRequest.loginId(),
                hashedPassword,
                userRequest.gender(),
                userRequest.birth(),
                userRequest.content(),
                userRequest.profileImage(),
                userRequest.phoneNumber());
        userRepository.save(user);
        return new UserResponse(user.getUserName(),
                user.getNickName(),
                user.getLoginId(),
                user.getPassword(),
                user.getGender(),
                user.getBirth(),
                user.getContent(),
                user.getProfileImage(),
                user.getPhoneNumber());
    }

    //로그인
    public String login(LoginRequest loginRequest) {
        User findUser = userRepository.findById(loginRequest.loginId()).orElseThrow(
                () -> new NoSuchElementException("ID 혹은 비밀번호가 틀립니다."));
        findUser.findByPassword(loginRequest.password());
        return jwtProvider.createToken(loginRequest.loginId());
    }

}
