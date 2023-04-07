package pro.jazzman.odmiana.services.vocabulary;

import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SJP {
    private final String url;

    public SJP(@Value("${sjp.url}") final String url) {
        this.url = url;
    }

    public String get(String word) throws Exception {
        Element infinitive = document(url + "/" + word).selectFirst("a.lc");

        if (infinitive == null) {
            throw new NotFoundException("Unable to find the word '" + word + "' in SJP");
        }

        return infinitive.text();
    }

    private Document document(String url) throws Exception {
        try {
            return Jsoup.connect(url).timeout(5000).get();
        } catch (HttpStatusException e) {
            if (HttpStatus.NOT_FOUND.value() == e.getStatusCode()) {
                throw new NotFoundException("The word not found on the '" + url + "'", e);
            }

            throw e;
        }
    }
}
