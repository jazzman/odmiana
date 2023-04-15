package pro.jazzman.odmiana.services.elements;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Objects;

@Slf4j
public class Cells extends Elements {
    private final Elements elements;

    private static final String SANITIZE_PATTERN = "\\[\\d+]|pot.\\s";


    public Cells(Elements elements) {
        this.elements = elements;
    }

    public Element cell(int index) {
        if (index < elements.size()) {
            return elements.get(index);
        }

        return null;
    }

    public String textInCell(int index) {
        Element cell = cell(index);

        if (cell == null) {
            return "";
        }

        return cell.text().replaceAll(SANITIZE_PATTERN, "").trim();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cells elements1)) return false;
        if (!super.equals(o)) return false;

        return Objects.equals(elements, elements1.elements);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (elements != null ? elements.hashCode() : 0);
        return result;
    }
}
