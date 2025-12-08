package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.service.ModulePackDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/modulepackdatas")
public class ModulePackDataController {

    private final ModulePackDataService modulePackDataService;

    public ModulePackDataController(ModulePackDataService modulePackDataService) {
        this.modulePackDataService = modulePackDataService;
    }

}
