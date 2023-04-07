package pro.jazzman.odmiana.services.parsers;

import org.jsoup.nodes.Document;
import pro.jazzman.odmiana.entities.Word;

public interface Parser {
    Word parse(Document document, String language) throws Exception;
}
