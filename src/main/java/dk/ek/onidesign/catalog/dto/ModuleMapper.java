package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.Module;

import java.util.List;

public class ModuleMapper {

    public static ModuleDto toDto(Module module) {
        if (module == null) {
            return null;
        }

        // Pakke-data kan være null
        var packDataDto = module.getPackData() != null
                ? PackDataMapper.toDto(module.getPackData())
                : null;

        // Til vores visning bruger vi ikke testSequences → tom liste
        List<TestSequenceDto> testSequenceDtos = List.of();

        return new ModuleDto(
                module.getModuleId(),
                packDataDto,
                testSequenceDtos,
                module.getModuleName(),
                module.getDescription(),
                module.getOverviewImageUrl(),
                module.getCreatedAt(),
                module.getUpdatedAt()
        );
    }

    public static Module toEntity(ModuleDto moduleDto) {
        Module module = new Module();

        module.setModuleId(moduleDto.moduleId());
        module.setPackData(PackDataMapper.toEntity(moduleDto.packData()));

        // Mapper hver testSequenceDto i moduleDto.testSequences() til en TestSequence entity,
        // sætter modulet på hver TestSequence (så relationen bliver korrekt),
        // samler alle TestSequence entities i en liste, og sætter listen på module.
        module.setTestSequences(moduleDto.testSequences().stream()
                .map(testSequenceDto -> {
                    var testSequence = TestSequenceMapper.toEntity(testSequenceDto);
                    testSequence.setModule(module);
                    return testSequence;
                })
                .toList()
        );

        module.setModuleName(moduleDto.moduleName());
        module.setDescription(moduleDto.description());
        module.setOverviewImageUrl(moduleDto.overviewImageUrl());
        module.setCreatedAt(moduleDto.createdAt());
        module.setUpdatedAt(moduleDto.updatedAt());

        return module;
    }
}

