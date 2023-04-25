package pro.jazzman.odmiana.services.vocabulary;

import jakarta.ws.rs.NotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.parsers.AdjectiveParser;
import pro.jazzman.odmiana.parsers.NounParser;
import pro.jazzman.odmiana.parsers.Parser;
import pro.jazzman.odmiana.parsers.VerbParser;

import java.io.IOException;

@Service
public class WSJP {
    private final String url;

    public WSJP(@Value("${wsjp.url}") final String url) {
        this.url = url;
    }

    public Word get(String query) throws IOException {
        Element firstResult = firstResultFor(query);
        Element firstMeaning = firstMeaningFor(firstResult);

        Document document = target(firstMeaning);

        return parser(document).parse();
    }

    private Element firstResultFor(String query) throws IOException {
        Element result = document(url + "/szukaj/podstawowe/wyniki?szukaj=" + query)
            .selectFirst(".search_list li:eq(0) a[href]");

        if (result == null) {
            throw new NotFoundException("Cannot find results for the word: '" + query + "'");
        }

        return result;
    }

    private Element firstMeaningFor(Element word) throws IOException {
        Element meaning = document(url + word.attr("href"))
            .selectFirst("ul.meanings li:eq(0) a[href]");

        if (meaning == null) {
            throw new NotFoundException("Cannot find meanings for the word: '" + word.text() + "'");
        }

        return meaning;
    }

    private Document target(Element word) throws IOException {
        return document(word.attr("href"));
    }

    private Parser parser(Document document) throws IOException {
        String partOfSpeech = partOfSpeech(document);

        return switch (partOfSpeech) {
            case "czasownik" -> new VerbParser(document);
            case "rzeczownik" -> new NounParser(document);
            case "przymiotnik" -> new AdjectiveParser(document);
            default -> throw new IOException("Cannot choose appropriate parser for the part of speech: " + partOfSpeech);
        };
    }

    private String partOfSpeech(Document document) throws IOException {
        Element p = document.selectFirst("p:containsOwn(część mowy:)");

        if (p != null) {
            Element em = p.selectFirst("em");

            if (em != null) {
                return em.text();
            }
        }

        throw new IOException("Cannot resolve the part of speech for the word");
    }

    private Document document(String url) throws IOException {
        return Jsoup.connect(url).get();
    }
}
