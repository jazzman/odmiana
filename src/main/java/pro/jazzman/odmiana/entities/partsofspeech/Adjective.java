package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import pro.jazzman.odmiana.bot.messages.AdjectiveView;

@Getter
@Setter
public class Adjective implements Word {
    private String base;

    private String singularMaleMianownik;
    private String singularMaleDopelniacz;
    private String singularMaleCelownik;
    private String singularMaleBiernik;
    private String singularMaleNarzednik;
    private String singularMaleMiejscownik;
    private String singularMaleWolacz;

    private String singularMaleNotAliveMianownik;
    private String singularMaleNotAliveDopelniacz;
    private String singularMaleNotAliveCelownik;
    private String singularMaleNotAliveBiernik;
    private String singularMaleNotAliveNarzednik;
    private String singularMaleNotAliveMiejscownik;
    private String singularMaleNotAliveWolacz;

    private String singularFemaleMianownik;
    private String singularFemaleDopelniacz;
    private String singularFemaleCelownik;
    private String singularFemaleBiernik;
    private String singularFemaleNarzednik;
    private String singularFemaleMiejscownik;
    private String singularFemaleWolacz;

    private String singularNeutralMianownik;
    private String singularNeutralDopelniacz;
    private String singularNeutralCelownik;
    private String singularNeutralBiernik;
    private String singularNeutralNarzednik;
    private String singularNeutralMiejscownik;
    private String singularNeutralWolacz;

    private String pluralMaleMianownik;
    private String pluralMaleDopelniacz;
    private String pluralMaleCelownik;
    private String pluralMaleBiernik;
    private String pluralMaleNarzednik;
    private String pluralMaleMiejscownik;
    private String pluralMaleWolacz;

    private String pluralNonMaleMianownik;
    private String pluralNonMaleDopelniacz;
    private String pluralNonMaleCelownik;
    private String pluralNonMaleBiernik;
    private String pluralNonMaleNarzednik;
    private String pluralNonMaleMiejscownik;
    private String pluralNonMaleWolacz;

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
