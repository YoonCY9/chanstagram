package CTHH.chanstagram.User.DTO;

import CTHH.chanstagram.User.Gender;

import java.time.LocalDate;

public record UserRequest(
        String userName,
        String nickName,
        Gender gender,
        LocalDate birth,
        String content,
        String profileImage,
        String phoneNumber
) {
}
