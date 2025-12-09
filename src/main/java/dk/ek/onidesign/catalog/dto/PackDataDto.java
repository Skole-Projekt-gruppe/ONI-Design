package dk.ek.onidesign.catalog.dto;

import java.math.BigDecimal;

public record PackDataDto(
        Long packDataId,
        Long moduleId,
        int cellQuantity,
        BigDecimal cellWeightKg,
        BigDecimal grossWeightKg,
        BigDecimal nominalCapacityKwh,
        BigDecimal peakCapacityKwh,
        BigDecimal nominalVoltageV,
        BigDecimal peakVoltageV,
        BigDecimal cutoffVoltageV,
        BigDecimal nominalDischargeA,
        BigDecimal peakDischargeA,
        BigDecimal nominalAcDcChargeA,
        int nominalChargeTimeMin,
        BigDecimal peakDcChargeA,
        int peakChargeTimeMin
) {}

