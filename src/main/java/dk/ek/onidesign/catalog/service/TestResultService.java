package dk.ek.onidesign.catalog.service;


import dk.ek.onidesign.catalog.dto.TestResultDto;
import dk.ek.onidesign.catalog.dto.TestResultMapper;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.repository.TestResultRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;

    public TestResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    /**
     * Hent alle TestResult for en given TestSequence,
     * sorteret efter valgfrit felt + retning.
     *
     * @param sequenceId id på TestSequence
     * @param sortField  hvilket felt i TestResult vi vil sortere efter (fx "testResultId")
     * @param sortDir    "asc" eller "desc" (case-insensitive)
     * @return liste af TestResultDto
     */
    public List<TestResultDto> getResultsForSequence(Long sequenceId,
                                                     String sortField,
                                                     String sortDir) {

        // 1) Bestem retning (default = asc)
        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        // 2) Bestem felt at sortere på (default = testResultId)
        String sortProperty =
                (sortField == null || sortField.isBlank())
                        ? "testResultId"   // <-- skal matche et felt i TestResult-entity
                        : sortField;

        Sort sort = Sort.by(direction, sortProperty);

        // 3) Hent data via @Query-metoden i repository
        List<TestResult> results = testResultRepository.findBySequenceId(sequenceId, sort);

        // 4) Map til DTO’er
        return results.stream()
                .map(TestResultMapper::toDto)
                .toList();
    }

    /**
     * Convenience-metode med standard-sortering (fx brugt af controller).
     */
    public List<TestResultDto> getResultsForSequence(Long sequenceId) {
        return getResultsForSequence(sequenceId, "testResultId", "asc");
    }

    public boolean deleteById(Long id) {
        if (!testResultRepository.existsById(id)) {
            return false;
        }
        testResultRepository.deleteById(id);
        return true;
    }
}
