package CTHH.chanstagram.Comment;

public record CommentResponse(
        Long id,
        String content,
        Long postId,
        String loginId,
        int likeCount
) {
}
