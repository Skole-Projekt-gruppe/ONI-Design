package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.TestSequence;

import java.math.BigDecimal;

public record TestResultDto(
        Long testResultId,
        TestSequenceDto testSequence,
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
) {}
