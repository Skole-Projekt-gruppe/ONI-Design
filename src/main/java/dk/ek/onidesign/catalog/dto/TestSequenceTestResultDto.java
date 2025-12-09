package dk.ek.onidesign.catalog.dto;

import java.math.BigDecimal;

public record TestSequenceTestResultDto(
        // Test Sequence
        Long testSequenceId,
        Long moduleId,
        String name,
        String description,
        Integer sequenceOrder,

        // TestResult
        Long testResultId,
        BigDecimal startingVoltageV,
        BigDecimal peakChargeVoltageV,
        BigDecimal dischargeVoltageV,
        BigDecimal voltageImbalanceMaxV,
        BigDecimal nominalTempC,
        BigDecimal maxTempC,
        BigDecimal minTempC,
        BigDecimal maxDischargeA,
        int sustainedMaxDischargeSec,
        boolean tempCutoffReached,
        int faultsEncountered,
        String faultType
) {
}
