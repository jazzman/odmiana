package pro.jazzman.odmiana.services.parsing;

import org.jsoup.select.Elements;
import java.io.IOException;

public class Rows extends Elements {
    private final Elements elements;

    public Rows(Elements elements) {
        this.elements = elements;
    }

    public Row row(int index) throws Exception {
        var element = elements.get(index);

        if (element == null) {
            throw new IOException("Unable to get the table row with index: " + index);
        }

        return new Row(element);
    }
}
