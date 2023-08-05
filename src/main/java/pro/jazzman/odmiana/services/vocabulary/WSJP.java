package pro.jazzman.odmiana.services.vocabulary;

import jakarta.ws.rs.NotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.partsofspeech.Word;
import pro.jazzman.odmiana.exceptions.ApplicationRuntimeException;
import pro.jazzman.odmiana.parsers.AdjectiveParser;
import pro.jazzman.odmiana.parsers.NounParser;
import pro.jazzman.odmiana.parsers.Parser;
import pro.jazzman.odmiana.parsers.VerbParser;

import java.io.IOException;

@Service
public class WSJP {
    private final String baseUrl;

    @Autowired
    private BeanFactory beanFactory;

    public WSJP(@Value("${wsjp.url}") final String baseUrl) {
        this.baseUrl = baseUrl;
    }

    /**
     * Returns all the forms of the word.
     * In general parsing looks like the following:
     * 1. Send the word -> opens the page with search results
     * 2. Click on the first of them -> opens the page with different meanings
     * 3. Click on the first meaning -> opens the page with grammar tables
     * 4. Parses HTML and fills in the word object with different forms
     * For some words there is no "meanings" page. In that case we navigate to the grammar page right from the search results
     *
     * @param query word to search by
     * @return an object that represent a word with all its forms
     * @throws IOException thrown if there is a problem with the retrieving the content from the web page
     */
    public Word get(String query) throws IOException {
        Element searchResult = firstResultBy(query);

        if (searchResult == null) {
            throw new NotFoundException("Cannot find results for the word: '" + query + "'");
        }

        Element firstMeaning = firstMeaningFor(searchResult);

        Document document = target(firstMeaning != null ? firstMeaning : searchResult);

        return parser(partOfSpeech(document)).parse(document);
    }

    @Nullable
    private Element firstResultBy(String query) throws IOException {
        return document(normalizedUrl("/szukaj/podstawowe/wyniki?szukaj=" + query))
            .selectFirst(".search_list li:eq(0) a[href]");
    }

    @Nullable
    private Element firstMeaningFor(Element word) throws IOException {
        return document(normalizedUrl(word.attr("href"))).selectFirst("ul.meanings li:eq(0) a[href]");
    }

    @NonNull
    private Document target(Element word) throws IOException {
        return document(normalizedUrl(word.attr("href")));
    }

    /**
     * Resolves a parser bean by a part of speech
     * @param partOfSpeech a part of speech
     * @return a parser bean by a part of speech
     * @throws ApplicationRuntimeException if cannot resolve the parser
     */
    @NonNull
    private Parser parser(String partOfSpeech) {
        return switch (partOfSpeech) {
            case "czasownik" -> new VerbParser();
            case "rzeczownik" -> new NounParser();
            case "przymiotnik" -> new AdjectiveParser();
            default -> throw new ApplicationRuntimeException(
                "Cannot choose appropriate parser for the part of speech: '" + partOfSpeech + "'"
            );
        };
    }

    /**
     * Analyzes an HTML document and resolves a part of speech
     * @param document HTML document
     * @return a part of speech
     * @throws ApplicationRuntimeException if cannot resolve a part of speech for the word
     */
    @NonNull
    private String partOfSpeech(Document document) {
        Element p = document.selectFirst("p:containsOwn(część mowy:)");

        if (p != null) {
            Element em = p.selectFirst("em");

            if (em != null) {
                return em.text();
            }
        }

        throw new ApplicationRuntimeException("Cannot resolve the part of speech for the word");
    }

    private Document document(String url) throws IOException {
        return Jsoup.connect(url).get();
    }

    private String normalizedUrl(String href) {
        try {
            return href.startsWith("http") ? baseUrl + new URL(href).getPath() : baseUrl + href;
        } catch (MalformedURLException e) {
            throw new ApplicationRuntimeException("Unable to resolve URL by the href: '" + href + "'");
        }
    }
}
