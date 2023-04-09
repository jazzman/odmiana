package pro.jazzman.odmiana.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pro.jazzman.odmiana.entities.partsofspeech.Word;

public interface WordRepository extends MongoRepository<Word, String> {
}
