package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;

public class PackDataMapper {

    public static PackDataDto toDto(PackData pack) {
        return new PackDataDto(
                pack.getPackDataId(),
                pack.getModule().getModuleId(),
                pack.getCellQuantity(),
                pack.getCellWeightKg(),
                pack.getGrossWeightKg(),
                pack.getNominalCapacityKwh(),
                pack.getPeakCapacityKwh(),
                pack.getNominalVoltageV(),
                pack.getPeakVoltageV(),
                pack.getCutoffVoltageV(),
                pack.getNominalDischargeA(),
                pack.getPeakDischargeA(),
                pack.getNominalAcDcChargeA(),
                pack.getNominalChargeTimeMin(),
                pack.getPeakDcChargeA(),
                pack.getPeakChargeTimeMin()
        );
    }

    public static PackData toEntity(PackDataDto dto, Module module) {
        PackData p = new PackData();
        p.setPackDataId(dto.packDataId());
        p.setModule(module);

        p.setCellQuantity(dto.cellQuantity());
        p.setCellWeightKg(dto.cellWeightKg());
        p.setGrossWeightKg(dto.grossWeightKg());
        p.setNominalCapacityKwh(dto.nominalCapacityKwh());
        p.setPeakCapacityKwh(dto.peakCapacityKwh());
        p.setNominalVoltageV(dto.nominalVoltageV());
        p.setPeakVoltageV(dto.peakVoltageV());
        p.setCutoffVoltageV(dto.cutoffVoltageV());

        p.setNominalDischargeA(dto.nominalDischargeA());
        p.setPeakDischargeA(dto.peakDischargeA());
        p.setNominalAcDcChargeA(dto.nominalAcDcChargeA());
        p.setNominalChargeTimeMin(dto.nominalChargeTimeMin());
        p.setPeakDcChargeA(dto.peakDcChargeA());
        p.setPeakChargeTimeMin(dto.peakChargeTimeMin());

        return p;
    }

}
