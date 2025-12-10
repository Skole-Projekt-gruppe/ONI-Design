package dk.ek.onidesign.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import dk.ek.onidesign.catalog.dto.TestSequenceTestResultDto;
import dk.ek.onidesign.catalog.service.TestSequenceService;
import dk.ek.onidesign.catalog.web.TestSequenceController;
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

@WebMvcTest(TestSequenceController.class)
public class TestSequenceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TestSequenceService testSequenceService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createTestSequenceTestResult_returnsCreatedDto() throws Exception {
        TestSequenceTestResultDto inputDto = new TestSequenceTestResultDto(
                null,
                1L,
                "Test Sequence 01",
                "Description of sequence",
                1,
                null,
                new BigDecimal("3.7"),
                new BigDecimal("4.2"),
                new BigDecimal("2.8"),
                new BigDecimal("0.05"),
                new BigDecimal("25"),
                new BigDecimal("40"),
                new BigDecimal("20"),
                new BigDecimal("10"),
                60,
                false,
                0,
                null
        );

        TestSequenceTestResultDto savedDto = new TestSequenceTestResultDto(
                1L,
                1L,
                "Test Sequence 01",
                "Description of sequence",
                1,
                1L,
                new BigDecimal("3.7"),
                new BigDecimal("4.2"),
                new BigDecimal("2.8"),
                new BigDecimal("0.05"),
                new BigDecimal("25"),
                new BigDecimal("40"),
                new BigDecimal("20"),
                new BigDecimal("10"),
                60,
                false,
                0,
                null
        );

        when(testSequenceService.createTestSequenceTestResult(any(TestSequenceTestResultDto.class)))
                .thenReturn(savedDto);

        mockMvc.perform(post("/api/testsequences/testresult")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.testSequenceId").value(1))
                .andExpect(jsonPath("$.moduleId").value(1))
                .andExpect(jsonPath("$.name").value("Test Sequence 01"))
                .andExpect(jsonPath("$.description").value("Description of sequence"))
                .andExpect(jsonPath("$.sequenceOrder").value(1))
                .andExpect(jsonPath("$.testResultId").value(1))
                .andExpect(jsonPath("$.startingVoltageV").value(3.7))
                .andExpect(jsonPath("$.peakChargeVoltageV").value(4.2))
                .andExpect(jsonPath("$.dischargeVoltageV").value(2.8))
                .andExpect(jsonPath("$.voltageImbalanceMaxV").value(0.05))
                .andExpect(jsonPath("$.nominalTempC").value(25))
                .andExpect(jsonPath("$.maxTempC").value(40))
                .andExpect(jsonPath("$.minTempC").value(20))
                .andExpect(jsonPath("$.maxDischargeA").value(10))
                .andExpect(jsonPath("$.sustainedMaxDischargeSec").value(60))
                .andExpect(jsonPath("$.tempCutoffReached").value(false))
                .andExpect(jsonPath("$.faultsEncountered").value(0))
                .andExpect(jsonPath("$.faultType").isEmpty());
    }
}
