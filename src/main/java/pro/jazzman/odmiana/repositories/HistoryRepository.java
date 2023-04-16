package pro.jazzman.odmiana.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import pro.jazzman.odmiana.entities.History;

@Repository
public class HistoryRepository {
    private final MongoTemplate mongo;

    @Autowired
    public HistoryRepository(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    public History save(History history) {
        return mongo.save(history);
    }
}
