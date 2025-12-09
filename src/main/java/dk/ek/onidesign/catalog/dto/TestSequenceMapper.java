package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.Module;
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

    public static TestSequence toEntity(TestSequenceDto dto, Module module) {
        TestSequence ts = new TestSequence();
        ts.setTestSequenceId(dto.testSequenceId());
        ts.setModule(module);
        ts.setName(dto.name());
        ts.setDescription(dto.description());
        ts.setSequenceOrder(dto.sequenceOrder());
        return ts;
    }
}

