package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.partsofspeech.Adjective;

import java.util.HashMap;

@AllArgsConstructor
public class AdjectiveView implements View {
    private static final String TEMPLATE = """
        *${mianownik.singular.male}*${translation}
            
        *Liczba Pojedyncza*
        
        🧔*M* (Kto? Co?): ${mianownik.singular.male}
        🧔*D* (Kogo? Czego?): ${dopelniacz.singular.male}
        🧔*C* (Komu? Czemu?): ${celownik.singular.male}
        🧔*B* (Kogo? Co?): ${biernik.singular.male.alive} | 🏠${biernik.singular.male.notalive}
        🧔*N* (Kim? Czym?): ${narzednik.singular.male}
        🧔*M* (O kim? O czym?): ${miejscownik.singular.male}
        🧔*W* (O!): ${wolacz.singular.male}
        
        👩*M* (Kto? Co?): ${mianownik.singular.female}
        👩*D* (Kogo? Czego?): ${dopelniacz.singular.female}
        👩*C* (Komu? Czemu?): ${celownik.singular.female}
        👩*B* (Kogo? Co?): ${biernik.singular.female}
        👩*N* (Kim? Czym?): ${narzednik.singular.female}
        👩*M* (O kim? O czym?): ${miejscownik.singular.female}
        👩*W* (O!): ${wolacz.singular.female}
        
        🍏*M* (Kto? Co?): ${mianownik.singular.neutral}
        🍏*D* (Kogo? Czego?): ${dopelniacz.singular.neutral}
        🍏*C* (Komu? Czemu?): ${celownik.singular.neutral}
        🍏*B* (Kogo? Co?): ${biernik.singular.neutral}
        🍏*N* (Kim? Czym?): ${narzednik.singular.neutral}
        🍏*M* (O kim? O czym?): ${miejscownik.singular.neutral}
        🍏*W* (O!): ${wolacz.singular.neutral}
        
        *Liczba Mnoga*
        
        *M* (Kto? Co?): 🧔${mianownik.plural.male} | 🙅‍${mianownik.plural.nonmale}
        *D* (Kogo? Czego?): ${dopelniacz.plural}
        *C* (Komu? Czemu?): ${celownik.plural}
        *B* (Kogo? Co?): 🧔${biernik.plural.male} | 🙅‍${biernik.plural.nonmale}
        *N* (Kim? Czym?): ${narzednik.plural}
        *M* (O kim? O czym?): ${miejscownik.plural}
        *W* (O!): 🧔${wolacz.plural.male} | 🙅‍${wolacz.plural.nonmale}
        
        """;
    private Adjective adjective;

    public String render(String higlighted) {
        var placeholders = new HashMap<String, String>();

        placeholders.put("translation", adjective.hasTranslation() ? " - " + adjective.getTranslation() : "");

        placeholders.put("mianownik.singular.male", adjective.getSingularMaleMianownik());
        placeholders.put("dopelniacz.singular.male", adjective.getSingularMaleDopelniacz());
        placeholders.put("celownik.singular.male", adjective.getSingularMaleCelownik());
        placeholders.put("biernik.singular.male.alive", adjective.getSingularMaleAliveBiernik());
        placeholders.put("biernik.singular.male.notalive", adjective.getSingularMaleNotAliveBiernik());
        placeholders.put("narzednik.singular.male", adjective.getSingularMaleNarzednik());
        placeholders.put("miejscownik.singular.male", adjective.getSingularMaleMiejscownik());
        placeholders.put("wolac.singular.male", adjective.getSingularMaleWolac());

        placeholders.put("mianownik.singular.female", adjective.getSingularFemaleMianownik());
        placeholders.put("dopelniacz.singular.female", adjective.getSingularFemaleDopelniacz());
        placeholders.put("celownik.singular.female", adjective.getSingularFemaleCelownik());
        placeholders.put("biernik.singular.female", adjective.getSingularFemaleBiernik());
        placeholders.put("narzednik.singular.female", adjective.getSingularFemaleNarzednik());
        placeholders.put("miejscownik.singular.female", adjective.getSingularFemaleMiejscownik());
        placeholders.put("wolac.singular.female", adjective.getSingularFemaleWolac());

        placeholders.put("mianownik.singular.neutral", adjective.getSingularNeutralMianownik());
        placeholders.put("dopelniacz.singular.neutral", adjective.getSingularNeutralDopelniacz());
        placeholders.put("celownik.singular.neutral", adjective.getSingularNeutralCelownik());
        placeholders.put("biernik.singular.neutral", adjective.getSingularNeutralBiernik());
        placeholders.put("narzednik.singular.neutral", adjective.getSingularNeutralNarzednik());
        placeholders.put("miejscownik.singular.neutral", adjective.getSingularNeutralMiejscownik());
        placeholders.put("wolac.singular.neutral", adjective.getSingularNeutralWolac());

        placeholders.put("mianownik.plural.male", adjective.getPluralMaleMianownik());
        placeholders.put("mianownik.plural.nonmale", adjective.getPluralNonMaleMianownik());
        placeholders.put("dopelniacz.plural", adjective.getPluralDopelniacz());
        placeholders.put("celownik.plural", adjective.getPluralCelownik());
        placeholders.put("biernik.plural.male", adjective.getPluralMaleBiernik());
        placeholders.put("biernik.plural.nonmale", adjective.getPluralNonMaleBiernik());
        placeholders.put("narzednik.plural", adjective.getPluralNarzednik());
        placeholders.put("miejscownik.plural", adjective.getPluralMiejscownik());
        placeholders.put("wolacz.plural.male", adjective.getPluralMaleWolac());
        placeholders.put("wolacz.plural.nonmale", adjective.getPluralNonMaleWolac());

        placeholders.replaceAll(
            (k, v) -> v != null && v.equals(higlighted) && !v.equals(adjective.getSingularMaleMianownik()) ? "*" + v + "* 👈" : v
        );

        return StringSubstitutor.replace(TEMPLATE, placeholders);
    }
}
