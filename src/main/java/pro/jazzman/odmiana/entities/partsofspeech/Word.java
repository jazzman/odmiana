package pro.jazzman.odmiana.entities.partsofspeech;

public interface Word {
    String message();

    void setTranslation(String translation);

    boolean hasTranslation();
}
