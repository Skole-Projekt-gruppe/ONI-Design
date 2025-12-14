package dk.ek.onidesign.web;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.exception.GlobalExceptionHandler;
import dk.ek.onidesign.catalog.service.PackDataService;
import dk.ek.onidesign.catalog.web.PackDataController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PackDataController.class)
@Import(GlobalExceptionHandler.class)   // <- din handler ligger i .exception
class PackDataControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    PackDataService packDataService;

    @Test
    void createPackData_returns201_whenRequestIsValid() throws Exception {
        PackDataDto dto = new PackDataDto(
                10L,
                1L,
                510,
                new BigDecimal("0.09"),
                new BigDecimal("45.00"),
                new BigDecimal("9.20"),
                new BigDecimal("10.70"),
                new BigDecimal("388.00"),
                new BigDecimal("420.00"),
                new BigDecimal("320.00"),
                new BigDecimal("150.0"),
                new BigDecimal("220.0"),
                new BigDecimal("80.0"),
                60,
                new BigDecimal("120.0"),
                30
        );

        when(packDataService.createPackData(any(PackDataDto.class))).thenReturn(dto);

        String json = """
                {
                  "packDataId": null,
                  "moduleId": 1,
                  "cellQuantity": 510,
                  "cellWeightKg": 0.09,
                  "grossWeightKg": 45.00,
                  "nominalCapacityKwh": 9.20,
                  "peakCapacityKwh": 10.70,
                  "nominalVoltageV": 388.00,
                  "peakVoltageV": 420.00,
                  "cutoffVoltageV": 320.00,
                  "nominalDischargeA": 150.0,
                  "peakDischargeA": 220.0,
                  "nominalAcDcChargeA": 80.0,
                  "nominalChargeTimeMin": 60,
                  "peakDcChargeA": 120.0,
                  "peakChargeTimeMin": 30
                }
                """;

        mockMvc.perform(post("/api/packdatas")
                        .contentType(APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.packDataId").value(10L))
                .andExpect(jsonPath("$.moduleId").value(1L))
                .andExpect(jsonPath("$.cellQuantity").value(510));
    }

    @Test
    void createPackData_returns400_whenValidationFails() throws Exception {
        // cellQuantity = 0 -> bryder @Positive på DTO'en
        String invalidJson = """
                {
                  "packDataId": null,
                  "moduleId": 1,
                  "cellQuantity": 0
                }
                """;

        mockMvc.perform(post("/api/packdatas")
                        .contentType(APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors[0]").exists());
    }
}
