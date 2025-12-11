package dk.ek.onidesign.catalog.service;


import dk.ek.onidesign.catalog.dto.TestResultDto;
import dk.ek.onidesign.catalog.dto.TestResultMapper;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.exception.InternalServerException;
import dk.ek.onidesign.catalog.exception.NotFoundException;
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
     * @param sequenceId id på TestSequence
     * @return liste af TestResultDto
     */
    public List<TestResultDto> getResultsForSequence(Long sequenceId) {
        return testResultRepository
                .findBySequenceId(sequenceId)
                .stream()
                .map(TestResultMapper::toDto)
                .toList();
    }

    /*
     * Sletter et TestResult med det givne id.
     * @return true hvis der blev slettet, false hvis id ikke fandtes.
     */
    public boolean deleteById(Long id) {
        if (!testResultRepository.existsById(id)) {
            return false;
        }
        testResultRepository.deleteById(id);
        return true;
    }
}
