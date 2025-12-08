package dk.ek.onidesign.catalog.service;


import dk.ek.onidesign.catalog.dto.TestResultDto;
import dk.ek.onidesign.catalog.dto.TestResultMapper;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.repository.TestResultRepository;
import org.springframework.stereotype.Service;

@Service
public class TestResultService {

    private final TestResultRepository testResultRepository;

    public TestResultService(TestResultRepository testResultRepository) {
        this.testResultRepository = testResultRepository;
    }

    public TestResultDto createTestResult(TestResultDto testResultDto) {
        TestResult testResult = TestResultMapper.toEntity(testResultDto);
        TestResult savedTestResult = testResultRepository.save(testResult);
        return TestResultMapper.toDto(savedTestResult);
    }
}
