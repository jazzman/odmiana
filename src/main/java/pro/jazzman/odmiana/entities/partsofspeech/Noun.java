package pro.jazzman.odmiana.entities.partsofspeech;

import pro.jazzman.odmiana.bot.messages.NounView;

public class Noun implements Word {
    private String mianownikSingular;
    private String mianownikPlural;
    private String dopelniaczSingular;
    private String dopelniaczPlural;
    private String celownikSingular;
    private String celownikPlural;
    private String biernikSingular;
    private String biernikPlural;
    private String narzednikSingular;
    private String narzednikPlural;
    private String miejscownikSingular;
    private String miejscownikPlural;
    private String wolaczSingular;
    private String wolaczPlural;

    private String translation;

    @Override
    public String message(String highlighted) {
        return new NounView(this).render(highlighted);
    }

    public boolean hasSingular() {
        return mianownikSingular != null && !mianownikSingular.isEmpty();
    }

    public boolean hasPlural() {
        return mianownikPlural != null && !mianownikPlural.isEmpty();
    }

    @Override
    public boolean hasTranslation() {
        return translation != null;
    }

    public String getMianownikSingular() {
        return mianownikSingular;
    }

    public void setMianownikSingular(String mianownikSingular) {
        this.mianownikSingular = mianownikSingular;
    }

    public String getMianownikPlural() {
        return mianownikPlural;
    }

    public void setMianownikPlural(String mianownikPlural) {
        this.mianownikPlural = mianownikPlural;
    }

    public String getDopelniaczSingular() {
        return dopelniaczSingular;
    }

    public void setDopelniaczSingular(String dopelniaczSingular) {
        this.dopelniaczSingular = dopelniaczSingular;
    }

    public String getDopelniaczPlural() {
        return dopelniaczPlural;
    }

    public void setDopelniaczPlural(String dopelniaczPlural) {
        this.dopelniaczPlural = dopelniaczPlural;
    }

    public String getCelownikSingular() {
        return celownikSingular;
    }

    public void setCelownikSingular(String celownikSingular) {
        this.celownikSingular = celownikSingular;
    }

    public String getCelownikPlural() {
        return celownikPlural;
    }

    public void setCelownikPlural(String celownikPlural) {
        this.celownikPlural = celownikPlural;
    }

    public String getBiernikSingular() {
        return biernikSingular;
    }

    public void setBiernikSingular(String biernikSingular) {
        this.biernikSingular = biernikSingular;
    }

    public String getBiernikPlural() {
        return biernikPlural;
    }

    public void setBiernikPlural(String biernikPlural) {
        this.biernikPlural = biernikPlural;
    }

    public String getNarzednikSingular() {
        return narzednikSingular;
    }

    public void setNarzednikSingular(String narzednikSingular) {
        this.narzednikSingular = narzednikSingular;
    }

    public String getNarzednikPlural() {
        return narzednikPlural;
    }

    public void setNarzednikPlural(String narzednikPlural) {
        this.narzednikPlural = narzednikPlural;
    }

    public String getMiejscownikSingular() {
        return miejscownikSingular;
    }

    public void setMiejscownikSingular(String miejscownikSingular) {
        this.miejscownikSingular = miejscownikSingular;
    }

    public String getMiejscownikPlural() {
        return miejscownikPlural;
    }

    public void setMiejscownikPlural(String miejscownikPlural) {
        this.miejscownikPlural = miejscownikPlural;
    }

    public String getWolaczSingular() {
        return wolaczSingular;
    }

    public void setWolaczSingular(String wolaczSingular) {
        this.wolaczSingular = wolaczSingular;
    }

    public String getWolaczPlural() {
        return wolaczPlural;
    }

    public void setWolaczPlural(String wolaczPlural) {
        this.wolaczPlural = wolaczPlural;
    }

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }
}
