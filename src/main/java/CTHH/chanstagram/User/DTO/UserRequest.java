package CTHH.chanstagram.User.DTO;

import CTHH.chanstagram.User.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserRequest(
        String userName,
        String nickName,
        String loginId,
        String password,
        Gender gender,
        LocalDate bitrh,
        String content,
        String profileImage,
        String phoneNumber
) {
}
