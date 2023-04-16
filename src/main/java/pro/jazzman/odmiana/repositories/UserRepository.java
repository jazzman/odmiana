package pro.jazzman.odmiana.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import pro.jazzman.odmiana.entities.users.User;

@Repository
public class UserRepository {
    private final MongoTemplate mongo;

    @Autowired
    public UserRepository(MongoTemplate mongo) {
        this.mongo = mongo;
    }

    public boolean existsById(Long id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(id));

        return mongo.exists(query, User.class);
    }

    public User save(User user) {
        return mongo.save(user);
    }
}
