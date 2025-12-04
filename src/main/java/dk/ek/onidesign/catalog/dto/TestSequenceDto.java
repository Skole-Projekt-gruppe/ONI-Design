package dk.ek.onidesign.catalog.dto;


import java.util.List;

public record TestSequenceDto(
        Long testSequenceId,
        ModuleDto module,
        List<TestResultDto> testResults,
        String name,
        String description,
        int sequenceOrder
        ) {
}
