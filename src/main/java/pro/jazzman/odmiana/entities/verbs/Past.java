package pro.jazzman.odmiana.entities.verbs;

public class Past {

    private Masculine masculine;
    private Feminine feminine;
    private Neutral neutral;
    private FeminineAndNeutral feminineAndNeutral;

    public Past(Masculine masculine, Feminine feminine, Neutral neutral) {
        this.masculine = masculine;
        this.feminine = feminine;
        this.neutral = neutral;
    }

    public Past(Masculine masculine, FeminineAndNeutral feminineAndNeutral) {
        this.masculine = masculine;
        this.feminineAndNeutral = feminineAndNeutral;
    }

    public Masculine getM() {
        return masculine;
    }

    public void setM(Masculine masculine) {
        this.masculine = masculine;
    }

    public Feminine getF() {
        return feminine;
    }

    public void setF(Feminine feminine) {
        this.feminine = feminine;
    }

    public Neutral getN() {
        return neutral;
    }

    public void setN(Neutral neutral) {
        this.neutral = neutral;
    }

    public FeminineAndNeutral getFn() {
        return feminineAndNeutral;
    }

    public void setFn(FeminineAndNeutral feminineAndNeutral) {
        this.feminineAndNeutral = feminineAndNeutral;
    }
}
