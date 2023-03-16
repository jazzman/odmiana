package pro.jazzman.odmiana.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pro.jazzman.odmiana.entities.History;
import pro.jazzman.odmiana.entities.users.User;
import pro.jazzman.odmiana.repositories.HistoryRepository;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("HistoryService")
class HistoryServiceTest {

    private HistoryService historyService;

    @Mock private HistoryRepository repository;

    @BeforeEach
    void setUp() {
        historyService = new HistoryService(repository);
    }

    @Test
    @DisplayName("Calls repo to save a new history entry")
    void create() {
        var history = new History(100500L, "kochać");
        when(repository.save(history)).thenReturn(history);

        historyService.create(history);

        verify(repository).save(history);
    }
}