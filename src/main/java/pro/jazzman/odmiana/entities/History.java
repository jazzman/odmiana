package pro.jazzman.odmiana.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document("history")
@Data
public class History {
    @Id
    private String id;
    private final Long userId;
    private final String word;
    private final String error;
    private final LocalDateTime createdAt = LocalDateTime.now();

    public History(Long userId, String word) {
        this(userId, word, null);
    }

    public History(Long userId, String word, String error) {
        this.userId = userId;
        this.word = word;
        this.error = error;
    }
}
