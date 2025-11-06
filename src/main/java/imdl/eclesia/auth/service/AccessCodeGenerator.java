package imdl.eclesia.auth.service;

import java.security.SecureRandom;

public class AccessCodeGenerator {
    private static final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom random = new SecureRandom();
    private static final int CODE_LENGTH = 6;

    public static String generateAccessCode() {
        StringBuilder accessCode = new StringBuilder(CODE_LENGTH);
        for (int i = 0; i < CODE_LENGTH; i++) {
            int randomIndex = (random.nextInt(chars.length()));
            accessCode.append(chars.charAt(randomIndex));
        }
        return accessCode.toString();
    }
}
