package pro.jazzman.odmiana.entities.verbs;

import pro.jazzman.odmiana.entities.Word;
import java.util.Arrays;

public record Verb(String infinitive, Singular singular, Plural plural, String translation) implements Word {
    public String text(String highlighted) {
        var header = """
            *%s*%s
            
            """.formatted(
            infinitive,
            translation != null ? " - " + translation : ""
        );

        var forms = new String[] {
            singular.present().first(),
            plural.present().first(),
            singular.present().second(),
            plural.present().second(),
            singular.present().third(),
            plural.present().third(),

            singular.past().getM().first(),
            plural.past().getM().first(),
            singular.past().getM().second(),
            plural.past().getM().second(),
            singular.past().getM().third(),
            plural.past().getM().third(),

            singular.past().getF().first(),
            plural.past().getFn().first(),
            singular.past().getF().second(),
            plural.past().getFn().second(),
            singular.past().getF().third(),
            plural.past().getFn().third(),

            singular.past().getN().first(),
            plural.past().getFn().first(),
            singular.past().getN().second(),
            plural.past().getFn().second(),
            singular.past().getN().third(),
            plural.past().getFn().third(),

            singular.imperative().first(),
            plural.imperative().first(),
            singular.imperative().second(),
            plural.imperative().second(),
            singular.imperative().third(),
            plural.imperative().third()
        };

        var words = Arrays.stream(forms).map(e -> {
            if (e.equals(highlighted.toLowerCase())) {
                e = "*" + e + "* 👈";
            }

            return e;
        }).toArray();

        return header + """
            __LICZBA POJEDYNCZA__ | __MNOGA__
            
            ⌛*Czas teraźniejszy*:
            
            _1 os._ - %s | %s
            _2 os._ - %s | %s
            _3 os._ - %s | %s
            
            ⌛*Czas przeszły*:
            
            🧔_1 os._ - %s | %s
            🧔‍_2 os._ - %s | %s
            🧔‍_3 os._ - %s | %s
            
            👩_1 os._ - %s | %s
            👩_2 os._ - %s | %s
            👩_3 os._ - %s | %s
            
            🍏_1 os._ - %s | %s
            🍏_2 os._ - %s | %s
            🍏_3 os._ - %s | %s
            
            *Tryb rozkazujący*:
            
            _1 os._ - %s | %s
            _2 os._ - %s | %s
            _3 os._ - %s | %s
            
            """.formatted(
            words
        );
    }
}














