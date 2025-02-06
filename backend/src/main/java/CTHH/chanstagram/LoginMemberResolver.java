package CTHH.chanstagram;

import CTHH.chanstagram.User.JwtProvider;
import CTHH.chanstagram.User.User;
import CTHH.chanstagram.User.UserService;
import org.springframework.stereotype.Component;

@Component
public class LoginMemberResolver {
    private static final String BEARER_PREFIX = "Bearer ";
    public static final String INVALID_TOKEN_MESSAGE = "로그인 정보가 유효하지 않습니다";

    private final JwtProvider jwtProvider;
    private final UserService userService;

    public LoginMemberResolver(JwtProvider jwtProvider, UserService userService) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
    }

    public User resolveUserFromToken(String bearerToken) {
        String token = extractToken(bearerToken);
        if (!jwtProvider.isValidToken(token)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        String loginId = jwtProvider.getSubject(token);
        return userService.findByLoginId(loginId);
    }

    private String extractToken(String bearerToken) {
        if (bearerToken == null || !bearerToken.startsWith(BEARER_PREFIX)) {
            throw new IllegalArgumentException(INVALID_TOKEN_MESSAGE);
        }
        return bearerToken.substring(BEARER_PREFIX.length());
    }
}
