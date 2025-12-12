package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.dto.PackDataMapper;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import dk.ek.onidesign.catalog.service.PackDataService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/packdatas")
public class PackDataController {

    private final PackDataService packDataService;

    public PackDataController(PackDataService packDataService) {
        this.packDataService = packDataService;
    }

    @GetMapping("/{moduleId}")
    public ResponseEntity<PackDataDto> getPackData(@PathVariable Long moduleId) {
        PackDataDto dto = packDataService.getByModuleId(moduleId);
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

}
