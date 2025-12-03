package dk.ek.onidesign.config;

import dk.ek.onidesign.entity.Module;
import dk.ek.onidesign.entity.PackData;
import dk.ek.onidesign.repository.ModuleRepository;
import dk.ek.onidesign.repository.PackDataRepository;
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
            if (moduleRepository.count() == 0) {
                Module module = new Module();
                module.setModuleName("MODULE 01");
                module.setDescription("MODULE 01 is a compact battery module design...");
                moduleRepository.save(module);

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


