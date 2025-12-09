package dk.ek.onidesign.repository;

import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PackDataRepositoryTest {

    @Autowired
    ModuleRepository moduleRepository;

    @Autowired
    PackDataRepository packDataRepository;

    @Test
    void findByModuleId_returnsPackDataForModule() {
        Module module = new Module();
        module.setModuleName("Test module");
        module.setDescription("Test description");
        module = moduleRepository.save(module);

        PackData packData = new PackData();
        packData.setModule(module);
        packData.setNominalCapacityKwh(new BigDecimal("9.2"));
        packData.setGrossWeightKg(new BigDecimal("45.0"));
        packData.setNominalVoltageV(new BigDecimal("388"));
        packDataRepository.save(packData);

        PackData result = packDataRepository.findByModule_ModuleId(module.getModuleId());

        assertThat(result).isNotNull();
        assertThat(result.getModule().getModuleId()).isEqualTo(module.getModuleId());
        assertThat(result.getNominalCapacityKwh()).isEqualByComparingTo("9.2");
    }
}