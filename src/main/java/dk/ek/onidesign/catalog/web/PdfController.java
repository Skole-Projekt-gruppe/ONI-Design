package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.service.PDFService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PdfController
{

    private final PDFService pdfService;

    public PdfController(PDFService pdfService)
    {
        this.pdfService = pdfService;
    }

    @GetMapping("pdf")
    public ResponseEntity<byte[]> downloadPdf()
    {
        byte[] pdfBytes = pdfService.generatePdf();

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"output.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}
