package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import pro.jazzman.odmiana.bot.messages.NounView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
public class Noun implements Word {
    private String base;
    private NounType type;

    private String singularMianownik;
    private String pluralMianownik;
    private String singularDopelniacz;
    private String pluralDopelniacz;
    private String singularCelownik;
    private String pluralCelownik;
    private String singularBiernik;
    private String pluralBiernik;
    private String singularNarzednik;
    private String pluralNarzednik;
    private String singularMiejscownik;
    private String pluralMiejscownik;
    private String singularWolacz;
    private String pluralWolacz;

    private String translation;

    @Override
    public String message() {
        return new NounView(this).render();
    }

//    public boolean hasSingular() {
//        return singularMianownik != null && !singularMianownik.isEmpty();
//    }
//
//    public boolean hasPlural() {
//        return pluralMianownik != null && !pluralMianownik.isEmpty();
//    }

    @Override
    public boolean hasTranslation() {
        return translation != null;
    }

    public List<String> singulars() {
        return Stream.of(
            singularMianownik,
            singularDopelniacz,
            singularCelownik,
            singularBiernik,
            singularNarzednik,
            singularMiejscownik,
            singularWolacz
        )
            .filter(e -> e != null && !e.isBlank())
            .collect(Collectors.toList());
    }

    public List<String> plurals() {
        return Stream.of(
            pluralMianownik,
            pluralDopelniacz,
            pluralCelownik,
            pluralBiernik,
            pluralNarzednik,
            pluralMiejscownik,
            pluralWolacz
        )
            .filter(e -> e != null && !e.isBlank())
            .collect(Collectors.toList());
    }
}
