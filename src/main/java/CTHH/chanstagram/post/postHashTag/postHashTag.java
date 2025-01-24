package CTHH.chanstagram.post.postHashTag;

import CTHH.chanstagram.hashTag.HashTag;
import CTHH.chanstagram.post.Post;
import jakarta.persistence.*;

@Entity
public class postHashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private HashTag hashTag;

    protected postHashTag() {

    }

    public postHashTag(HashTag hashTag, Post post) {
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
