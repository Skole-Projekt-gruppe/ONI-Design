package dk.ek.onidesign.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.ek.onidesign.catalog.dto.ModulePackDataDto;
import dk.ek.onidesign.catalog.service.ModuleService;
import dk.ek.onidesign.catalog.web.ModuleController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ModuleController.class)
public class ModuleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ModuleService moduleService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createModulePackData_returnsCreatedDto() throws Exception {
        ModulePackDataDto inputDto = new ModulePackDataDto(
                null,
                "Test Module",
                "Test description",
                "image.png",
                null,
                10,
                new BigDecimal("2.5"),
                new BigDecimal("25.0"),
                new BigDecimal("5.5"),
                new BigDecimal("6.0"),
                new BigDecimal("388"),
                new BigDecimal("400"),
                new BigDecimal("300"),
                new BigDecimal("20"),
                new BigDecimal("25"),
                new BigDecimal("15"),
                60,
                new BigDecimal("30"),
                45
        );

        ModulePackDataDto savedDto = new ModulePackDataDto(
                1L,
                "Test Module",
                "Test description",
                "image.png",
                1L,
                10,
                new BigDecimal("2.5"),
                new BigDecimal("25.0"),
                new BigDecimal("5.5"),
                new BigDecimal("6.0"),
                new BigDecimal("388"),
                new BigDecimal("400"),
                new BigDecimal("300"),
                new BigDecimal("20"),
                new BigDecimal("25"),
                new BigDecimal("15"),
                60,
                new BigDecimal("30"),
                45
        );

        when(moduleService.createModulePackData(any(ModulePackDataDto.class)))
                .thenReturn(savedDto);

        mockMvc.perform(post("/api/modules/packdata")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.moduleId").value(1))
                .andExpect(jsonPath("$.moduleName").value("Test Module"))
                .andExpect(jsonPath("$.description").value("Test description"))
                .andExpect(jsonPath("$.cellQuantity").value(10))
                .andExpect(jsonPath("$.grossWeightKg").value(25.0))
                .andExpect(jsonPath("$.nominalCapacityKwh").value(5.5))
                .andExpect(jsonPath("$.peakCapacityKwh").value(6.0))
                .andExpect(jsonPath("$.nominalVoltageV").value(388))
                .andExpect(jsonPath("$.peakVoltageV").value(400))
                .andExpect(jsonPath("$.cutoffVoltageV").value(300))
                .andExpect(jsonPath("$.nominalDischargeA").value(20))
                .andExpect(jsonPath("$.peakDischargeA").value(25))
                .andExpect(jsonPath("$.nominalAcDcChargeA").value(15))
                .andExpect(jsonPath("$.nominalChargeTimeMin").value(60))
                .andExpect(jsonPath("$.peakDcChargeA").value(30))
                .andExpect(jsonPath("$.peakChargeTimeMin").value(45));
    }
}