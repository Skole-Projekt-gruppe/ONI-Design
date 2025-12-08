package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.TestSequenceDto;
import dk.ek.onidesign.catalog.dto.TestSequenceMapper;
import dk.ek.onidesign.catalog.entity.TestSequence;
import dk.ek.onidesign.catalog.repository.TestSequenceRepository;
import org.springframework.stereotype.Service;

@Service
public class TestSequenceService {

    private final TestSequenceRepository testSequenceRepository;

    public TestSequenceService(TestSequenceRepository testSequenceRepository) {
        this.testSequenceRepository = testSequenceRepository;
    }

    public TestSequenceDto createTestSequence(TestSequenceDto testSequenceDto) {
        TestSequence testSequence = TestSequenceMapper.toEntity(testSequenceDto);
        TestSequence savedTestSequence = testSequenceRepository.save(testSequence);
        return TestSequenceMapper.toDto(savedTestSequence);
    }
}
