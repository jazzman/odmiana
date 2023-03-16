package pro.jazzman.odmiana.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.junit.jupiter.Testcontainers;
import pro.jazzman.odmiana.entities.users.User;
import pro.jazzman.odmiana.repositories.UserRepository;

import static org.mockito.ArgumentMatchers.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Testcontainers
@DisplayName("UserService")
class UserServiceTest {
    private UserService userService;

    @Mock UserRepository repository;


    @BeforeEach
    void setUp() {
        userService = new UserService(repository);
    }

    @Test
    @DisplayName("Returns true if user exists in database")
    void exists() {
        when(repository.existsById(anyLong())).thenReturn(true);

        assertThat(userService.exists(100500L)).isTrue();
    }

    @Test
    @DisplayName("Calls repo to save a new user")
    void create() {
        var user = new User(100500L, "Lewis", "Carrol", "en");
        when(repository.save(user)).thenReturn(user);

        userService.create(user);

        verify(repository).save(user);
    }
}