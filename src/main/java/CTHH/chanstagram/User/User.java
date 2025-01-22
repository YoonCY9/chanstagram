package CTHH.chanstagram.User;

import CTHH.chanstagram.BaseEntity;
import CTHH.chanstagram.SecurityUtils;
import jakarta.persistence.*;
import org.hibernate.annotations.GeneratedColumn;

import java.time.LocalDate;
import java.util.NoSuchElementException;

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

    public User(String userName, String nickName, String loginId, String password, Gender gender,
                LocalDate birth, String content, String profileImage, String phoneNumber) {
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

    //입력 받은 비밀번호와 저장된 비밀번호 조회
    public void findByPassword(String password) {
        if (!this.getPassword().equals(SecurityUtils.sha256EncryptHex2(password))) {
            throw new NoSuchElementException("ID 혹은 비밀번호가 틀립니다.");
        }
    }

    public void updateUserName(String userName) {
        this.userName = userName;
    }

    public void updateNickName(String nickName) {
        this.nickName = nickName;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateBirth(LocalDate birth) {
        this.birth = birth;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
