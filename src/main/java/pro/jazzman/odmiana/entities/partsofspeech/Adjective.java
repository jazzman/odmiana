package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import pro.jazzman.odmiana.bot.messages.AdjectiveView;

@Getter
@Setter
public class Adjective implements Word {
    private String singularMaleMianownik;
    private String singularMaleDopelniacz;
    private String singularMaleCelownik;
    private String singularMaleAliveBiernik;
    private String singularMaleNotAliveBiernik;
    private String singularMaleNarzednik;
    private String singularMaleMiejscownik;
    private String singularMaleWolac;

    private String singularFemaleMianownik;
    private String singularFemaleDopelniacz;
    private String singularFemaleCelownik;
    private String singularFemaleBiernik;
    private String singularFemaleNarzednik;
    private String singularFemaleMiejscownik;
    private String singularFemaleWolac;

    private String singularNeutralMianownik;
    private String singularNeutralDopelniacz;
    private String singularNeutralCelownik;
    private String singularNeutralBiernik;
    private String singularNeutralNarzednik;
    private String singularNeutralMiejscownik;
    private String singularNeutralWolac;

    private String pluralMaleMianownik;
    private String pluralDopelniacz;
    private String pluralCelownik;
    private String pluralMaleBiernik;
    private String pluralNarzednik;
    private String pluralMiejscownik;
    private String pluralMaleWolac;

    private String pluralNonMaleMianownik;
    private String pluralNonMaleBiernik;
    private String pluralNonMaleWolac;

    private String translation;

    @Override
    public String message() {
        return new AdjectiveView(this).render();
    }

    @Override
    public void setTranslation(String translation) {
        this.translation = translation;
    }

    @Override
    public boolean hasTranslation() {
        return translation != null;
    }
}
