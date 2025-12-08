package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.TestSequence;

public class TestSequenceMapper {

    public static TestSequenceDto toDto(TestSequence seq) {
        return new TestSequenceDto(
                seq.getTestSequenceId(),
                seq.getModule().getModuleId(),
                seq.getName(),
                seq.getDescription(),
                seq.getSequenceOrder()
        );
    }

    public static TestSequence toEntity(TestSequenceDto dto, Module module) {
        TestSequence t = new TestSequence();
        t.setTestSequenceId(dto.testSequenceId());
        t.setModule(module);
        t.setName(dto.name());
        t.setDescription(dto.description());
        t.setSequenceOrder(dto.sequenceOrder());
        return t;
    }
}
