package pro.jazzman.odmiana.entities.partsofspeech;

import pro.jazzman.odmiana.bot.messages.VerbView;

public class Verb implements Word {
    private String infinitive;
    private String singularPresent1;
    private String singularPresent2;
    private String singularPresent3;
    private String singularPastMale1;
    private String singularPastMale2;
    private String singularPastMale3;
    private String singularPastFemale1;
    private String singularPastFemale2;
    private String singularPastFemale3;
    private String singularPastNeutral1;
    private String singularPastNeutral2;
    private String singularPastNeutral3;
    private String singularImperative1;
    private String singularImperative2;
    private String singularImperative3;
    private String pluralPresent1;
    private String pluralPresent2;
    private String pluralPresent3;
    private String pluralPastMale1;
    private String pluralPastMale2;
    private String pluralPastMale3;
    private String pluralPastFemale1;
    private String pluralPastFemale2;
    private String pluralPastFemale3;
    private String pluralPastNeutral1;
    private String pluralPastNeutral2;
    private String pluralPastNeutral3;
    private String pluralImperative1;
    private String pluralImperative2;
    private String pluralImperative3;

    private String translation;

    @Override
    public String message(String highlighted) {
        return new VerbView(this).render(highlighted);
    }

    @Override
    public boolean hasTranslation() {
        return translation != null;
    }

    public String getInfinitive() {
        return infinitive;
    }

    public void setInfinitive(String infinitive) {
        this.infinitive = infinitive;
    }

    public String getSingularPresent1() {
        return singularPresent1;
    }

    public void setSingularPresent1(String singularPresent1) {
        this.singularPresent1 = singularPresent1;
    }

    public String getSingularPresent2() {
        return singularPresent2;
    }

    public void setSingularPresent2(String singularPresent2) {
        this.singularPresent2 = singularPresent2;
    }

    public String getSingularPresent3() {
        return singularPresent3;
    }

    public void setSingularPresent3(String singularPresent3) {
        this.singularPresent3 = singularPresent3;
    }

    public String getSingularPastMale1() {
        return singularPastMale1;
    }

    public void setSingularPastMale1(String singularPastMale1) {
        this.singularPastMale1 = singularPastMale1;
    }

    public String getSingularPastMale2() {
        return singularPastMale2;
    }

    public void setSingularPastMale2(String singularPastMale2) {
        this.singularPastMale2 = singularPastMale2;
    }

    public String getSingularPastMale3() {
        return singularPastMale3;
    }

    public void setSingularPastMale3(String singularPastMale3) {
        this.singularPastMale3 = singularPastMale3;
    }

    public String getSingularPastFemale1() {
        return singularPastFemale1;
    }

    public void setSingularPastFemale1(String singularPastFemale1) {
        this.singularPastFemale1 = singularPastFemale1;
    }

    public String getSingularPastFemale2() {
        return singularPastFemale2;
    }

    public void setSingularPastFemale2(String singularPastFemale2) {
        this.singularPastFemale2 = singularPastFemale2;
    }

    public String getSingularPastFemale3() {
        return singularPastFemale3;
    }

    public void setSingularPastFemale3(String singularPastFemale3) {
        this.singularPastFemale3 = singularPastFemale3;
    }

    public String getSingularPastNeutral1() {
        return singularPastNeutral1;
    }

    public void setSingularPastNeutral1(String singularPastNeutral1) {
        this.singularPastNeutral1 = singularPastNeutral1;
    }

    public String getSingularPastNeutral2() {
        return singularPastNeutral2;
    }

    public void setSingularPastNeutral2(String singularPastNeutral2) {
        this.singularPastNeutral2 = singularPastNeutral2;
    }

    public String getSingularPastNeutral3() {
        return singularPastNeutral3;
    }

    public void setSingularPastNeutral3(String singularPastNeutral3) {
        this.singularPastNeutral3 = singularPastNeutral3;
    }

    public String getSingularImperative1() {
        return singularImperative1;
    }

    public void setSingularImperative1(String singularImperative1) {
        this.singularImperative1 = singularImperative1;
    }

    public String getSingularImperative2() {
        return singularImperative2;
    }

    public void setSingularImperative2(String singularImperative2) {
        this.singularImperative2 = singularImperative2;
    }

    public String getSingularImperative3() {
        return singularImperative3;
    }

    public void setSingularImperative3(String singularImperative3) {
        this.singularImperative3 = singularImperative3;
    }

    public String getPluralPresent1() {
        return pluralPresent1;
    }

    public void setPluralPresent1(String pluralPresent1) {
        this.pluralPresent1 = pluralPresent1;
    }

    public String getPluralPresent2() {
        return pluralPresent2;
    }

    public void setPluralPresent2(String pluralPresent2) {
        this.pluralPresent2 = pluralPresent2;
    }

    public String getPluralPresent3() {
        return pluralPresent3;
    }

    public void setPluralPresent3(String pluralPresent3) {
        this.pluralPresent3 = pluralPresent3;
    }

    public String getPluralPastMale1() {
        return pluralPastMale1;
    }

    public void setPluralPastMale1(String pluralPastMale1) {
        this.pluralPastMale1 = pluralPastMale1;
    }

    public String getPluralPastMale2() {
        return pluralPastMale2;
    }

    public void setPluralPastMale2(String pluralPastMale2) {
        this.pluralPastMale2 = pluralPastMale2;
    }

    public String getPluralPastMale3() {
        return pluralPastMale3;
    }

    public void setPluralPastMale3(String pluralPastMale3) {
        this.pluralPastMale3 = pluralPastMale3;
    }

    public String getPluralPastFemale1() {
        return pluralPastFemale1;
    }

    public void setPluralPastFemale1(String pluralPastFemale1) {
        this.pluralPastFemale1 = pluralPastFemale1;
    }

    public String getPluralPastFemale2() {
        return pluralPastFemale2;
    }

    public void setPluralPastFemale2(String pluralPastFemale2) {
        this.pluralPastFemale2 = pluralPastFemale2;
    }

    public String getPluralPastFemale3() {
        return pluralPastFemale3;
    }

    public void setPluralPastFemale3(String pluralPastFemale3) {
        this.pluralPastFemale3 = pluralPastFemale3;
    }

    public String getPluralPastNeutral1() {
        return pluralPastNeutral1;
    }

    public void setPluralPastNeutral1(String pluralPastNeutral1) {
        this.pluralPastNeutral1 = pluralPastNeutral1;
    }

    public String getPluralPastNeutral2() {
        return pluralPastNeutral2;
    }

    public void setPluralPastNeutral2(String pluralPastNeutral2) {
        this.pluralPastNeutral2 = pluralPastNeutral2;
    }

    public String getPluralPastNeutral3() {
        return pluralPastNeutral3;
    }

    public void setPluralPastNeutral3(String pluralPastNeutral3) {
        this.pluralPastNeutral3 = pluralPastNeutral3;
    }

    public String getPluralImperative1() {
        return pluralImperative1;
    }

    public void setPluralImperative1(String pluralImperative1) {
        this.pluralImperative1 = pluralImperative1;
    }

    public String getPluralImperative2() {
        return pluralImperative2;
    }

    public void setPluralImperative2(String pluralImperative2) {
        this.pluralImperative2 = pluralImperative2;
    }

    public String getPluralImperative3() {
        return pluralImperative3;
    }

    public void setPluralImperative3(String pluralImperative3) {
        this.pluralImperative3 = pluralImperative3;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}














