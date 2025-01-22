package CTHH.chanstagram.post.DTO;

import java.util.List;

public record UpdatePost(String content,
                         List<String> imageUrl) {
}
