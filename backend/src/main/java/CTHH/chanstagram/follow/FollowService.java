package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;

import CTHH.chanstagram.follow.followDTO.FollowResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public void follow(User follower, String followeeName) {
        User followee = userRepository.findByNickName(followeeName);

        if (follower.getNickName().equals(followee.getNickName())) {
            throw new IllegalArgumentException("자기 자신은 팔로우 할 수 없습니다");
        }

        if (followRepository.existsByFollowerAndFollowee(follower, followee)) {
            Follow follow = followRepository
                    .findByFollower_NickNameAndFollowee_NickName(follower.getNickName(), followee.getNickName()).orElseThrow();
            followRepository.deleteById(follow.getId());
        } else {
            Follow follow = new Follow(follower, followee);
            followRepository.save(follow);
        }
    }

    // nickName의 팔로워 리스트
    public List<FollowResponse> findAllFollowers(String nickName) {
        List<User> followers = followRepository.findFollowersByFolloweeNickName(nickName);

        return followers.stream()
                .map(f -> new FollowResponse(
                        f.getProfileImage(),
                        f.getUserName(),
                        f.getNickName())).toList();
    }

    // nickName의 팔로우리스트
    public List<FollowResponse> findAllFollowees(String nickName) {
        List<User> followees = followRepository.findFolloweesByFollowerNickName(nickName);

        return followees.stream()
                .map(f -> new FollowResponse(
                        f.getProfileImage(),
                        f.getUserName(),
                        f.getNickName())).toList();
    }


}
