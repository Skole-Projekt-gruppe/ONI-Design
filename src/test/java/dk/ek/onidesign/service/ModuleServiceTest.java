/*

package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

import dk.ek.onidesign.catalog.entity.Module;


import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleService moduleService;

    @Test
    void testCreateModulePackData() {
        ModulePackDataDto input = new ModulePackDataDto(
                null,
                "Test Module",
                "Description",
                "img.png",

                null,
                10,
                new BigDecimal("2.5"),
                new BigDecimal("25.0"),
                new BigDecimal("5.5"),
                new BigDecimal("6.0"),
                new BigDecimal("400.0"),
                new BigDecimal("420.0"),
                new BigDecimal("300.0"),
                new BigDecimal("50.0"),
                new BigDecimal("60.0"),
                new BigDecimal("20.0"),
                30,
                new BigDecimal("40.0"),
                25
        );

        Module savedModule = new Module();
        savedModule.setModuleId(1L);

        PackData savedPack = new PackData();
        savedPack.setPackDataId(2L);

        // bi-direktionelt
        savedPack.setModule(savedModule);
        savedModule.setPackData(savedPack);

        // Mocking af "moduleRepository.save(module);"
        when(moduleRepository.save(any(Module.class)))
                .thenReturn(savedModule);

        ModulePackDataDto result = moduleService.createModulePackData(input);

        assertNull(result.moduleId());
        assertEquals("Test Module", result.moduleName());
        assertEquals(2L, result.packDataId());
        assertEquals(10, result.cellQuantity());
        assertEquals(new BigDecimal("25.0"), result.grossWeightKg());
        assertEquals(new BigDecimal("50.0"), result.nominalDischargeA());
        assertEquals(30, result.nominalChargeTimeMin());
        assertEquals(new BigDecimal("40.0"), result.peakDcChargeA());
    }
}

*/