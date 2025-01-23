package CTHH.chanstagram.hashTag;

import CTHH.chanstagram.post.Post;
import jakarta.persistence.*;

@Entity
public class HashTag {

    @Column(unique = true)
    private String content;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Post post;

    public HashTag(String content, Post post) {
        this.content = content;
        this.post = post;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

    public Post getPost() {
        return post;
    }
}
