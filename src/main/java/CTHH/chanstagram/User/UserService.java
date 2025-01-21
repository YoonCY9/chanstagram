package CTHH.chanstagram.User;

import CTHH.chanstagram.Comment.CommentRepository;
import CTHH.chanstagram.SecurityUtils;
import CTHH.chanstagram.User.DTO.LoginRequest;
import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.DTO.UserRequest;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    public UserService(UserRepository userRepository, JwtProvider jwtProvider,
                       PostRepository postRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
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

    // 가입한 회원이 자신의 가입 정보를 조회하는 API
    public String getProfile(String authorization) {
        String[] tokenFormat = authorization.split(" ");
        String tokenType = tokenFormat[0];
        String token = tokenFormat[1];
        // Bearer 토큰인지 검증
        if (tokenType.equals("Bearer") == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }
        // 유효한 JWT 토큰인지 검증
        if (jwtProvider.isValidToken(token) == false) {
            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
        }
        // JWT 토큰에서 loginId 끄집어냄
        String loginId = jwtProvider.getSubject(token);
        return loginId;
    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(String loginId) {
        User user = userRepository.findById(loginId).orElseThrow(
                () -> new NoSuchElementException("로그인 정보가 유효하지 않습니다"));

        /* loginId 로 본인이 작성한 게시글 지우기 -> 게시글에 댓글이 있다면? / 게시글에 있는 댓글들 먼저 지우기
        1. 자신이 작성한 댓글 지우기
        2. 자신이 작성한 게시글에 달려있는 모든 댓글 지우기
        3. 자신이 작성한 게시글 지우기
         */
        userRepository.delete(user);
    }

    //회원 수정
}

