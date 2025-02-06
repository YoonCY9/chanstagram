package CTHH.chanstagram.User.DTO;

import CTHH.chanstagram.User.Gender;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDetailRequest(
        @NotNull String userName,
        @NotNull String nickName,
        @NotNull String loginId,
        @NotNull String password,
        @NotNull Gender gender,
        @NotNull LocalDate birth,
        String content,
        String profileImage,
        @NotNull String phoneNumber
) {
}
