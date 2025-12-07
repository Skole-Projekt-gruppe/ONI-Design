package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.TestSequence;

public class TestSequenceMapper {

    public static TestSequenceDto toDto(TestSequence testSequence) {
        return new TestSequenceDto(
                testSequence.getTestSequenceId(),
                ModuleMapper.toDto(testSequence.getModule()),
                testSequence.getTestResults().stream()
                        .map(TestResultMapper::toDto)
                        .toList(),
                testSequence.getName(),
                testSequence.getDescription(),
                testSequence.getSequenceOrder()
        );
    }

    public static TestSequence toEntity(TestSequenceDto testSequenceDto) {
        TestSequence testSequence = new TestSequence();

        testSequence.setTestSequenceId(testSequenceDto.testSequenceId());
        testSequence.setModule(ModuleMapper.toEntity(testSequenceDto.module()));

        // Mapper hver testResultDto i testSequenceDto.testResults() til en TestResult entity,
        // sætter modulet på hver TestResult (så relationen bliver korrekt),
        // samler alle TestResult entities i en liste, og sætter listen på TestSequence.
        testSequence.setTestResults(testSequenceDto.testResults().stream()
                .map(testResultDto -> {
                    var testResult = TestResultMapper.toEntity(testResultDto);
                    testResult.setTestSequence(testSequence);
                    return testResult;
                })
                .toList()
        );

        testSequence.setName(testSequenceDto.name());
        testSequence.setDescription(testSequenceDto.description());
        testSequence.setSequenceOrder(testSequenceDto.sequenceOrder());

        return testSequence;
    }
}
