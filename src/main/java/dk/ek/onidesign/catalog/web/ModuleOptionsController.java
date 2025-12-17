package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.ModuleOptionDto;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

@RestController
public class ModuleOptionsController {

    private final ModuleRepository moduleRepository;

    public ModuleOptionsController(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    @GetMapping("/api/modules/options")
    public List<ModuleOptionDto> getModuleOptions() {
        return moduleRepository.findAll()
                .stream()
                .sorted(Comparator.comparingLong(m -> m.getModuleId() == null ? 0L : m.getModuleId()))
                .map(m -> new ModuleOptionDto(m.getModuleId(), m.getModuleName()))
                .toList();
    }
}
