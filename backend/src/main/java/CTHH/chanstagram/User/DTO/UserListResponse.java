package CTHH.chanstagram.User.DTO;

import java.util.List;

public record UserListResponse(
        List<UserResponse> userResponses
) {
}
