package pro.jazzman.odmiana.entities.nouns;

import pro.jazzman.odmiana.entities.Word;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public record Noun(Mianownik mianownik, Dopelniacz dopelniacz, Celownik celownik, Biernik biernik, Narzednik narzednik, Miejscownik miejscownik, Wolacz wolacz, String translation) implements Word {

    @Override
    public String text(String highlighted) {
        var header = """
            *%s*%s
            
            """.formatted(
            mianownik.singular(),
            translation != null ? " - " + translation : ""
        );

        var words = Stream.of(
                mianownik, dopelniacz, celownik, biernik, narzednik, miejscownik, wolacz
            )
            .map(c -> {
                var singular = c.singular();
                var plural = c.plural();

                if (highlighted.toLowerCase().equals(c.singular())) {
                    singular = "*" + c.singular() + "* 👈";
                }

                if (highlighted.toLowerCase().equals(c.plural())) {
                    plural = "*" + c.plural() + "* 👈";
                }

                return "*" + c.name().charAt(0) + "*" + " (" + c.question() + "): " + singular + (!plural.isEmpty() ? " | " + plural : "");
            })
            .collect(Collectors.joining(System.lineSeparator()));

        return header + """
            __LICZBA POJEDYNCZA__ | __MNOGA__
            
            %s""".formatted(words);
    }

    public record Mianownik(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Mianownik";
        }

        @Override
        public String question() {
            return "Kto? Co?";
        }
    }
    public record Dopelniacz(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Dopełniacz";
        }

        @Override
        public String question() {
            return "Kogo? Czego?";
        }
    }
    public record Celownik(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Celownik";
        }

        @Override
        public String question() {
            return "Komu? Czemu?";
        }
    }
    public record Biernik(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Biernik";
        }

        @Override
        public String question() {
            return "Kogo? Co?";
        }
    }

    public record Narzednik(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Narzędnik";
        }

        @Override
        public String question() {
            return "Kim? Czym?";
        }
    }

    public record Miejscownik(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Miejscownik";
        }

        @Override
        public String question() {
            return "O kim? O czym?";
        }
    }

    public record Wolacz(String singular, String plural) implements Case {
        @Override
        public String name() {
            return "Wołacz";
        }

        @Override
        public String question() {
            return "O!";
        }
    }
}
