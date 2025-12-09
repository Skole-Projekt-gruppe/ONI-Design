package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.TestSequenceDto;
import dk.ek.onidesign.catalog.dto.TestSequenceMapper;
import dk.ek.onidesign.catalog.entity.TestSequence;
import dk.ek.onidesign.catalog.repository.TestSequenceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestSequenceService {

    private final TestSequenceRepository testSequenceRepository;

    public TestSequenceService(TestSequenceRepository testSequenceRepository) {
        this.testSequenceRepository = testSequenceRepository;
    }

    public List<TestSequenceDto> getAll(String search, String sortField, String sortDir) {
        // 1) Bestem retning (default asc)
        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        // 2) Bestem sorteringsfelt (default = "name")
        String sortProperty =
                (sortField == null || sortField.isBlank())
                        ? "name"
                        : sortField;

        Sort sort = Sort.by(direction, sortProperty);

        // 3) Hent data fra DB
        List<TestSequence> sequences;
        if (search != null && !search.isBlank()) {
            // kræver en metode i repo:
            // List<TestSequence> findByNameContainingIgnoreCase(String name, Sort sort);
            sequences = testSequenceRepository.findByNameContainingIgnoreCase(search, sort);
        } else {
            sequences = testSequenceRepository.findAll(sort);
        }

        // 4) Map til dine eksisterende DTO’er
        return sequences.stream()
                .map(TestSequenceMapper::toDto)
                .toList();
    }

}
