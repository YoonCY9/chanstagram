package CTHH.chanstagram.User.DTO;

import CTHH.chanstagram.User.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDetailRequest(
        @NotNull String userName,
        @NotNull String nickName,
        @NotNull String loginId,
        @NotNull String password,
        @NotNull Gender gender,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate birth,
        String content,
        String profileImage,
        @NotNull String phoneNumber
) {
}
