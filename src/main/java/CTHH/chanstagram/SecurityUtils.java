package CTHH.chanstagram;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HexFormat;

// 비밀번호 해쉬화용
public class SecurityUtils {

    private static final MessageDigest SHA256;

    static {
        try {
            SHA256 = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 알고리즘을 찾을 수 없음");
        }
    }

    public static String sha256EncryptHex2(String plainText) {
        byte[] hash = SHA256.digest(plainText.getBytes());
        return HexFormat.of().formatHex(hash);
    }


}