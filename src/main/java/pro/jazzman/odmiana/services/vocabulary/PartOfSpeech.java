package pro.jazzman.odmiana.services.vocabulary;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PartOfSpeech {
    VERB("czasowniki"),
    NOUN("rzeczowniki"),
    ADJECTIVE("przymiotniki");

    private final String inPolish;
}
