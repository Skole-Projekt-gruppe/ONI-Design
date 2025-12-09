package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.TestSequence;

public class TestSequenceMapper {

    public static TestSequenceDto toDto(TestSequence ts) {
        return new TestSequenceDto(
                ts.getTestSequenceId(),
                ts.getModule().getModuleId(),
                ts.getName(),
                ts.getDescription(),
                ts.getSequenceOrder()
        );
    }

    public static TestSequence toEntity(TestSequenceDto dto) {
        TestSequence ts = new TestSequence();
        ts.setTestSequenceId(dto.testSequenceId());
        // ts.setModule(module); Ved ikke om dette kan slettes ??
        ts.setName(dto.name());
        ts.setDescription(dto.description());
        ts.setSequenceOrder(dto.sequenceOrder());
        return ts;
    }
}

