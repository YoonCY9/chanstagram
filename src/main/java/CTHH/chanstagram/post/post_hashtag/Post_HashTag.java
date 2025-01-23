package CTHH.chanstagram.post.post_hashtag;

import CTHH.chanstagram.hashTag.HashTag;
import CTHH.chanstagram.post.Post;
import jakarta.persistence.*;

@Entity
public class Post_HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private HashTag hashTag;

    protected Post_HashTag() {

    }

    public Post_HashTag(HashTag hashTag, Post post) {
        this.hashTag = hashTag;
        this.post = post;
    }

    public HashTag getHashTag() {
        return hashTag;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }
}
