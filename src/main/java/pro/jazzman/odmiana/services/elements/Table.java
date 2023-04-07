package pro.jazzman.odmiana.services.elements;

import org.jsoup.nodes.Element;

public class Table {
    private final Element element;

    public static Table from(Element element) {
        return new Table(element);
    }

    public Table(Element element) {
        this.element = element;
    }

    public Rows rows() {
        return new Rows(element.select("tr"));
    }
}
