package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.TestResultDto;
import dk.ek.onidesign.catalog.service.TestResultService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testresults")
public class TestResultController {

    private final TestResultService testResultService;

    public TestResultController(TestResultService testResultService) {
        this.testResultService = testResultService;
    }

    @GetMapping("/sequence/{sequenceId}")
    public ResponseEntity<List<TestResultDto>> getForSequence(@PathVariable Long sequenceId) {
        List<TestResultDto> dtos = testResultService.getResultsForSequence(sequenceId);
        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestResult(@PathVariable Long id) {
        boolean deleted = testResultService.deleteById(id);
        if (deleted) {
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build();  // 404
        }
    }
}
