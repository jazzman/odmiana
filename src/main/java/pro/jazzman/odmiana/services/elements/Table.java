package pro.jazzman.odmiana.services.elements;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

@Slf4j
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

    public String extract(int rowIndex, int cellIndex, String selector) {
        Element cell = cell(rowIndex, cellIndex);

        if (cell != null) {
            Element element = cell.selectFirst(selector);

            if (element != null) {
                return element.text();
            }
        }

        return "";
    }

    public String extract(int rowIndex, int cellIndex) {
        Element cell = cell(rowIndex, cellIndex);

        return cell != null ? cell.text() : "";
    }

    private Element cell(int rowIndex, int cellIndex) {
        Elements rows = element.select("tr");

        if (rowIndex < 0 || rowIndex >= rows.size()) {
            log.warn("Unable to get table row: " + rowIndex);

            return null;
        }

        Elements cells = rows.get(rowIndex).select("td");

        if (cellIndex < 0 || cellIndex >= cells.size()) {
            log.warn("Unable to get table cell with row: " + rowIndex + ", cell: " + cellIndex);

            return null;
        }

        return cells.get(cellIndex);
    }
}
