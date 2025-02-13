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

    @Column(nullable = false)
    private String content;

    private int commentCount;
    private Long likeCount;

    @ElementCollection
    private List<String> imageUrl;

    @ManyToOne
    private User user;

    protected Post() {
    }

    public Post(String content, List<String> imageUrl, User user) {
        this.content = content;
        this.commentCount = 0;
        this.likeCount = 0L;
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

    public Long getLikeCount() {
        return likeCount;
    }

    public void setPost(String content) {
        if (content == null && imageUrl == null) {
            throw new IllegalStateException("수정할 내용이 없습니다.");
        }
        if (content != null) {
            this.content = content;
        }
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", commentCount=" + commentCount +
                ", likeCount=" + likeCount +
                ", imageUrl=" + imageUrl +
                ", user=" + user +
                '}';
    }

    // 댓글수 증가 함수
    public void increaseCommentCount() {
        this.commentCount = commentCount++;
    }

    //좋아요 수 증가
    public void upLikeCount(){
        this.likeCount++;
    }
    //좋아요 수 감소
    public void downLikeCount(){
        // likeCount -= 1;
        this.likeCount--;
    }
}
