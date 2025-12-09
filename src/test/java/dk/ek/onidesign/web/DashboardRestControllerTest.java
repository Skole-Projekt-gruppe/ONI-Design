package dk.ek.onidesign.web;

import dk.ek.onidesign.catalog.dto.DashboardDto;
import dk.ek.onidesign.catalog.service.DashboardService;
import dk.ek.onidesign.catalog.exception.ModuleNotFoundException;
import dk.ek.onidesign.catalog.web.DashboardRestController;
import dk.ek.onidesign.catalog.web.GlobalExceptionHandler;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DashboardRestController.class)
@Import(GlobalExceptionHandler.class)
class DashboardRestControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    DashboardService dashboardService;

    @Test
    void getDashboard_returnsDashboardDtoAsJson() throws Exception {
        DashboardDto dto = new DashboardDto();
        dto.setModuleName("MODULE 01");
        dto.setDescription("Test module");
        dto.setNominalCapacityKwh(new BigDecimal("9.2"));
        dto.setGrossWeightKg(new BigDecimal("45.0"));
        dto.setNominalVoltageV(new BigDecimal("388"));

        when(dashboardService.getModuleDashboard(1L)).thenReturn(dto);

        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.moduleName").value("MODULE 01"))
                .andExpect(jsonPath("$.description").value("Test module"))
                .andExpect(jsonPath("$.nominalCapacityKwh").value(9.2))
                .andExpect(jsonPath("$.grossWeightKg").value(45.0))
                .andExpect(jsonPath("$.nominalVoltageV").value(388));
    }

    @Test
    void getDashboard_returns404WhenModuleNotFound() throws Exception {
        when(dashboardService.getModuleDashboard(1L))
                .thenThrow(new ModuleNotFoundException(1L));

        mockMvc.perform(get("/api/dashboard"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }
}