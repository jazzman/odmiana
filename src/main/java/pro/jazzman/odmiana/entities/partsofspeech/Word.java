package pro.jazzman.odmiana.entities.partsofspeech;

public interface Word {
    String message(String highlighted);

    void setTranslation(String translation);

    boolean hasTranslation();
}
