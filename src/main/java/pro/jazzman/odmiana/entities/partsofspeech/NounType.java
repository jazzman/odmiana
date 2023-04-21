package pro.jazzman.odmiana.entities.partsofspeech;

public enum NounType {
    MESKOOSOBOWY("m1"),
    MESKOZYWOTNY("m2"),
    MESKORZECZOWY("m3"),
    ZENSKI("ż"),
    NIJAKI_1("n1"),
    NIJAKI_2("n2"),
    PRZYMNOGI_1("p1"),
    PRZYMNOGI_2("p2");

    private final String abbr;

    NounType(String abbr) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }

    public String inPolish() {
        return switch (abbr) {
            case "m1"       -> "męskoosobowy";
            case "m2"       -> "męskożywotny";
            case "m3"       -> "męskorzeczowy";
            case "ż"        -> "żeński";
            case "n1", "n2" -> "nijaki";
            case "p1", "p2" -> "przymnogi";
            default         -> "";
        };
    }
}
