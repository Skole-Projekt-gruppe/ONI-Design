package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public ModulePackDataDto createModulePackData(ModulePackDataDto dto) {
        Module module = new Module();
        module.setModuleName(dto.moduleName());
        module.setDescription(dto.description());
        module.setOverviewImageUrl(dto.overviewImageUrl());

        PackData pack = new PackData();
        pack.setModule(module); // sætter også module.setPackData(this)
        pack.setCellQuantity(dto.cellQuantity());
        pack.setCellWeightKg(dto.cellWeightKg());
        pack.setGrossWeightKg(dto.grossWeightKg());
        pack.setNominalCapacityKwh(dto.nominalCapacityKwh());
        pack.setPeakCapacityKwh(dto.peakCapacityKwh());
        pack.setNominalVoltageV(dto.nominalVoltageV());
        pack.setPeakVoltageV(dto.peakVoltageV());
        pack.setCutoffVoltageV(dto.cutoffVoltageV());
        pack.setNominalDischargeA(dto.nominalDischargeA());
        pack.setPeakDischargeA(dto.peakDischargeA());
        pack.setNominalAcDcChargeA(dto.nominalAcDcChargeA());
        pack.setNominalChargeTimeMin(dto.nominalChargeTimeMin());
        pack.setPeakDcChargeA(dto.peakDcChargeA());
        pack.setPeakChargeTimeMin(dto.peakChargeTimeMin());

        // sikrer bi-direktionalitet
        module.setPackData(pack);

        //cascade sørger for PackData
        moduleRepository.save(module);

        // Returner DTO i stedet for entity
        return new ModulePackDataDto(
                module.getModuleId(),
                module.getModuleName(),
                module.getDescription(),
                module.getOverviewImageUrl(),
                pack.getPackDataId(),
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
}
