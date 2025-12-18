package dk.ek.onidesign.catalog.config;

import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.Optional;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner initData(ModuleRepository moduleRepository,
                               PackDataRepository packDataRepository) {
        return args -> {

            // ✅ MODULE 01 (opret kun hvis den ikke findes)
            Module module1 = getOrCreateModuleByName(moduleRepository,
                    "MODULE 01",
                    "MODULE 01 is a compact battery module design...",
                    "/images/module01.png"
            );

            // ✅ PackData for MODULE 01 (kun hvis den mangler)
            ensurePackData(packDataRepository, module1,
                    510,
                    new BigDecimal("45.0"),
                    new BigDecimal("9.2"),
                    new BigDecimal("10.7"),
                    new BigDecimal("388")
            );

            // ✅ MODULE 02 (opret kun hvis den ikke findes)
            Module module2 = getOrCreateModuleByName(moduleRepository,
                    "MODULE 02",
                    "MODULE 02 is a second module variant for testing module switching.",
                    "/images/module02.png"
            );

            // ✅ PackData for MODULE 02 (kun hvis den mangler)
            ensurePackData(packDataRepository, module2,
                    480,
                    new BigDecimal("41.5"),
                    new BigDecimal("8.6"),
                    new BigDecimal("10.2"),
                    new BigDecimal("360")
            );
        };
    }

    private Module getOrCreateModuleByName(ModuleRepository repo,
                                           String name,
                                           String description,
                                           String overviewImageUrl) {

        Optional<Module> existing = repo.findAll()
                .stream()
                .filter(m -> name.equalsIgnoreCase(m.getModuleName()))
                .findFirst();

        if (existing.isPresent()) {
            Module m = existing.get();

            // Optional: hold fields opdaterede, uden at ændre data hvis I ikke vil.
            if (m.getDescription() == null || m.getDescription().isBlank()) {
                m.setDescription(description);
            }
            if (m.getOverviewImageUrl() == null || m.getOverviewImageUrl().isBlank()) {
                m.setOverviewImageUrl(overviewImageUrl);
            }

            return repo.save(m);
        }

        Module module = new Module();
        module.setModuleName(name);
        module.setDescription(description);
        module.setOverviewImageUrl(overviewImageUrl);
        return repo.save(module);
    }

    private void ensurePackData(PackDataRepository packRepo,
                                Module module,
                                int cellQuantity,
                                BigDecimal grossWeightKg,
                                BigDecimal nominalCapacityKwh,
                                BigDecimal peakCapacityKwh,
                                BigDecimal nominalVoltageV) {

        PackData existingPack = packRepo.findByModule_ModuleId(module.getModuleId());
        if (existingPack != null) {
            return;
        }

        PackData pack = new PackData();
        pack.setModule(module);
        pack.setCellQuantity(cellQuantity);
        pack.setGrossWeightKg(grossWeightKg);
        pack.setNominalCapacityKwh(nominalCapacityKwh);
        pack.setPeakCapacityKwh(peakCapacityKwh);
        pack.setNominalVoltageV(nominalVoltageV);

        packRepo.save(pack);
    }
}
