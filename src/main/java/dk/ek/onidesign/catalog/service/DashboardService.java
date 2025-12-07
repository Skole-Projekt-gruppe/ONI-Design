package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.model.PackData;
import dk.ek.onidesign.catalog.model.Module;
import dk.ek.onidesign.catalog.repository.ModuleRepositoryC;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import dk.ek.onidesign.catalog.dto.DashboardDto;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final ModuleRepositoryC moduleRepository;
    private final PackDataRepository packDataRepository;

    public DashboardService(ModuleRepositoryC moduleRepository,
                            PackDataRepository packDataRepository) {
        this.moduleRepository = moduleRepository;
        this.packDataRepository = packDataRepository;
    }

    public DashboardDto getModuleDashboard(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new RuntimeException("Module not found"));

        PackData packData = packDataRepository.findByModule_ModuleId(moduleId);

        DashboardDto dto = new DashboardDto();
        dto.setModuleName(module.getModuleName());
        dto.setDescription(module.getDescription());
        dto.setNominalCapacityKwh(packData.getNominalCapacityKwh());
        dto.setGrossWeightKg(packData.getGrossWeightKg());
        dto.setNominalVoltageV(packData.getNominalVoltageV());

        return dto;
    }
}
