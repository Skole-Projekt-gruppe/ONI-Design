package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.dto.TestSequenceTestResultDto;
import dk.ek.onidesign.catalog.service.PDFService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PDFServiceTest {

    @Test
    void testGeneratePdf() {
        PDFService service = new PDFService();

        ModulePackDataDto module = new ModulePackDataDto(
                1L,
                "Module 01",
                "Test module description",
                null,
                10L,
                96,
                new BigDecimal("0.5"),
                new BigDecimal("12.4"),
                new BigDecimal("10.2"),
                new BigDecimal("12.5"),
                new BigDecimal("48.0"),
                new BigDecimal("52.0"),
                new BigDecimal("40.0"),
                new BigDecimal("100.0"),
                new BigDecimal("150.0"),
                new BigDecimal("80.0"),
                60,
                new BigDecimal("120.0"),
                30
        );

        TestSequenceTestResultDto result = new TestSequenceTestResultDto(
                1L,
                1L,
                "Sequence A",
                "Basic test sequence",
                1,
                1L,
                new BigDecimal("48.5"),
                new BigDecimal("52.7"),
                new BigDecimal("40.2"),
                new BigDecimal("0.3"),
                new BigDecimal("25.0"),
                new BigDecimal("45.0"),
                new BigDecimal("10.0"),
                new BigDecimal("120.0"),
                30,
                false,
                0,
                "None"
        );

        byte[] pdf = service.generatePdf(module, result);

        assertNotNull(pdf);
        assertTrue(pdf.length > 100); // Should not be empty
    }
}
