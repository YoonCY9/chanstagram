package CTHH.chanstagram.Comment;

import java.util.List;

public record CommentListResponse(
        List<CommentResponse> comments
) {
}
