package se.g5.itsprojgrupp5.configurations;


public class MaskingUtils {

    public static String anonymizeEmail(String email) {
        try {
            // Dela upp e-mail-adressen i användarnamn och domän
            String[] parts = email.split("@");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Ogiltig e-mail-adress.");
            }

            String username = parts[0];
            String domain = parts[1];

            // Användarenamn minst två tecken
            if (username.length() < 2) {
                throw new IllegalArgumentException("Användarnamnet i e-mail-adressen måste innehålla minst två tecken.");
            }

            // Skapa anonymiserat användarnamn
            String maskedUsername = username.charAt(0) + "*".repeat(username.length() - 2) + username.charAt(username.length() - 1);

            // Sätt ihop det anonymiserade användarnamnet med domänen
            return maskedUsername + "@" + domain;
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
