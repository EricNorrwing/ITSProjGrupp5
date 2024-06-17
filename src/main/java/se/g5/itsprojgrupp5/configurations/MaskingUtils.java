package se.g5.itsprojgrupp5.configurations;

import java.util.regex.Pattern;

public class MaskingUtils {

    //Stolen from the internet as regex for general email patterns
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
            Pattern.CASE_INSENSITIVE
    );

    public static String anonymizeEmail(String email) {
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            return email;
        }

            String[] parts = email.split("@");

            String username = parts[0];
            String domain = parts[1];


            String maskedUsername = username.charAt(0) + "*".repeat(username.length() - 2) + username.charAt(username.length() - 1);

            return maskedUsername + "@" + domain;

    }
}
