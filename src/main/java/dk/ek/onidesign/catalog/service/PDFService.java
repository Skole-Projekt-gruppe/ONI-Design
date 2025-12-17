package dk.ek.onidesign.catalog.service;

import dk.ek.onidesign.catalog.dto.*;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.entity.TestSequence;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

@Service
public class PDFService {

    private static final DecimalFormat DF = new DecimalFormat("#,##0.###");
    private static final float LEFT = 50;
    private static final float RIGHT = 550;
    private static final float TOP = 800;
    private static final float SECTION_SPACING = 35f;
    private static final float LINE_SPACING = 18f;

    /**
     * Generer PDF for et modul inkl. alle testsekvenser og testresultater.
     */
    public byte[] generatePdf(Module module) {
        ModulePackDataDto moduleDto = toModulePackDataDto(module);

        try (PDDocument doc = new PDDocument()) {

            // Oversigtsside
            renderOverviewPage(doc, moduleDto);
            renderPackDataPage(doc, moduleDto);

            // Testsekvenser og resultater
            for (TestSequence ts : module.getTestSequences()) {
                for (TestResult tr : ts.getTestResults()) {
                    TestSequenceTestResultDto trDto = toTestSequenceTestResultDto(ts, tr, module.getModuleId());
                    renderTestPage(doc, trDto);
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            doc.save(out);
            return out.toByteArray();

        } catch (IOException e) {
            throw new RuntimeException("PDF generation failed: " + e.getMessage(), e);
        }
    }

    // --------------------------------------------------------------------------
    // DTO-MAPPING
    // --------------------------------------------------------------------------
    private ModulePackDataDto toModulePackDataDto(Module module) {
        var pack = module.getPackData();
        return new ModulePackDataDto(
                module.getModuleId(),
                module.getModuleName(),
                module.getDescription(),
                module.getOverviewImageUrl(),
                pack.getPackDataId(),
                pack.getCellQuantity(),
                pack.getCellWeightKg(),
                pack.getGrossWeightKg(),
                pack.getNominalCapacityKwh(),
                pack.getPeakCapacityKwh(),
                pack.getNominalVoltageV(),
                pack.getPeakVoltageV(),
                pack.getCutoffVoltageV(),
                pack.getNominalDischargeA(),
                pack.getPeakDischargeA(),
                pack.getNominalAcDcChargeA(),
                pack.getNominalChargeTimeMin(),
                pack.getPeakDcChargeA(),
                pack.getPeakChargeTimeMin()
        );
    }

    private TestSequenceTestResultDto toTestSequenceTestResultDto(TestSequence ts, TestResult tr, Long moduleId) {
        return new TestSequenceTestResultDto(
                ts.getTestSequenceId(),
                moduleId,
                ts.getName(),
                ts.getDescription(),
                ts.getSequenceOrder(),
                tr.getTestResultId(),
                tr.getStartingVoltageV(),
                tr.getPeakChargeVoltageV(),
                tr.getDischargeVoltageV(),
                tr.getVoltageImbalanceMaxV(),
                tr.getNominalTempC(),
                tr.getMaxTempC(),
                tr.getMinTempC(),
                tr.getMaxDischargeA(),
                tr.getSustainedMaxDischargeSec(),
                tr.isTempCutoffReached(),
                tr.getFaultsEncountered(),
                tr.getFaultType()
        );
    }

    // --------------------------------------------------------------------------
    // PDF RENDERING
    // --------------------------------------------------------------------------
    private void renderOverviewPage(PDDocument doc, ModulePackDataDto module) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
            float y = TOP;
            drawText(cs, module.moduleName(), LEFT, y, 32, true);
            y -= SECTION_SPACING;
            drawBigValue(cs, module.nominalCapacityKwh() + " kWh", LEFT, y);
            drawBigValue(cs, module.grossWeightKg() + " Kg", LEFT + 180, y);
            drawBigValue(cs, module.nominalVoltageV() + " Volt", LEFT + 340, y);
            y -= 60;
            drawDivider(cs, y);
            y -= 25;
            drawText(cs, "Module Overview", LEFT, y, 14, true);
            y -= SECTION_SPACING;
            drawMultiline(cs, module.description(), LEFT, y, 12, 450);
        }
    }

    private void renderPackDataPage(PDDocument doc, ModulePackDataDto p) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
            float y = TOP;
            drawText(cs, "Pack Data", LEFT, y, 20, true);
            y -= SECTION_SPACING;
            cs.setStrokingColor(150, 150, 150);
            drawDivider(cs, y);
            y -= SECTION_SPACING;
            float col1 = LEFT, col2 = LEFT + 250, y2 = TOP - 80;

            // LEFT
            y = listLine(cs, "Cell quantity", p.cellQuantity(), col1, y);
            y = listLine(cs, "Cell weight (kg)", p.cellWeightKg(), col1, y);
            y = listLine(cs, "Gross weight (kg)", p.grossWeightKg(), col1, y);
            y = listLine(cs, "Nominal capacity (kWh)", p.nominalCapacityKwh(), col1, y);
            y = listLine(cs, "Peak capacity (kWh)", p.peakCapacityKwh(), col1, y);

            // RIGHT
            y2 = listLine(cs, "Nominal voltage (V)", p.nominalVoltageV(), col2, y2);
            y2 = listLine(cs, "Peak voltage (V)", p.peakVoltageV(), col2, y2);
            y2 = listLine(cs, "Cutoff voltage (V)", p.cutoffVoltageV(), col2, y2);
            y2 = listLine(cs, "Nominal discharge (A)", p.nominalDischargeA(), col2, y2);
            y2 = listLine(cs, "Peak discharge (A)", p.peakDischargeA(), col2, y2);
            y2 = listLine(cs, "AC/DC charge (A)", p.nominalAcDcChargeA(), col2, y2);
            y2 = listLine(cs, "Nominal charge time (min)", p.nominalChargeTimeMin(), col2, y2);
            y2 = listLine(cs, "Peak DC charge (A)", p.peakDcChargeA(), col2, y2);
            y2 = listLine(cs, "Peak charge time (min)", p.peakChargeTimeMin(), col2, y2);
        }
    }

    private void renderTestPage(PDDocument doc, TestSequenceTestResultDto tr) throws IOException {
        PDPage page = new PDPage(PDRectangle.A4);
        doc.addPage(page);
        try (PDPageContentStream cs = new PDPageContentStream(doc, page)) {
            float y = TOP;
            drawText(cs, "Module Test Overview", LEFT, y, 20, true);
            y -= 40;
            drawDivider(cs, y);
            y -= 30;

            // LEFT - Test sequence
            drawText(cs, "Test sequence", LEFT, y, 14, true);
            y -= 25;
            y = listLine(cs, "Sequence name", tr.name(), LEFT, y);
            y = listLine(cs, "Description", tr.description(), LEFT, y);
            y = listLine(cs, "Order", tr.sequenceOrder(), LEFT, y);

            // RIGHT - Test result
            float col = LEFT + 250;
            float yRight = TOP - 70;
            drawText(cs, "Test result", col, TOP - 40, 14, true);
            yRight = listLine(cs, "Starting voltage (V)", tr.startingVoltageV(), col, yRight);
            yRight = listLine(cs, "Peak charge voltage (V)", tr.peakChargeVoltageV(), col, yRight);
            yRight = listLine(cs, "Discharge voltage (V)", tr.dischargeVoltageV(), col, yRight);
            yRight = listLine(cs, "Voltage imbalance max (V)", tr.voltageImbalanceMaxV(), col, yRight);
            yRight = listLine(cs, "Nominal temp (°C)", tr.nominalTempC(), col, yRight);
            yRight = listLine(cs, "Max temp (°C)", tr.maxTempC(), col, yRight);
            yRight = listLine(cs, "Min temp (°C)", tr.minTempC(), col, yRight);
            yRight = listLine(cs, "Max discharge (A)", tr.maxDischargeA(), col, yRight);
            yRight = listLine(cs, "Sustained discharge (s)", tr.sustainedMaxDischargeSec(), col, yRight);
            yRight = listLine(cs, "Temp cutoff reached", tr.tempCutoffReached(), col, yRight);
            yRight = listLine(cs, "Faults encountered", tr.faultsEncountered(), col, yRight);
            yRight = listLine(cs, "Fault type", tr.faultType(), col, yRight);
        }
    }

    // --------------------------------------------------------------------------
    // HELPER METODER
    // --------------------------------------------------------------------------
    private void drawBigValue(PDPageContentStream cs, String value, float x, float y) throws IOException {
        drawText(cs, value, x, y, 22, true);
    }

    private float listLine(PDPageContentStream cs, String label, Object value, float x, float y) throws IOException {
        drawText(cs, label + ": " + format(value), x, y, 12, false);
        return y - LINE_SPACING;
    }

    private void drawDivider(PDPageContentStream cs, float y) throws IOException {
        cs.moveTo(LEFT, y);
        cs.lineTo(RIGHT, y);
        cs.setLineWidth(1f);
        cs.stroke();
    }

    private String format(Object o) {
        if (o == null) return "-";
        if (o instanceof BigDecimal b) return DF.format(b);
        return String.valueOf(o);
    }

    private void drawText(PDPageContentStream cs, String txt, float x, float y, int size, boolean bold) throws IOException {
        cs.beginText();
        cs.setFont(bold ? PDType1Font.HELVETICA_BOLD : PDType1Font.HELVETICA, size);
        cs.newLineAtOffset(x, y);
        cs.showText(txt);
        cs.endText();
    }

    private float drawMultiline(PDPageContentStream cs, String text, float x, float y, int size, float width) throws IOException {
        if (text == null) return y;
        String[] words = text.split(" ");
        StringBuilder line = new StringBuilder();
        float leading = size + 3;

        for (String word : words) {
            String testLine = line + word + " ";
            if (testLine.length() * (size * 0.5) > width) {
                drawText(cs, line.toString(), x, y, size, false);
                y -= leading;
                line = new StringBuilder(word + " ");
            } else {
                line.append(word).append(" ");
            }
        }
        drawText(cs, line.toString(), x, y, size, false);
        return y - leading;
    }
}
