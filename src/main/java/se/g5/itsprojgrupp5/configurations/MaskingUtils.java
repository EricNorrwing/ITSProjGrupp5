package se.g5.itsprojgrupp5.configurations;


public class MaskingUtils {

    public static String anonymizeEmail(String email) {

        // Dela upp e-mail-adressen i användarnamn och domän
        String[] parts = email.split("@");

        String username = parts[0];
        String domain = parts[1];

        // Skapa anonymiserat användarnamn
        String maskedUsername = username.charAt(0) + "*".repeat(username.length() - 2) + username.charAt(username.length() - 1);

        // Sätt ihop det anonymiserade användarnamnet med domänen
        return maskedUsername + "@" + domain;

    }
}