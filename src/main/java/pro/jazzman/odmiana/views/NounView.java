package pro.jazzman.odmiana.views;

import org.apache.commons.text.StringSubstitutor;
import pro.jazzman.odmiana.entities.nouns.Noun;
import java.util.HashMap;

public class NounView {
    private final String template = """
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

    private final String templateForSingular = """
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

    private final String templateForPlural = """
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

    public String render(Noun noun, String higlighted) {
        var placeholders = new HashMap<String, String>() {{
            put("translation", noun.hasTranslation() ? " - " + noun.translation() : "");
        }};

        if (noun.hasSingular()) {
            placeholders.putAll(new HashMap<>() {{
                put("mianownik.singular", noun.mianownik().singular());
                put("dopelniacz.singular", noun.dopelniacz().singular());
                put("celownik.singular", noun.celownik().singular());
                put("biernik.singular", noun.biernik().singular());
                put("narzednik.singular", noun.narzednik().singular());
                put("miejscownik.singular", noun.miejscownik().singular());
                put("wolacz.singular", noun.wolacz().singular());
            }});
        }

        if (noun.hasPlural()) {
            placeholders.putAll(new HashMap<>() {{
                put("mianownik.plural", noun.mianownik().plural());
                put("dopelniacz.plural", noun.dopelniacz().plural());
                put("celownik.plural", noun.celownik().plural());
                put("biernik.plural", noun.biernik().plural());
                put("narzednik.plural", noun.narzednik().plural());
                put("miejscownik.plural", noun.miejscownik().plural());
                put("wolacz.plural", noun.wolacz().plural());
            }});
        }

        placeholders.replaceAll((k, v) -> v.equals(higlighted) ? "*" + v + "* 👈" : v);

        if (noun.hasSingular() && noun.hasPlural()) {
            placeholders.put("mianownik", noun.mianownik().singular());

            return StringSubstitutor.replace(template, placeholders);
        }

        if (noun.hasSingular()) {
            placeholders.put("mianownik", noun.mianownik().singular());

            return StringSubstitutor.replace(templateForSingular, placeholders);
        }

        if (noun.hasPlural()) {
            placeholders.put("mianownik", noun.mianownik().plural());

            return StringSubstitutor.replace(templateForPlural, placeholders);
        }

        throw new RuntimeException("Unable to render a message");
    }
}
