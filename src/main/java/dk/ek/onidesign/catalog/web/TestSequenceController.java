package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.TestSequenceDto;
import dk.ek.onidesign.catalog.service.TestSequenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/testsequences")
public class TestSequenceController {

    private final TestSequenceService  testSequenceService;

    public TestSequenceController(TestSequenceService testSequenceService) {
        this.testSequenceService = testSequenceService;
    }

    @PostMapping
    public ResponseEntity<TestSequenceDto> createTestSequence(@RequestBody TestSequenceDto testSequenceDto) {
        TestSequenceDto newTestSequence = testSequenceService.createTestSequence(testSequenceDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newTestSequence);
    }
}
