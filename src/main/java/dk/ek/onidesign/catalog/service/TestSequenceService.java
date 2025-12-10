package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.TestSequenceDto;
import dk.ek.onidesign.catalog.dto.TestSequenceMapper;
import dk.ek.onidesign.catalog.dto.TestSequenceTestResultDto;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.entity.TestSequence;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.TestSequenceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;




import java.util.List;

@Service
public class TestSequenceService {

    private final TestSequenceRepository testSequenceRepository;
    private final ModuleRepository moduleRepository;

    public TestSequenceService(TestSequenceRepository testSequenceRepository, ModuleRepository moduleRepository) {
        this.testSequenceRepository = testSequenceRepository;
        this.moduleRepository = moduleRepository;
    }

    public TestSequenceTestResultDto createTestSequenceTestResult(TestSequenceTestResultDto dto) {
        Long moduleId = dto.moduleId();
        if (moduleId == null) {
            throw new IllegalArgumentException("Module ID must not be null");
        }

        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new EntityNotFoundException("Module not found with id " + moduleId));


        TestSequence testSequence = new TestSequence();
        testSequence.setModule(module);
        testSequence.setName(dto.name());
        testSequence.setDescription(dto.description());
        testSequence.setSequenceOrder(dto.sequenceOrder());

        TestResult testResult = new TestResult();
        testResult.setTestSequence(testSequence); // dette sætter også bi-direktionalitet
        testResult.setStartingVoltageV(dto.startingVoltageV());
        testResult.setPeakChargeVoltageV(dto.peakChargeVoltageV());
        testResult.setDischargeVoltageV(dto.dischargeVoltageV());
        testResult.setVoltageImbalanceMaxV(dto.voltageImbalanceMaxV());
        testResult.setNominalTempC(dto.nominalTempC());
        testResult.setMaxTempC(dto.maxTempC());
        testResult.setMinTempC(dto.minTempC());
        testResult.setMaxDischargeA(dto.maxDischargeA());
        testResult.setSustainedMaxDischargeSec(dto.sustainedMaxDischargeSec());
        testResult.setTempCutoffReached(dto.tempCutoffReached());
        testResult.setFaultsEncountered(dto.faultsEncountered());
        testResult.setFaultType(dto.faultType());

        testSequenceRepository.save(testSequence);

        return new TestSequenceTestResultDto(
                testSequence.getTestSequenceId(),
                module.getModuleId(),
                testSequence.getName(),
                testSequence.getDescription(),
                testSequence.getSequenceOrder(),
                testResult.getTestResultId(),
                testResult.getStartingVoltageV(),
                testResult.getPeakChargeVoltageV(),
                testResult.getDischargeVoltageV(),
                testResult.getVoltageImbalanceMaxV(),
                testResult.getNominalTempC(),
                testResult.getMaxTempC(),
                testResult.getMinTempC(),
                testResult.getMaxDischargeA(),
                testResult.getSustainedMaxDischargeSec(),
                testResult.isTempCutoffReached(),
                testResult.getFaultsEncountered(),
                testResult.getFaultType()
        );
    }
/*
    public List<TestSequenceDto> getAll(String search, String sortField, String sortDir) {
        // 1) Bestem retning (default asc)
        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        // 2) Bestem sorteringsfelt (default = "name")
        String sortProperty =
                (sortField == null || sortField.isBlank())
                        ? "name"
                        : sortField;

        Sort sort = Sort.by(direction, sortProperty);

        // 3) Hent data fra DB
        List<TestSequence> sequences;
        if (search != null && !search.isBlank()) {
            // kræver en metode i repo:
            // List<TestSequence> findByNameContainingIgnoreCase(String name, Sort sort);
            sequences = testSequenceRepository.findByNameContainingIgnoreCase(search, sort);
        } else {
            sequences = testSequenceRepository.findAll(sort);
        }

        // 4) Map til dine eksisterende DTO’er
        return sequences.stream()
                .map(TestSequenceMapper::toDto)
                .toList();
    }
    */
}
