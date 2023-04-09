package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import pro.jazzman.odmiana.bot.messages.NounView;

@Getter
@Setter
public class Noun implements Word {
    private String mianownikSingular;
    private String mianownikPlural;
    private String dopelniaczSingular;
    private String dopelniaczPlural;
    private String celownikSingular;
    private String celownikPlural;
    private String biernikSingular;
    private String biernikPlural;
    private String narzednikSingular;
    private String narzednikPlural;
    private String miejscownikSingular;
    private String miejscownikPlural;
    private String wolaczSingular;
    private String wolaczPlural;

    private String translation;

    @Override
    public String message(String highlighted) {
        return new NounView(this).render(highlighted);
    }

    public boolean hasSingular() {
        return mianownikSingular != null && !mianownikSingular.isEmpty();
    }

    public boolean hasPlural() {
        return mianownikPlural != null && !mianownikPlural.isEmpty();
    }

    @Override
    public boolean hasTranslation() {
        return translation != null;
    }
}
