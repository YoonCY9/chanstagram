package CTHH.chanstagram.post.DTO;

import java.util.List;

public record CreatePost(List<String> imageUrl, String content, User user) {
}
