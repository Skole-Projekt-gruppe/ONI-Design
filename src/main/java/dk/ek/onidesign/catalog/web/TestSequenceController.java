package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.TestSequenceDto;
import dk.ek.onidesign.catalog.dto.TestSequenceTestResultDto;
import dk.ek.onidesign.catalog.service.TestSequenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/testsequences")
public class TestSequenceController {

    private final TestSequenceService  testSequenceService;

    public TestSequenceController(TestSequenceService testSequenceService) {
        this.testSequenceService = testSequenceService;
    }
    @GetMapping
    public List<TestSequenceDto> getTestSequences(
            @RequestParam Long moduleId
    ) {
        return testSequenceService.getByModuleId(moduleId);
    }
    @PostMapping("/testresult")
    public ResponseEntity<TestSequenceTestResultDto> createModulePackData(@RequestBody TestSequenceTestResultDto dto) {
        TestSequenceTestResultDto savedDto = testSequenceService.createTestSequenceTestResult(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        testSequenceService.deleteTestSequence(id);
        return ResponseEntity.noContent().build();
    }
}

