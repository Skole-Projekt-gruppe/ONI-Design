package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.dto.PackDataMapper;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.exception.ModuleNotFoundException;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.exception.NotFoundException;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import org.springframework.stereotype.Service;

@Service
public class PackDataService {

    private final PackDataRepository packDataRepository;
    private final ModuleRepository moduleRepository;

    public PackDataService(PackDataRepository packDataRepository,
                           ModuleRepository moduleRepository) {
        this.packDataRepository = packDataRepository;
        this.moduleRepository = moduleRepository;
    }

    public PackDataDto getByModuleId(Long moduleId) {
        PackData pack = packDataRepository.findByModule_ModuleId(moduleId);

        if (pack == null) {
            throw new NotFoundException("PackData for moduleId " + moduleId + " blev ikke fundet");
        }
        return PackDataMapper.toDto(pack);
    }


    public PackDataDto createPackData(PackDataDto dto) {
        // 1) Find modulet (ellers 404 via custom exception)
        Module module = moduleRepository.findById(dto.moduleId())
                .orElseThrow(() -> new ModuleNotFoundException(dto.moduleId()));

        // 2) DTO -> entity
        PackData entity = PackDataMapper.toEntity(dto, module);

        // 3) Gem i DB
        PackData saved = packDataRepository.save(entity);

        // 4) Entity -> DTO
        return PackDataMapper.toDto(saved);
    }
}
