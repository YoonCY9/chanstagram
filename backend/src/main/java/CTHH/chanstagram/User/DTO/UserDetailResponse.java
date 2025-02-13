package CTHH.chanstagram.User.DTO;

import CTHH.chanstagram.User.Gender;

import java.time.LocalDate;

public record UserDetailResponse(
        String userName,
        String nickName,
        String loginId,
        String password,
        Gender gender,
        LocalDate birth,
        String content,
        String profileImage,
        String phoneNumber
) {
}
