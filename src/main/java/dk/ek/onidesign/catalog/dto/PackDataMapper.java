package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.PackData;

public class PackDataMapper {

    public static PackDataDto toDto(PackData packData) {
        return new PackDataDto(
                packData.getPackDataId(),
                ModuleMapper.toDto(packData.getModule()),
                packData.getCellQuantity(),
                packData.getCellWeightKg(),
                packData.getGrossWeightKg(),
                packData.getNominalCapacityKwh(),
                packData.getPeakCapacityKwh(),
                packData.getNominalVoltageV(),
                packData.getPeakVoltageV(),
                packData.getCutoffVoltageV(),
                packData.getNominalDischargeA(),
                packData.getPeakDischargeA(),
                packData.getNominalAcDcChargeA(),
                packData.getNominalChargeTimeMin(),
                packData.getPeakDcChargeA(),
                packData.getPeakChargeTimeMin()
        );
    }

    public static PackData toEntity(PackDataDto packDataDto) {
        PackData packData = new PackData();

        packData.setPackDataId(packDataDto.packDataId());
        packData.setModule(ModuleMapper.toEntity(packDataDto.module()));
        packData.setCellQuantity(packDataDto.cellQuantity());
        packData.setCellWeightKg(packDataDto.cellWeightKg());
        packData.setGrossWeightKg(packDataDto.grossWeightKg());
        packData.setNominalCapacityKwh(packDataDto.nominalCapacityKwh());
        packData.setPeakCapacityKwh(packDataDto.peakCapacityKwh());
        packData.setNominalVoltageV(packDataDto.nominalVoltageV());
        packData.setPeakVoltageV(packDataDto.peakVoltageV());
        packData.setCutoffVoltageV(packDataDto.cutoffVoltageV());
        packData.setNominalDischargeA(packDataDto.nominalDischargeA());
        packData.setPeakDischargeA(packDataDto.peakDischargeA());
        packData.setNominalAcDcChargeA(packDataDto.nominalAcDcChargeA());
        packData.setNominalChargeTimeMin(packDataDto.nominalChargeTimeMin());
        packData.setPeakDcChargeA(packDataDto.peakDcChargeA());
        packData.setPeakChargeTimeMin(packDataDto.peakChargeTimeMin());

        return packData;
    }
}
