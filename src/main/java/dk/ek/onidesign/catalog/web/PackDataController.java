package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.service.PackDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packdatas")
public class PackDataController {

    private final PackDataService packDataService;

    public PackDataController(PackDataService packDataService) {
        this.packDataService = packDataService;
    }

}
