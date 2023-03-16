package pro.jazzman.odmiana.services;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pro.jazzman.odmiana.entities.History;
import pro.jazzman.odmiana.repositories.HistoryRepository;

@Service
@AllArgsConstructor
public class HistoryService {
    private HistoryRepository repository;

    public void create(History entry) {
        repository.save(entry);
    }
}
