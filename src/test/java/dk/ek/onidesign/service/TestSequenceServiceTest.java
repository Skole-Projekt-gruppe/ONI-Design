package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.dto.TestSequenceTestResultDto;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.TestSequenceRepository;
import dk.ek.onidesign.catalog.service.TestSequenceService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import dk.ek.onidesign.catalog.entity.Module;

@ExtendWith(MockitoExtension.class)
public class TestSequenceServiceTest {

    @Mock
    private TestSequenceRepository testSequenceRepository;

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private TestSequenceService service;

    // Metoden tester ikke oprettelse af ID'er via databasen,
    // da den faktisk aldrig gemmes.
    // Vi bliver dog nød til at indsætte et dummyModule for at findById fungerer korrekt
    @Test
    void testcreateTestSequenceTestResult() {
        Module dummyModule = new Module();
        dummyModule.setModuleId(1L);

        when(moduleRepository.findById(anyLong()))
                .thenReturn(Optional.of(dummyModule));

        TestSequenceTestResultDto input = new TestSequenceTestResultDto(
                null,
                1L,
                "Module Test Sequence",
                "A test sequence for module 101",
                1,

                null,
                new BigDecimal("3.7"),
                new BigDecimal("4.2"),
                new BigDecimal("3.3"),
                new BigDecimal("0.05"),
                new BigDecimal("25.0"),
                new BigDecimal("45.0"),
                new BigDecimal("20.0"),
                new BigDecimal("50.0"),
                120,
                false,
                0,
                "fejl"
        );

        TestSequenceTestResultDto result = service.createTestSequenceTestResult(input);


        assertNull(result.testSequenceId(), "testSequenceId should be null");
        assertEquals(1L, result.moduleId(), "moduleId mismatch");
        assertEquals("Module Test Sequence", result.name(), "name mismatch");
        assertEquals("A test sequence for module 101", result.description(), "description mismatch");
        assertEquals(1, result.sequenceOrder(), "sequenceOrder mismatch");

        assertNull(result.testResultId(), "testResultId should be null");
        assertEquals(new BigDecimal("3.7"), result.startingVoltageV(), "startingVoltageV mismatch");
        assertEquals(new BigDecimal("4.2"), result.peakChargeVoltageV(), "peakChargeVoltageV mismatch");
        assertEquals(new BigDecimal("3.3"), result.dischargeVoltageV(), "dischargeVoltageV mismatch");
        assertEquals(new BigDecimal("0.05"), result.voltageImbalanceMaxV(), "voltageImbalanceMaxV mismatch");
        assertEquals(new BigDecimal("25.0"), result.nominalTempC(), "nominalTempC mismatch");
        assertEquals(new BigDecimal("45.0"), result.maxTempC(), "maxTempC mismatch");
        assertEquals(new BigDecimal("20.0"), result.minTempC(), "minTempC mismatch");
        assertEquals(new BigDecimal("50.0"), result.maxDischargeA(), "maxDischargeA mismatch");
        assertEquals(120, result.sustainedMaxDischargeSec(), "sustainedMaxDischargeSec mismatch");
        assertFalse(result.tempCutoffReached(), "tempCutoffReached should be false");
        assertEquals(0, result.faultsEncountered(), "faultsEncountered mismatch");
        assertEquals("fejl", result.faultType(), "faultType mismatch");
    }
}
