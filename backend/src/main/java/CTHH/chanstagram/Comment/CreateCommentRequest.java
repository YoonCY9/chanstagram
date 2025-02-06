package CTHH.chanstagram.Comment;

public record CreateCommentRequest(
        String content,
        Long postId
) {
}
