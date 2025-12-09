package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.dto.ModuleMapper;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    public void createModule(ModuleDto moduleDto) {
        Module module = ModuleMapper.toEntity(moduleDto);
        moduleRepository.save(module);
    }

    // === NY: hent alle moduler med søg + sort ===
    public List<ModuleDto> getAll(String search, String sortField, String sortDir) {

        // 1) Bestem sorteringsretning (default = asc)
        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        // 2) Bestem sorteringsfelt (default = moduleName)
        String sortProperty =
                (sortField == null || sortField.isBlank())
                        ? "moduleName"
                        : sortField;

        Sort sort = Sort.by(direction, sortProperty);

        // 3) Hent fra DB – med søgning hvis der er search-tekst
        List<Module> modules;
        if (search != null && !search.isBlank()) {
            modules = moduleRepository.findByModuleNameContainingIgnoreCase(search, sort);
        } else {
            modules = moduleRepository.findAll(sort);
        }

        // 4) Map til DTO’er som frontend/HTML bruger
        return modules.stream()
                .map(ModuleMapper::toDto)
                .toList();
    }
}
