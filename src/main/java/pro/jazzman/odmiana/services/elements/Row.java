package pro.jazzman.odmiana.services.elements;

import org.jsoup.nodes.Element;

public class Row {
    private final Element element;
    public Row(Element element) {
        this.element = element;
    }
    public Cells cells() {
        return new Cells(element.select("td"));
    }

    public Cells headerCells() {
        return new Cells(element.select("th"));
    }
}
