package dk.ek.onidesign.mapper;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.dto.PackDataMapper;
import dk.ek.onidesign.catalog.entity.PackData;
import org.junit.jupiter.api.Test;
import dk.ek.onidesign.catalog.entity.Module;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PackDataMapperTest {

    @Test
    void testToDto() {
        Module module = new Module();
        module.setModuleId(101L);

        PackData packData = new PackData();
        packData.setPackDataId(1L);
        packData.setModule(module);
        packData.setCellQuantity(3);
        packData.setCellWeightKg(new BigDecimal("1.5"));
        packData.setGrossWeightKg(new BigDecimal("45.0"));
        packData.setNominalCapacityKwh(new BigDecimal("10.0"));
        packData.setPeakCapacityKwh(new BigDecimal("12.0"));
        packData.setNominalVoltageV(new BigDecimal("48.0"));
        packData.setPeakVoltageV(new BigDecimal("50.0"));
        packData.setCutoffVoltageV(new BigDecimal("42.0"));
        packData.setNominalDischargeA(new BigDecimal("20.0"));
        packData.setPeakDischargeA(new BigDecimal("25.0"));
        packData.setNominalAcDcChargeA(new BigDecimal("10.0"));
        packData.setNominalChargeTimeMin(60);
        packData.setPeakDcChargeA(new BigDecimal("15.0"));
        packData.setPeakChargeTimeMin(45);

        PackDataDto dto = PackDataMapper.toDto(packData);

        assertEquals(1L, dto.packDataId());
        assertEquals(101L, dto.moduleId());
        assertEquals(3, dto.cellQuantity());
        assertEquals(new BigDecimal("1.5"), dto.cellWeightKg());
        assertEquals(new BigDecimal("45.0"), dto.grossWeightKg());
        assertEquals(new BigDecimal("10.0"), dto.nominalCapacityKwh());
        assertEquals(new BigDecimal("12.0"), dto.peakCapacityKwh());
        assertEquals(new BigDecimal("48.0"), dto.nominalVoltageV());
        assertEquals(new BigDecimal("50.0"), dto.peakVoltageV());
        assertEquals(new BigDecimal("42.0"), dto.cutoffVoltageV());
        assertEquals(new BigDecimal("20.0"), dto.nominalDischargeA());
        assertEquals(new BigDecimal("25.0"), dto.peakDischargeA());
        assertEquals(new BigDecimal("10.0"), dto.nominalAcDcChargeA());
        assertEquals(60, dto.nominalChargeTimeMin());
        assertEquals(new BigDecimal("15.0"), dto.peakDcChargeA());
        assertEquals(45, dto.peakChargeTimeMin());
    }

    @Test
    void testToEntity() {
        // Opret Module
        Module module = new Module();
        module.setModuleId(101L);

        // Opret PackDataDto
        PackDataDto dto = new PackDataDto(
                1L,
                101L,
                3,
                new BigDecimal("1.5"),
                new BigDecimal("45.0"),
                new BigDecimal("10.0"),
                new BigDecimal("12.0"),
                new BigDecimal("48.0"),
                new BigDecimal("50.0"),
                new BigDecimal("42.0"),
                new BigDecimal("20.0"),
                new BigDecimal("25.0"),
                new BigDecimal("10.0"),
                60,
                new BigDecimal("15.0"),
                45
        );

        // Map til entity
        PackData packData = PackDataMapper.toEntity(dto, module);

        // Assertions
        assertEquals(1L, packData.getPackDataId());
        assertEquals(module, packData.getModule());
        assertEquals(3, packData.getCellQuantity());
        assertEquals(new BigDecimal("1.5"), packData.getCellWeightKg());
        assertEquals(new BigDecimal("45.0"), packData.getGrossWeightKg());
        assertEquals(new BigDecimal("10.0"), packData.getNominalCapacityKwh());
        assertEquals(new BigDecimal("12.0"), packData.getPeakCapacityKwh());
        assertEquals(new BigDecimal("48.0"), packData.getNominalVoltageV());
        assertEquals(new BigDecimal("50.0"), packData.getPeakVoltageV());
        assertEquals(new BigDecimal("42.0"), packData.getCutoffVoltageV());
        assertEquals(new BigDecimal("20.0"), packData.getNominalDischargeA());
        assertEquals(new BigDecimal("25.0"), packData.getPeakDischargeA());
        assertEquals(new BigDecimal("10.0"), packData.getNominalAcDcChargeA());
        assertEquals(60, packData.getNominalChargeTimeMin());
        assertEquals(new BigDecimal("15.0"), packData.getPeakDcChargeA());
        assertEquals(45, packData.getPeakChargeTimeMin());
    }
}
