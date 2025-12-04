package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.model.Module;
import dk.ek.onidesign.catalog.model.PackData;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import dk.ek.onidesign.catalog.service.DashboardService;
import dk.ek.onidesign.catalog.web.DashboardDto;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    ModuleRepository moduleRepository;

    @Mock
    PackDataRepository packDataRepository;

    @InjectMocks
    DashboardService dashboardService;

    @Test
    void getModuleDashboard_returnsDtoWithData() {
        Module module = new Module();
        module.setModuleId(1L);
        module.setModuleName("MODULE 01");
        module.setDescription("Test module");

        PackData packData = new PackData();
        packData.setModule(module);
        packData.setNominalCapacityKwh(new BigDecimal("9.2"));
        packData.setGrossWeightKg(new BigDecimal("45.0"));
        packData.setNominalVoltageV(new BigDecimal("388"));

        when(moduleRepository.findById(1L)).thenReturn(Optional.of(module));
        when(packDataRepository.findByModule_ModuleId(1L)).thenReturn(packData);

        DashboardDto dto = dashboardService.getModuleDashboard(1L);

        assertThat(dto.getModuleName()).isEqualTo("MODULE 01");
        assertThat(dto.getDescription()).isEqualTo("Test module");
        assertThat(dto.getNominalCapacityKwh()).isEqualByComparingTo("9.2");
        assertThat(dto.getGrossWeightKg()).isEqualByComparingTo("45.0");
        assertThat(dto.getNominalVoltageV()).isEqualByComparingTo("388");
    }
}
