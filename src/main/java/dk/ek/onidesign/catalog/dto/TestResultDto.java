package dk.ek.onidesign.catalog.dto;

import java.math.BigDecimal;

public record TestResultDto(
        Long testResultId,
        Long testSequenceId,
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
