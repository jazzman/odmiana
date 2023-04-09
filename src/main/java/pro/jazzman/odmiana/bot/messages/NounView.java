package pro.jazzman.odmiana.bot.messages;

import lombok.AllArgsConstructor;
import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.ApplicationRuntimeException;
import pro.jazzman.odmiana.entities.partsofspeech.Noun;
import java.util.HashMap;

@AllArgsConstructor
public class NounView implements View {
    private static final String MIANOWNIK = "mianownik";
    private static final String TEMPLATE = """
        *${mianownik}*${translation}
            
        *Liczba Pojedyncza* | *Mnoga*
        
        *M* (Kto? Co?): ${mianownik.singular} | ${mianownik.plural}
        *D* (Kogo? Czego?): ${dopelniacz.singular} | ${dopelniacz.plural}
        *C* (Komu? Czemu?): ${celownik.singular} | ${celownik.plural}
        *B* (Kogo? Co?): ${biernik.singular} | ${biernik.plural}
        *N* (Kim? Czym?): ${narzednik.singular} | ${narzednik.plural}
        *M* (O kim? O czym?): ${miejscownik.singular} | ${miejscownik.plural}
        *W* (O!): ${wolacz.singular} | ${wolacz.plural}
        """;

    private static final String templateForSingular = """
        *${mianownik}*${translation}
            
        *Liczba Pojedyncza*
        
        *M* (Kto? Co?): ${mianownik.singular}
        *D* (Kogo? Czego?): ${dopelniacz.singular}
        *C* (Komu? Czemu?): ${celownik.singular}
        *B* (Kogo? Co?): ${biernik.singular}
        *N* (Kim? Czym?): ${narzednik.singular}
        *M* (O kim? O czym?): ${miejscownik.singular}
        *W* (O!): ${wolacz.singular}
        """;

    private static final String templateForPlural = """
        *${mianownik}*${translation}
            
        *Liczba Mnoga*
        
        *M* (Kto? Co?): ${mianownik.plural}
        *D* (Kogo? Czego?): ${dopelniacz.plural}
        *C* (Komu? Czemu?): ${celownik.plural}
        *B* (Kogo? Co?): ${biernik.plural}
        *N* (Kim? Czym?): ${narzednik.plural}
        *M* (O kim? O czym?): ${miejscownik.plural}
        *W* (O!): ${wolacz.plural}
        """;

    private final Noun noun;

    public String render(String higlighted) {
        var placeholders = new HashMap<String, String>();
        placeholders.put("translation", noun.hasTranslation() ? " - " + noun.getTranslation() : "");

        if (noun.hasSingular()) {
            placeholders.put("mianownik.singular", noun.getMianownikSingular());
            placeholders.put("dopelniacz.singular", noun.getDopelniaczSingular());
            placeholders.put("celownik.singular", noun.getCelownikSingular());
            placeholders.put("biernik.singular", noun.getBiernikSingular());
            placeholders.put("narzednik.singular", noun.getNarzednikSingular());
            placeholders.put("miejscownik.singular", noun.getMiejscownikSingular());
            placeholders.put("wolacz.singular", noun.getWolaczSingular());
        }

        if (noun.hasPlural()) {
            placeholders.put("mianownik.plural", noun.getMianownikPlural());
            placeholders.put("dopelniacz.plural", noun.getDopelniaczPlural());
            placeholders.put("celownik.plural", noun.getCelownikPlural());
            placeholders.put("biernik.plural", noun.getBiernikPlural());
            placeholders.put("narzednik.plural", noun.getNarzednikPlural());
            placeholders.put("miejscownik.plural", noun.getMiejscownikPlural());
            placeholders.put("wolacz.plural", noun.getWolaczPlural());
        }

        placeholders.replaceAll((k, v) -> v.equals(higlighted) ? "*" + v + "* 👈" : v);

        placeholders.put(MIANOWNIK, noun.getMianownikSingular());

        if (noun.hasSingular() && noun.hasPlural()) {
            return StringSubstitutor.replace(TEMPLATE, placeholders);
        }

        if (noun.hasSingular()) {
            return StringSubstitutor.replace(templateForSingular, placeholders);
        }

        if (noun.hasPlural()) {
            placeholders.put(MIANOWNIK, noun.getMianownikPlural());

            return StringSubstitutor.replace(templateForPlural, placeholders);
        }

        throw new ApplicationRuntimeException("Unable to render a message");
    }
}
