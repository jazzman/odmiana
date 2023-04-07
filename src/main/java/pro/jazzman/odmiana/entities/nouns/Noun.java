package pro.jazzman.odmiana.entities.nouns;

import pro.jazzman.odmiana.entities.Word;
import pro.jazzman.odmiana.views.NounView;

public record Noun(Mianownik mianownik, Dopelniacz dopelniacz, Celownik celownik, Biernik biernik, Narzednik narzednik, Miejscownik miejscownik, Wolacz wolacz, String translation) implements Word {

    @Override
    public String text(String highlighted) {
        return new NounView().render(this, highlighted);
    }

    public boolean hasSingular() {
        return mianownik.singular != null;
    }

    public boolean hasPlural() {
        return mianownik.plural != null;
    }

    public boolean hasTranslation() {
        return translation != null;
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
