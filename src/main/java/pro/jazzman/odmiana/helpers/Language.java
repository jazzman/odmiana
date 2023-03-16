package pro.jazzman.odmiana.helpers;

public class Language {
    private Language() {
    }

    public static String byCode(String code) {
        return switch (code) {
            case "uk" -> "ukraiński";
            case "it" -> "włoski";
            case "de" -> "niemiecki";
            case "es" -> "hiszpański";
            case "cs" -> "czeski";
            case "da" -> "duński";
            case "nl" -> "holenderski";
            case "ja" -> "japoński";
            default -> "angielski";
        };
    }
}
