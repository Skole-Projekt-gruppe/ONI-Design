package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    @PostMapping("/packdata")
    public ResponseEntity<ModulePackDataDto> createModulePackData(@RequestBody ModulePackDataDto dto) {
        ModulePackDataDto savedDto = moduleService.createModulePackData(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }
}
