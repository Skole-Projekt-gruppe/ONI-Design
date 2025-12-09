package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.service.ModuleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModulesController {

    private final ModuleService service;

    public ModulesController(ModuleService service) {
        this.service = service;
    }

    // GET /api/modules?search=...
    @GetMapping("/api/modules")
    public List<ModuleDto> list(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "moduleName") String sortField,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        return service.getAll(search, sortField, sortDir);
    }
}