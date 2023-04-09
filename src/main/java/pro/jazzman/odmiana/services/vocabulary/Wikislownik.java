package pro.jazzman.odmiana.services.vocabulary;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.services.wikislownik.Page;


@Service
@Slf4j
public class Wikislownik {
    private final String url;
    public Wikislownik(@Value("${wikislownik.url}") final String url) {
        this.url = url;
    }

    public Page page(String word) {
        return new Page(url, word);
    }
}
