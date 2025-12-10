package dk.ek.onidesign.catalog.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record PackDataDto(
        Long packDataId,
        Long moduleId,

        @Positive(message = "Cell Quantity must be greater than 0")
        int cellQuantity,

        @Digits(integer = 3, fraction = 2, message = "Cell weight must be a number with up to 3 digits and 2 decimals")
        BigDecimal cellWeightKg,

        @Digits(integer = 3, fraction = 2, message = "Gross weight must be a number with up to 5 digits and 2 decimals")
        BigDecimal grossWeightKg,

        @Digits(integer = 3, fraction = 2, message = "Nominal capacity must be a number with up to 5 digits and 2 decimals")
        BigDecimal nominalCapacityKwh,

        @Digits(integer = 3, fraction = 2, message = "Peak capacity must be a number with up to 5 digits and 2 decimals")
        BigDecimal peakCapacityKwh,

        @Digits(integer = 3, fraction = 2, message = "Nominal voltage must be a number with up to 5 digits and 2 decimals")
        BigDecimal nominalVoltageV,

        @Digits(integer = 3, fraction = 2, message = "Peak voltage must be a number with up to 5 digits and 2 decimals")
        BigDecimal peakVoltageV,

        @Digits(integer = 3, fraction = 2, message = "Cutoff voltage must be a number with up to 5 digits and 2 decimals")
        BigDecimal cutoffVoltageV,

        @Digits(integer = 5, fraction = 1, message = "Nominal discharge must be a number with up to 6 digits and 1 decimal")
        BigDecimal nominalDischargeA,

        @Digits(integer = 5, fraction = 1, message = "Peak discharge must be a number with up to 6 digits and 1 decimal")
        BigDecimal peakDischargeA,

        @Digits(integer = 5, fraction = 1, message = "Nominal AC/DC charge must be a number with up to 6 digits and 1 decimal")
        BigDecimal nominalAcDcChargeA,

        @Positive(message = "Nominal charge time must be positive")
        int nominalChargeTimeMin,

        @Digits(integer = 5, fraction = 1, message = "Peak DC charge must be a number with up to 6 digits and 1 decimal")
        BigDecimal peakDcChargeA,

        @Positive(message = "Peak charge time must be positive")
        int peakChargeTimeMin
) {
}

