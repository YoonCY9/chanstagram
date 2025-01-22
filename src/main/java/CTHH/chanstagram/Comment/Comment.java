package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.post.Post;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    public Comment(String content, User user, Post post) {
        this.content = content;
        this.user = user;
        this.post = post;
    }

    protected Comment() {
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public void updateContent(String content){
        this.content=content;
    }
}
