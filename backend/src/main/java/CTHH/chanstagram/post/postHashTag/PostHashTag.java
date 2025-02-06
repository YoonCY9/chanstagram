package CTHH.chanstagram.post.postHashTag;

import CTHH.chanstagram.hashTag.HashTag;
import CTHH.chanstagram.post.Post;
import jakarta.persistence.*;

@Entity
public class PostHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "hashtag_id")
    private HashTag hashTag;

    protected PostHashTag() {

    }

    public PostHashTag(HashTag hashTag, Post post) {
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
