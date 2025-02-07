package CTHH.chanstagram.User.DTO;

import CTHH.chanstagram.User.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record UserDetailRequest(
        @NotBlank String userName,
        @NotBlank String nickName,
        @NotBlank String loginId,
        @NotBlank String password,
        @NotNull Gender gender,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") LocalDate birth,
        String content,
        String profileImage,
        @NotBlank String phoneNumber
) {
}
