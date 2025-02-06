package CTHH.chanstagram.post.DTO;

import java.util.List;

public record PostListResponse(
        List<PostResponse> postResponses
) {
}
