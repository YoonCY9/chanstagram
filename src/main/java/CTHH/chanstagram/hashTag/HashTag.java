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

    public HashTag(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public Long getId() {
        return id;
    }

}
