package CTHH.chanstagram.Comment;

import CTHH.chanstagram.User.User;
import CTHH.chanstagram.post.Post;
import jakarta.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    private User user;

    @ManyToOne
    private Post post;

    private int likeCommentCount;

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

    public int getLikeCommentCount() {
        return likeCommentCount;
    }

    public User getUser() {
        return user;
    }

    public Post getPost() {
        return post;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    //좋아요 수 증가
    public void upLikeCommentCount() {
        this.likeCommentCount++;
    }

    //좋아요 수 감소
    public void downLikeCommentCount() {
        // likeCount -= 1;
        this.likeCommentCount--;
    }
}
