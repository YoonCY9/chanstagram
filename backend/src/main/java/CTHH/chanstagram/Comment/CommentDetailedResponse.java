package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.DTO.UserResponse;
import CTHH.chanstagram.User.User;

public record CommentDetailedResponse(
        Long id,
        String content,
        String nickName
) {
}
