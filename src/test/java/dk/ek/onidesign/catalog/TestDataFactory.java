package dk.ek.onidesign.catalog;

import dk.ek.onidesign.catalog.dto.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TestDataFactory {

    public static ModuleDto createMockModule() {

        PackDataDto packData = new PackDataDto(
                1L,
                null,
                200,
                new BigDecimal("2.50"),
                new BigDecimal("25.00"),
                new BigDecimal("12.5"),
                new BigDecimal("15.0"),
                new BigDecimal("48.0"),
                new BigDecimal("52.0"),
                new BigDecimal("40.0"),
                new BigDecimal("120.0"),
                new BigDecimal("200.0"),
                new BigDecimal("150.0"),
                60,
                new BigDecimal("220.0"),
                45
        );

// First, create a placeholder sequence with empty results
        TestSequenceDto sequence = new TestSequenceDto(
                1L,
                null,
                List.of(),
                "Charge Cycle Test",
                "Measures voltages and temperatures under full cycle.",
                1
        );

// Now create the TestResultDto with the sequence
        TestResultDto result = new TestResultDto(
                1L,
                sequence, // pass the sequence reference
                new BigDecimal("44.2"),
                new BigDecimal("52.1"),
                new BigDecimal("41.8"),
                new BigDecimal("0.05"),
                new BigDecimal("32.5"),
                new BigDecimal("40.0"),
                new BigDecimal("28.0"),
                new BigDecimal("180.0"),
                120,
                false,
                0,
                "None"
        );

// Finally, create a new sequence including the result
        sequence = new TestSequenceDto(
                sequence.testSequenceId(),
                sequence.module(),
                List.of(result),
                sequence.name(),
                sequence.description(),
                sequence.sequenceOrder()
        );

        return new ModuleDto(
                1L,
                packData,
                List.of(sequence),
                "Module Alpha 48V",
                "High-performance battery module for EV applications.",
                null,
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }
}
