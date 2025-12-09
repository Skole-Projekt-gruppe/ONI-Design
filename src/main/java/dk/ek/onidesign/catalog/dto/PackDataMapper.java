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
        PackData packData = new PackData();
        packData.setPackDataId(dto.packDataId());
        packData.setModule(module);

        packData.setCellQuantity(dto.cellQuantity());
        packData.setCellWeightKg(dto.cellWeightKg());
        packData.setGrossWeightKg(dto.grossWeightKg());
        packData.setNominalCapacityKwh(dto.nominalCapacityKwh());
        packData.setPeakCapacityKwh(dto.peakCapacityKwh());
        packData.setNominalVoltageV(dto.nominalVoltageV());
        packData.setPeakVoltageV(dto.peakVoltageV());
        packData.setCutoffVoltageV(dto.cutoffVoltageV());

        packData.setNominalDischargeA(dto.nominalDischargeA());
        packData.setPeakDischargeA(dto.peakDischargeA());
        packData.setNominalAcDcChargeA(dto.nominalAcDcChargeA());
        packData.setNominalChargeTimeMin(dto.nominalChargeTimeMin());
        packData.setPeakDcChargeA(dto.peakDcChargeA());
        packData.setPeakChargeTimeMin(dto.peakChargeTimeMin());

        return packData;
    }

}

