package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.model.Module;
import dk.ek.onidesign.catalog.service.ModuleServiceC;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ModuleSearchController {

    private final ModuleServiceC service;

    public ModuleSearchController(ModuleServiceC service) {
        this.service = service;
    }

    // GET /api/modules?search=...
    @GetMapping("/api/modules")
    public List<Module> list(@RequestParam(required = false) String search) {
        // Vi holder det simpelt: altid sortér på moduleName stigende
        return service.getAll(search, "moduleName", "asc");
    }
}