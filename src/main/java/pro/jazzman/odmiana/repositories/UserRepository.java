package pro.jazzman.odmiana.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import pro.jazzman.odmiana.entities.users.User;

public interface UserRepository extends MongoRepository<User, Long> {
}
