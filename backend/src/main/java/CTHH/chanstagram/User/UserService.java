package CTHH.chanstagram.User;

import CTHH.chanstagram.Comment.CommentRepository;
import CTHH.chanstagram.SecurityUtils;
import CTHH.chanstagram.User.DTO.*;
import CTHH.chanstagram.follow.FollowRepository;
import CTHH.chanstagram.post.Post;
import CTHH.chanstagram.post.PostRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final FollowRepository followRepository;
    private final UserQueryRepository userQueryRepository;

    public UserService(UserRepository userRepository, JwtProvider jwtProvider,
                       PostRepository postRepository, CommentRepository commentRepository, FollowRepository followRepository, UserQueryRepository userQueryRepository) {
        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
        this.followRepository = followRepository;
        this.userQueryRepository = userQueryRepository;
    }

    //회원가입
    public UserDetailResponse signUp(UserDetailRequest userDetailRequest) {
        String hashedPassword = SecurityUtils.sha256EncryptHex2(userDetailRequest.password());
        User user = new User(userDetailRequest.userName(),
                userDetailRequest.nickName(),
                userDetailRequest.loginId(),
                hashedPassword,
                userDetailRequest.gender(),
                userDetailRequest.birth(),
                userDetailRequest.content(),
                "https://png.pngtree.com/png-clipart/20220112/ourmid/pngtree-cartoon-hand-drawn-default-avatar-png-image_4154232.png",
                userDetailRequest.phoneNumber());
        userRepository.save(user);
        return new UserDetailResponse(user.getUserName(),
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
    public LoginResponse login(LoginRequest loginRequest) {
        User findUser = userRepository.findById(loginRequest.loginId()).orElseThrow(
                () -> new NoSuchElementException("ID 혹은 비밀번호가 틀립니다."));
        findUser.findByPassword(loginRequest.password());
        return new LoginResponse(jwtProvider.createToken(loginRequest.loginId()));
    }

    // 가입한 회원이 자신의 가입 정보를 조회하는 API
//    public String getProfile(String authorization) {
//        String[] tokenFormat = authorization.split(" ");
//        String tokenType = tokenFormat[0];
//        String token = tokenFormat[1];
//        // Bearer 토큰인지 검증
//        if (tokenType.equals("Bearer") == false) {
//            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
//        }
//        // 유효한 JWT 토큰인지 검증
//        if (jwtProvider.isValidToken(token) == false) {
//            throw new IllegalArgumentException("로그인 정보가 유효하지 않습니다");
//        }
//        // JWT 토큰에서 loginId 끄집어냄
//        String loginId = jwtProvider.getSubject(token);
//        return loginId;
//    }

    //회원 탈퇴
    @Transactional
    public void deleteUser(User user) {
        commentRepository.deleteByUser_LoginId(user.getLoginId());
        List<Post> postIdList = postRepository.findIdByUser_LoginId(user.getLoginId());

        for (Post post : postIdList) {
            commentRepository.deleteAllByPostId(post.getId());
            postRepository.delete(post);
        }
        userRepository.delete(user);
    }

    //회원 수정
    @Transactional
    public void updateUser(User user, UserRequest userRequest) {
        user.updateUserName(userRequest.userName());
        user.updateNickName(userRequest.nickName());
        user.updateGender(userRequest.gender());
        user.updateBirth(userRequest.birth());
        user.updateContent(userRequest.content());
        user.updateProfileImage(userRequest.profileImage());
        user.updatePhoneNumber(userRequest.phoneNumber());
    }

    public User findByLoginId(String loginId) {
        return userRepository.findByLoginId(loginId).orElseThrow(
                () -> new NoSuchElementException("회원을 찾을 수 없습니다."));
    }

    public UserDetailResponse getUserInfo(User user) {
        return new UserDetailResponse(
                user.getUserName(),
                user.getNickName(),
                user.getLoginId(),
                user.getPassword(),
                user.getGender(),
                user.getBirth(),
                user.getContent(),
                user.getProfileImage(),
                user.getPhoneNumber());
    }

    public UserfollowResponse getUserInfoByNickname(String nickName, User me) {
        User byNickName = userRepository.findByNickName(nickName);
        boolean follow = followRepository.existsByFollowerAndFollowee(me, byNickName);
        return new UserfollowResponse(byNickName.getNickName(), byNickName.getProfileImage(), follow);
    }

    public UserResponse getUserInfoByloginId(String loginId) {
        User user = userRepository.findByLoginId(loginId).orElseThrow(
                () -> new NoSuchElementException("해당하는 유저가 없습니다.")
        );
        return new UserResponse(user.getNickName(), user.getProfileImage());
    }

    public UserListResponse findByNickName(int page, int size, String keyWord) {
        List<User> users = userQueryRepository.findUser(page, size, keyWord);
        return new UserListResponse(
                users.stream()
                        .map(user -> new UserResponse(user.getNickName(), user.getProfileImage()))
                        .toList());
    }
}

