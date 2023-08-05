package pro.jazzman.odmiana.services.elements;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import org.jsoup.select.Selector;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Slf4j
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class Table {
    private final Element element;

    /**
     * Factory method for a table creation by an HTML-element.
     * @param element an HTML-element
     * @return table
     */
    public static Table from(Element element) {
        return new Table(element);
    }

    /**
     * Extracts the text from the HTML table cell by a column and row index, and a selector
     * @param rowIndex a row index
     * @param columnIndex a column index
     * @param selector cssQuery a {@link Selector} CSS-like query
     * @return the text from the HTML table cell by a column and row index, and a selector, or an empty string
     */
    @NonNull
    public String extract(int rowIndex, int columnIndex, String selector) {
        Element cell = cell(rowIndex, columnIndex);

        if (cell != null) {
            Element element = cell.selectFirst(selector);

            if (element != null) {
                return element.text();
            }
        }

        return "";
    }

    /**
     * Extracts the text from the HTML table cell by a column and a row index.
     * @param rowIndex a row index
     * @param columnIndex a column index
     * @return the text from the HTML table cell by a column and a row index, or an empty string
     */
    @NonNull
    public String extract(int rowIndex, int columnIndex) {
        Element cell = cell(rowIndex, columnIndex);

        return cell != null ? cell.text() : "";
    }

    /**
     * Returns a table cell by a column and a row index as an HTML element.
     * @param rowIndex a row index
     * @param columnIndex a column index
     * @return a table cell by a column and a row index as an HTML element, <b>{@code null}</b> if the cell is not found
     */
    @Nullable
    private Element cell(int rowIndex, int columnIndex) {
        Elements rows = element.select("tr");

        if (rowIndex < 0 || rowIndex >= rows.size()) {
            log.warn("Unable to get table row: " + rowIndex);

            return null;
        }

        Elements cells = rows.get(rowIndex).select("td");

        if (columnIndex < 0 || columnIndex >= cells.size()) {
            log.warn("Unable to get table cell with row: " + rowIndex + ", column: " + columnIndex);

            return null;
        }

        return cells.get(columnIndex);
    }
}
