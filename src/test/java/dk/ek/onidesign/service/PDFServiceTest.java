package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.entity.TestSequence;
import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.service.PDFService;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PDFServiceTest {

    @Test
    void testGeneratePdf() {
        PDFService service = new PDFService();

        // -----------------------------
        // Opret Module + PackData
        // -----------------------------
        Module module = new Module("Module 01", "Test module description", null);

        PackData packData = new PackData();
        packData.setModule(module);
        packData.setCellQuantity(96);
        packData.setCellWeightKg(new BigDecimal("0.5"));
        packData.setGrossWeightKg(new BigDecimal("12.4"));
        packData.setNominalCapacityKwh(new BigDecimal("10.2"));
        packData.setPeakCapacityKwh(new BigDecimal("12.5"));
        packData.setNominalVoltageV(new BigDecimal("48.0"));
        packData.setPeakVoltageV(new BigDecimal("52.0"));
        packData.setCutoffVoltageV(new BigDecimal("40.0"));
        packData.setNominalDischargeA(new BigDecimal("100.0"));
        packData.setPeakDischargeA(new BigDecimal("150.0"));
        packData.setNominalAcDcChargeA(new BigDecimal("80.0"));
        packData.setNominalChargeTimeMin(60);
        packData.setPeakDcChargeA(new BigDecimal("120.0"));
        packData.setPeakChargeTimeMin(30);

        module.setPackData(packData);

        // -----------------------------
        // Opret TestSequence + TestResult
        // -----------------------------
        TestSequence ts = new TestSequence();
        ts.setModule(module);
        ts.setName("Sequence A");
        ts.setDescription("Basic test sequence");
        ts.setSequenceOrder(1);

        TestResult tr = new TestResult();
        tr.setTestSequence(ts);
        tr.setStartingVoltageV(new BigDecimal("48.5"));
        tr.setPeakChargeVoltageV(new BigDecimal("52.7"));
        tr.setDischargeVoltageV(new BigDecimal("40.2"));
        tr.setVoltageImbalanceMaxV(new BigDecimal("0.3"));
        tr.setNominalTempC(new BigDecimal("25.0"));
        tr.setMaxTempC(new BigDecimal("45.0"));
        tr.setMinTempC(new BigDecimal("10.0"));
        tr.setMaxDischargeA(new BigDecimal("120.0"));
        tr.setSustainedMaxDischargeSec(30);
        tr.setTempCutoffReached(false);
        tr.setFaultsEncountered(0);
        tr.setFaultType("None");

        ts.getTestResults().add(tr);
        module.getTestSequences().add(ts);

        // -----------------------------
        // Generer PDF
        // -----------------------------
        byte[] pdf = service.generatePdf(module);

        assertNotNull(pdf);
        assertTrue(pdf.length > 100, "PDF should not be empty");
    }
}
