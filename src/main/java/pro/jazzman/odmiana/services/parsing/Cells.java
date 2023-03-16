package pro.jazzman.odmiana.services.parsing;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class Cells extends Elements {
    private final Elements elements;

    public Cells(Elements elements) {
        this.elements = elements;
    }

    public Element cell(int index) {
        var element = elements.get(index);

        if (element != null) {
            return element;
        }

        log.warn("Unable to get the table cell with index: {}", index);

        return new Element("td").text("");
    }
}
