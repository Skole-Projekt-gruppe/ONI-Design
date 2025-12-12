package dk.ek.onidesign.catalog.config;

import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ModuleRepository moduleRepository,
                               PackDataRepository packDataRepository) {
        return args -> {

            // Find eller opret modulet
            Module module = moduleRepository.findById(1L)
                    .orElseGet(() -> {
                        Module m = new Module();
                        m.setModuleName("Modul 1");
                        m.setDescription("Beskrivelse");
                        return moduleRepository.save(m);
                    });

            // Sørg for packData findes for moduleId=1
            PackData existing = packDataRepository.findByModule_ModuleId(module.getModuleId());
            if (existing == null) {
                PackData packData = new PackData();
                packData.setModule(module);
                packData.setCellQuantity(510);
                packData.setGrossWeightKg(new BigDecimal("45.0"));
                packData.setNominalCapacityKwh(new BigDecimal("9.2"));
                packData.setPeakCapacityKwh(new BigDecimal("10.7"));
                packData.setNominalVoltageV(new BigDecimal("388"));
                packDataRepository.save(packData);
            }
        };
    }
}



