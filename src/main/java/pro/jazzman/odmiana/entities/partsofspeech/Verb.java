package pro.jazzman.odmiana.entities.partsofspeech;

import lombok.Getter;
import lombok.Setter;
import pro.jazzman.odmiana.bot.messages.VerbView;

@Getter
@Setter
public class Verb implements Word {
    private String infinitive;

    private String singularPresent1;
    private String singularPresent2;
    private String singularPresent3;
    private String pluralPresent1;
    private String pluralPresent2;
    private String pluralPresent3;

    private String singularPastMale1;
    private String singularPastMale2;
    private String singularPastMale3;
    private String pluralPastMale1;
    private String pluralPastMale2;
    private String pluralPastMale3;

    private String singularPastFemale1;
    private String singularPastFemale2;
    private String singularPastFemale3;
    private String pluralPastFemale1;
    private String pluralPastFemale2;
    private String pluralPastFemale3;

    private String singularPastNeutral1;
    private String singularPastNeutral2;
    private String singularPastNeutral3;
    private String pluralPastNeutral1;
    private String pluralPastNeutral2;
    private String pluralPastNeutral3;

    private String singularFutureMale1;
    private String singularFutureMale2;
    private String singularFutureMale3;
    private String pluralFutureMale1;
    private String pluralFutureMale2;
    private String pluralFutureMale3;

    private String singularFutureFemale1;
    private String singularFutureFemale2;
    private String singularFutureFemale3;

    private String singularFutureNeutral1;
    private String singularFutureNeutral2;
    private String singularFutureNeutral3;

    private String pluralFutureNonMale1;
    private String pluralFutureNonMale2;
    private String pluralFutureNonMale3;

    private String singularImperative2;
    private String pluralImperative1;
    private String pluralImperative2;

    private String impersonal;
    private String gerund;

    private String modernAdverbialParticiple;
    private String activeParticiple;
    private String passiveAdjectiveParticiple;

    private String translation;

    @Override
    public String message() {
        return new VerbView(this).render();
    }

    @Override
    public boolean hasTranslation() {
        return translation != null;
    }
}














