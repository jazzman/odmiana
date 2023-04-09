package pro.jazzman.odmiana.services.elements;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

@Slf4j
public class Cells extends Elements {
    private final Elements elements;

    public Cells(Elements elements) {
        this.elements = elements;
    }

    public Element cell(int index) {
        if (index < elements.size()) {
            return elements.get(index);
        }

        return null;
    }
}
