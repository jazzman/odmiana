package pro.jazzman.odmiana.parsers;

import org.jsoup.nodes.Document;
import pro.jazzman.odmiana.entities.partsofspeech.Word;

import java.io.IOException;

public interface Parser {
    Word parse(Document document) throws IOException;
}
