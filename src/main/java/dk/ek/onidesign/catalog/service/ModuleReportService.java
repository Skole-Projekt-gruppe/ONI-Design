package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.*;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.entity.TestSequence;
import dk.ek.onidesign.catalog.exception.ModuleNotFoundException;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import org.springframework.stereotype.Service;

@Service
public class ModuleReportService {

    private final ModuleRepository moduleRepository;
    private final PDFService pdfService;

    public ModuleReportService(ModuleRepository moduleRepository, PDFService pdfService) {
        this.moduleRepository = moduleRepository;
        this.pdfService = pdfService;
    }

    public byte[] generatePdf(Long moduleId) {
        Module module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ModuleNotFoundException(moduleId));
        return pdfService.generatePdf(module);
    }
}
