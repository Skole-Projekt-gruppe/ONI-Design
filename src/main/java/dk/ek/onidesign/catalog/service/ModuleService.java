package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.dto.ModuleMapper;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import org.springframework.stereotype.Service;

@Service
public class ModuleService {

    private final ModuleRepository moduleRepository;

    public ModuleService(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

}
