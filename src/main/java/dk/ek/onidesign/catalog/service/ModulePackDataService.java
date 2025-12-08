package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.dto.PackDataDto;
import org.springframework.stereotype.Service;

@Service
public class ModulePackDataService {

    private final ModuleService moduleService;
    private final PackDataService packDataService;

    public ModulePackDataService(ModuleService moduleService, PackDataService packDataService) {
        this.moduleService = moduleService;
        this.packDataService = packDataService;
    }

}
