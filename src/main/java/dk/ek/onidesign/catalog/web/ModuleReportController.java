package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.service.ModuleReportService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/report")
public class ModuleReportController {

    private final ModuleReportService reportService;

    public ModuleReportController(ModuleReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/module/{id}")
    public ResponseEntity<byte[]> generateReport(@PathVariable Long id) {

        byte[] pdf = reportService.generatePdf(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"module-" + id + ".pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdf);
    }
}
