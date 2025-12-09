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

    @PostMapping("/testresult")
    public ResponseEntity<TestSequenceTestResultDto> createModulePackData(@RequestBody TestSequenceTestResultDto dto) {
        TestSequenceTestResultDto savedDto = testSequenceService.createTestSequenceTestResult(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }

    // GET /api/testsequences?search=&sortField=&sortDir=
    @GetMapping
    public List<TestSequenceDto> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "name") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return testSequenceService.getAll(search, sortField, sortDir);
    }
}
