package dk.ek.onidesign.catalog;

import dk.ek.onidesign.catalog.dto.ModuleDto;
import dk.ek.onidesign.catalog.service.PDFService;

import java.nio.file.Files;
import java.nio.file.Path;

public class PdfTestMain {
    public static void main(String[] args) throws Exception {

        PDFService pdfService = new PDFService();

        ModuleDto example = TestDataFactory.createMockModule();

        byte[] pdf = pdfService.generatePdf(example);

        Files.write(Path.of("manual_test.pdf"), pdf);

        System.out.println("PDF saved!");
    }
}
