package CTHH.chanstagram.post;

import CTHH.chanstagram.BaseEntity;
import CTHH.chanstagram.User.User;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    private int commentCount;

    @ElementCollection
    private List<String> imageUrl;

    @ManyToOne
    private User user;

    protected Post() {
    }

    public Post(String content, List<String> imageUrl, User user) {
        this.content = content;
        this.commentCount = 0;
        this.imageUrl = imageUrl;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public List<String> getImageUrl() {
        return imageUrl;
    }

    public User getUser() {
        return user;
    }

    public void setPost(String content, List<String> imageUrl) {
        if (content == null && imageUrl == null) {
            throw new IllegalStateException("수정할 내용이 없습니다.");
        }
        if (content != null) {
            this.content = content;
        }
        if (imageUrl != null) {
            this.imageUrl = imageUrl;
        }
    }
}
