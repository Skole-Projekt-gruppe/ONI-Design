package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.TestResultDto;
import dk.ek.onidesign.catalog.service.TestResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testresults")
public class TestResultController {

    private final TestResultService testResultService;

    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @PostMapping
    public ResponseEntity<TestResultDto> createTestResult(@RequestBody TestResultDto testResultDto) {
        TestResultDto newTestResult = testResultService.createTestResult(testResultDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTestResult);
    }
}
