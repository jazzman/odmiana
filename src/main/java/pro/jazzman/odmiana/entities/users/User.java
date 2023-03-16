package pro.jazzman.odmiana.entities.users;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("users")
@Data
public class User {
    @Id
    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String lang;
    private final LocalDateTime createdAt = LocalDateTime.now();
}
