package pro.jazzman.odmiana.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.users.User;
import pro.jazzman.odmiana.repositories.UserRepository;

@Service
@AllArgsConstructor
public class UserService {
    private UserRepository repository;

    public boolean exists(Long id) {
        return repository.existsById(id);
    }

    public void create(User user) {
        repository.save(user);
    }
}
