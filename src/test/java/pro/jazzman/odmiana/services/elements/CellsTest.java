package pro.jazzman.odmiana.services.elements;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class CellsTest {
    private Cells cells;

    private final Element cell1 = Jsoup.parse("<td class=\"forma\"><a href=\"/wiki/biernik#pl\" title=\"biernik\">biernik</a></td>");
    private final Element cell2 = Jsoup.parse("<td>banan / \n <link rel=\"mw-deduplicated-inline-style\" href=\"mw-data:TemplateStyles:r7574220\"><span class=\"short-container\"><a href=\"/wiki/Aneks:Skr%C3%B3ty_u%C5%BCywane_w_Wikis%C5%82owniku#P\" class=\"mw-redirect\" title=\"Aneks:Skróty używane w Wikisłowniku\"><span class=\"short-wrapper\" title=\"potocznie, potoczny\" data-expanded=\"potocznie, potoczny\"><span class=\"short-content\">pot.</span></span></a></span> banana<sup id=\"cite_ref-4\" class=\"reference\"><a href=\"#cite_note-4\">[4]</a></sup></td>");
    private final Element cell3 = Jsoup.parse("<td>banany</td>");

    @BeforeEach
    void setUp() {
        cells = new Cells(
            new Elements(cell1, cell2, cell3)
        );
    }

    @Test
    @DisplayName("Returns a cell by index")
    void cell() {
        assertThat(cells.cell(2)).isEqualTo(cell3);
    }

    @Test
    @DisplayName("Returns null on the missing cell")
    void cellMissingCellReturnsNull() {
        assertThat(cells.cell(5)).isNull();
    }

    @Test
    @DisplayName("Extracts a sanitized cell content")
    void textInCell() {
        assertThat(cells.textInCell(1)).isEqualTo("banan / banana");
    }

    @Test
    @DisplayName("Returns an empty string when cell is missing")
    void textInCellMissingCellReturnsEmptyString() {
        assertThat(cells.textInCell(5)).isEqualTo("");
    }

    @Test
    @DisplayName("Test for cells equality")
    void equals() {
        assertThat(new Cells(new Elements(cell1, cell2, cell3))).isEqualTo(cells);
    }
}