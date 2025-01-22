package CTHH.chanstagram.post.DTO;

import CTHH.chanstagram.User.User;

import java.time.LocalDateTime;
import java.util.List;

public record PostResponse(
        Long postId,
        String content,
        int commentCount,
        List<String> imageUrl,
        User user,
        LocalDateTime createdTime,
        LocalDateTime updatedTime
) {
}
