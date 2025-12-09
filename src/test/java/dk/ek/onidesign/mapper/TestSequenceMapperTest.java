package dk.ek.onidesign.mapper;

import dk.ek.onidesign.catalog.dto.TestSequenceDto;
import dk.ek.onidesign.catalog.dto.TestSequenceMapper;
import dk.ek.onidesign.catalog.entity.TestSequence;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import dk.ek.onidesign.catalog.entity.Module;


public class TestSequenceMapperTest {

    @Test
    void testToDto() {
        Module module = new Module();
        module.setModuleId(2L);

        TestSequence ts = new TestSequence();
        ts.setTestSequenceId(1L);
        ts.setModule(module);
        ts.setName("Test");
        ts.setDescription("Desc");
        ts.setSequenceOrder(1);

        TestSequenceDto dto = TestSequenceMapper.toDto(ts);

        assertEquals(1L,dto.testSequenceId());
        assertEquals(2L,dto.moduleId());
        assertEquals("Test",dto.name());
        assertEquals("Desc",dto.description());
        assertEquals(1,dto.sequenceOrder());
    }

    @Test
    void testToEntity() {
        Module module = new Module();

        TestSequenceDto dto = new TestSequenceDto(
                1L,
                2L,
                "test",
                "desc",
                1);

        TestSequence ts = TestSequenceMapper.toEntity(dto,module);

        assertEquals(1L,ts.getTestSequenceId());
        assertEquals(module,ts.getModule());
        assertEquals("test",ts.getName());
        assertEquals("desc",ts.getDescription());
        assertEquals(1,ts.getSequenceOrder());
    }
}
