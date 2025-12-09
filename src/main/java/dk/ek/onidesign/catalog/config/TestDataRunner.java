package dk.ek.onidesign.catalog.config;

import dk.ek.onidesign.catalog.entity.Module;
import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import dk.ek.onidesign.catalog.entity.*;
import dk.ek.onidesign.catalog.repository.ModuleRepository;

import java.math.BigDecimal;

@Component
public class TestDataRunner implements CommandLineRunner {

    @Autowired
    private ModuleRepository moduleRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        // Module + PackData
        Module module = new Module("Modul 1", "Beskrivelse", "image.png");
        PackData packData = new PackData();
        packData.setModule(module);
        module.setPackData(packData);

        // TestSequence
        TestSequence sequence = new TestSequence(module, "Test 1", "Beskrivelse af testen", 1);
        module.addTestSequence(sequence);

        // TestResult
        TestResult result = new TestResult(
                sequence,                   // TestSequence vi tilknytter
                new BigDecimal("3.7"),      // startingVoltageV
                new BigDecimal("4.2"),      // peakChargeVoltageV
                new BigDecimal("3.5"),      // dischargeVoltageV
                new BigDecimal("0.05"),     // voltageImbalanceMaxV
                new BigDecimal("25.0"),     // nominalTempC
                new BigDecimal("45.0"),     // maxTempC
                new BigDecimal("20.0"),     // minTempC
                new BigDecimal("50.0"),     // maxDischargeA
                120,                        // sustainedMaxDischargeSec
                false,                      // tempCutoffReached
                0,                          // faultsEncountered
                null                        // faultType
        );
        sequence.addTestResult(result);

        // Gem alt
        moduleRepository.save(module);

        System.out.println("Module med PackData, TestSequence og TestResult gemt!");
    }
}
