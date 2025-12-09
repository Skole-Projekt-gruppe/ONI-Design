package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.Module;

public class ModuleMapper {

    public static ModuleDto toDto(Module module) {
        return new ModuleDto(
                module.getModuleId(),
                module.getModuleName(),
                module.getDescription(),
                module.getOverviewImageUrl()
        );
    }

    public static Module toEntity(ModuleDto dto) {
        Module module = new Module();
        module.setModuleName(dto.moduleName());
        module.setDescription(dto.description());
        module.setOverviewImageUrl(dto.overviewImageUrl());
        return module;
    }
}
