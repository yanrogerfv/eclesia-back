package imdl.eclesia.auth.service;

public class AccessCodeGenerator {
    public static String generateAccessCode() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder accessCode = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            int randomIndex = (int) (Math.random() * chars.length());
            accessCode.append(chars.charAt(randomIndex));
        }
        return accessCode.toString();
    }
}
