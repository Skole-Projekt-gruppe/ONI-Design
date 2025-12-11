package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.service.ModuleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class ModuleController {

    private final ModuleService moduleService;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
    }

    // GET /api/modules?search=...&sortField=...&sortDir=...
    @GetMapping
    public List<ModuleDto> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "moduleName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return moduleService.getAll(search, sortField, sortDir);
    }
    // Post /api/modiles/packdata
    @PostMapping("/packdata")
    public ResponseEntity<ModulePackDataDto> createModulePackData(@RequestBody ModulePackDataDto dto) {
        ModulePackDataDto savedDto = moduleService.createModulePackData(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedDto);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        moduleService.deleteModule(id);
        return ResponseEntity.noContent().build();
    }
}
