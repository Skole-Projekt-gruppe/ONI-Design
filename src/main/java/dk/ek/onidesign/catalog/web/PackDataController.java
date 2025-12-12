package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.dto.PackDataMapper;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import dk.ek.onidesign.catalog.service.PackDataService;
import jakarta.validation.Valid;
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
        return ResponseEntity.ok(packDataService.getByModuleId(moduleId));
    }

    @PostMapping
    public ResponseEntity<PackDataDto> createPackData(@Valid @RequestBody PackDataDto dto) {
        PackDataDto saved = packDataService.createPackData(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
