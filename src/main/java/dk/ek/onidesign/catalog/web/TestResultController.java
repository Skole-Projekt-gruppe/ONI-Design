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
    public ResponseEntity<List<TestResultDto>> getForSequence(
            @PathVariable Long sequenceId,
            @RequestParam(defaultValue = "testResultId") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        List<TestResultDto> dtos = testResultService.getResultsForSequence(sequenceId, sortField, sortDir);
        return ResponseEntity.ok(dtos);
    }

}
