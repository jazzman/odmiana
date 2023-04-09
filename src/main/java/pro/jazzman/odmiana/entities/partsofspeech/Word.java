package pro.jazzman.odmiana.entities.partsofspeech;

public interface Word {
    String message(String highlighted);

    void addTranslation(String translation);

    boolean hasTranslation();
}
