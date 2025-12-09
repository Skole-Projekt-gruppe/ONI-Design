package dk.ek.onidesign.mapper;

import dk.ek.onidesign.catalog.dto.TestResultDto;
import dk.ek.onidesign.catalog.dto.TestResultMapper;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.entity.TestSequence;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestResultMapperTest {

    @Test
    void testToDto() {
        TestSequence seq = new TestSequence();
        seq.setTestSequenceId(100L);

        TestResult tr = new TestResult();
        tr.setTestResultId(1L);
        tr.setTestSequence(seq);
        tr.setStartingVoltageV(new BigDecimal("48.5"));
        tr.setPeakChargeVoltageV(new BigDecimal("50.0"));
        tr.setDischargeVoltageV(new BigDecimal("42.0"));
        tr.setVoltageImbalanceMaxV(new BigDecimal("0.1"));
        tr.setNominalTempC(new BigDecimal("25.0"));
        tr.setMaxTempC(new BigDecimal("35.0"));
        tr.setMinTempC(new BigDecimal("20.0"));
        tr.setMaxDischargeA(new BigDecimal("15.0"));
        tr.setSustainedMaxDischargeSec(60);
        tr.setTempCutoffReached(true);
        tr.setFaultsEncountered(2);
        tr.setFaultType("OverVoltage");

        TestResultDto dto = TestResultMapper.toDto(tr);

        assertEquals(1L, dto.testResultId());
        assertEquals(100L, dto.testSequenceId());
        assertEquals(new BigDecimal("48.5"), dto.startingVoltageV());
        assertEquals(new BigDecimal("50.0"), dto.peakChargeVoltageV());
        assertEquals(new BigDecimal("42.0"), dto.dischargeVoltageV());
        assertEquals(new BigDecimal("0.1"), dto.voltageImbalanceMaxV());
        assertEquals(new BigDecimal("25.0"), dto.nominalTempC());
        assertEquals(new BigDecimal("35.0"), dto.maxTempC());
        assertEquals(new BigDecimal("20.0"), dto.minTempC());
        assertEquals(new BigDecimal("15.0"), dto.maxDischargeA());
        assertEquals(60, dto.sustainedMaxDischargeSec());
        assertTrue(dto.tempCutoffReached());
        assertEquals(2, dto.faultsEncountered());
        assertEquals("OverVoltage", dto.faultType());
    }

    @Test
    void testToEntity() {
        TestSequence seq = new TestSequence();
        seq.setTestSequenceId(100L);

        TestResultDto dto = new TestResultDto(
                1L,
                100L,
                new BigDecimal("48.5"),
                new BigDecimal("50.0"),
                new BigDecimal("42.0"),
                new BigDecimal("0.1"),
                new BigDecimal("25.0"),
                new BigDecimal("35.0"),
                new BigDecimal("20.0"),
                new BigDecimal("15.0"),
                60,
                true,
                2,
                "none"
        );

        TestResult tr = TestResultMapper.toEntity(dto, seq);

        assertEquals(1L, tr.getTestResultId());
        assertEquals(seq, tr.getTestSequence());
        assertEquals(new BigDecimal("48.5"), tr.getStartingVoltageV());
        assertEquals(new BigDecimal("50.0"), tr.getPeakChargeVoltageV());
        assertEquals(new BigDecimal("42.0"), tr.getDischargeVoltageV());
        assertEquals(new BigDecimal("0.1"), tr.getVoltageImbalanceMaxV());
        assertEquals(new BigDecimal("25.0"), tr.getNominalTempC());
        assertEquals(new BigDecimal("35.0"), tr.getMaxTempC());
        assertEquals(new BigDecimal("20.0"), tr.getMinTempC());
        assertEquals(new BigDecimal("15.0"), tr.getMaxDischargeA());
        assertEquals(60, tr.getSustainedMaxDischargeSec());
        assertTrue(tr.isTempCutoffReached());
        assertEquals(2, tr.getFaultsEncountered());
        assertEquals("none", tr.getFaultType());
    }
}
