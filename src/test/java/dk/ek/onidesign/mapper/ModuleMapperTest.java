package dk.ek.onidesign.mapper;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.dto.ModuleMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import dk.ek.onidesign.catalog.entity.Module;

public class ModuleMapperTest {

    @Test
    void testToDto() {
        Module module = new Module();
        module.setModuleId(1L);
        module.setModuleName("Test");

        ModuleDto dto = ModuleMapper.toDto(module);

        assertEquals(1L, dto.moduleId());
        assertEquals("Test", dto.moduleName());
    }

    @Test
    void testToEntity() {
        // ToEntity metoden tager ikke ID med, derfor null. Der er GenerationType i entitien.
        ModuleDto dto = new ModuleDto(null, "Test","Test-D","LINK");

        Module entity = ModuleMapper.toEntity(dto);

        assertNull(entity.getModuleId());
        assertEquals("Test", entity.getModuleName());
        assertEquals("Test-D",entity.getDescription());
        assertEquals("LINK",entity.getOverviewImageUrl());
    }
}
