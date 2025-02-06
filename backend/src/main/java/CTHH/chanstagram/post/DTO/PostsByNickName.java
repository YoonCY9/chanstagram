package CTHH.chanstagram.post.DTO;

import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.User;

import java.time.LocalDateTime;
import java.util.List;

public record PostsByNickName(
        Long postId,
        String content,
        int commentCount,
        List<String> imageUrl,
        UserResponse user,
        LocalDateTime createdTime,
        LocalDateTime updatedTime) {
}
