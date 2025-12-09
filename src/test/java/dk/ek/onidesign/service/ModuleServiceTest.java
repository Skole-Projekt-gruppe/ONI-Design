package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.service.ModuleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.math.BigDecimal;

@ExtendWith(MockitoExtension.class)
class ModuleServiceTest {

    @Mock
    private ModuleRepository moduleRepository;

    @InjectMocks
    private ModuleService moduleService;


    // Metoden tester ikke oprettelse af ID'er via databasen,
    // da den faktisk aldrig gemmes.
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

        ModulePackDataDto result = moduleService.createModulePackData(input);

        assertNull(result.moduleId());
        assertEquals("Test Module", result.moduleName());
        assertEquals(null, result.packDataId());
        assertEquals(10, result.cellQuantity());
        assertEquals(new BigDecimal("25.0"), result.grossWeightKg());
        assertEquals(new BigDecimal("50.0"), result.nominalDischargeA());
        assertEquals(30, result.nominalChargeTimeMin());
        assertEquals(new BigDecimal("40.0"), result.peakDcChargeA());
    }
}