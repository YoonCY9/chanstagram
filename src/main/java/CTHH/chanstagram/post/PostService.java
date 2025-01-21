package CTHH.chanstagram.post;

import CTHH.chanstagram.post.DTO.CreatePost;
import org.springframework.stereotype.Service;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public void create(CreatePost dto) {
        User user =
        Post post = new Post(dto.content(), dto.imageUrl(), dto.user());
        postRepository.save(post);
    }
}
