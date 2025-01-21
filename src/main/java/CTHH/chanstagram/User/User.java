package CTHH.chanstagram.User;

import CTHH.chanstagram.BaseEntity;
import jakarta.persistence.*;
import org.hibernate.annotations.GeneratedColumn;

import java.time.LocalDate;

@Entity
@Table(name = "users")
public class User extends BaseEntity {

    @Column(nullable = false)
    private String userName;

    @Column(unique = true, nullable = false)
    private String nickName;

    @Id
    @Column(unique = true, nullable = false)
    private String loginId;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;

    @Column(nullable = false)
    private LocalDate birth;

    private String content;

    private String profileImage;

    @Column(unique = true, nullable = false)
    private String phoneNumber;

    protected User() {
    }

    public User(String userName, String nickName, String loginId, String password, Gender gender, LocalDate birth, String content, String profileImage, String phoneNumber) {
        this.userName = userName;
        this.nickName = nickName;
        this.loginId = loginId;
        this.password = password;
        this.gender = gender;
        this.birth = birth;
        this.content = content;
        this.profileImage = profileImage;
        this.phoneNumber = phoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLoginId() {
        return loginId;
    }

    public String getPassword() {
        return password;
    }

    public Gender getGender() {
        return gender;
    }

    public LocalDate getBirth() {
        return birth;
    }

    public String getContent() {
        return content;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
