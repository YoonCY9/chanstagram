package CTHH.chanstagram.follow;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserRepository;
import CTHH.chanstagram.follow.followDTO.CreateFollow;
import CTHH.chanstagram.follow.followDTO.FollowResponse;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserRepository userRepository;

    public FollowService(FollowRepository followRepository, UserRepository userRepository) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
    }

    public void follow(String followerName, String followeeName) {
        User follower = userRepository.findByLoginId(followerName).orElseThrow(() ->
                new NoSuchElementException("존재하지 않는 유저" + followerName));

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


}
