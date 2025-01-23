package CTHH.chanstagram.post;

import CTHH.chanstagram.hashTag.HashTag;
import jakarta.persistence.*;

import java.util.IdentityHashMap;

@Entity
public class Post_HashTag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    @ManyToOne
    private HashTag hashTag;

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
