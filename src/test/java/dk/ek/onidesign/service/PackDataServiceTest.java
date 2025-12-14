package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.dto.PackDataDto;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.entity.PackData;
import dk.ek.onidesign.catalog.exception.ModuleNotFoundException;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.repository.PackDataRepository;
import dk.ek.onidesign.catalog.service.PackDataService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PackDataServiceTest {

    @Mock
    ModuleRepository moduleRepository;

    @Mock
    PackDataRepository packDataRepository;

    @InjectMocks
    PackDataService packDataService;

    @Test
    void createPackData_savesAndReturnsDto_whenModuleExists() {
        Long moduleId = 1L;

        // Arrange: module exists
        Module module = new Module();
        module.setModuleId(moduleId);
        module.setModuleName("MODULE 01");

        when(moduleRepository.findById(moduleId)).thenReturn(Optional.of(module));

        PackDataDto dto = new PackDataDto(
                null,
                moduleId,
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

        PackData savedEntity = new PackData();
        savedEntity.setPackDataId(10L);
        savedEntity.setModule(module);
        savedEntity.setCellQuantity(dto.cellQuantity());
        savedEntity.setGrossWeightKg(dto.grossWeightKg());
        savedEntity.setNominalCapacityKwh(dto.nominalCapacityKwh());
        savedEntity.setNominalVoltageV(dto.nominalVoltageV());

        when(packDataRepository.save(any(PackData.class))).thenReturn(savedEntity);

        // Act
        PackDataDto result = packDataService.createPackData(dto);

        // Assert
        assertThat(result.packDataId()).isEqualTo(10L);
        assertThat(result.moduleId()).isEqualTo(moduleId);
        assertThat(result.cellQuantity()).isEqualTo(510);
        assertThat(result.nominalCapacityKwh()).isEqualByComparingTo("9.20");

        verify(moduleRepository).findById(moduleId);
        verify(packDataRepository).save(any(PackData.class));
    }

    @Test
    void createPackData_throwsModuleNotFoundException_whenModuleMissing() {
        Long missingId = 99L;

        PackDataDto dto = new PackDataDto(
                null,
                missingId,
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

        when(moduleRepository.findById(missingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> packDataService.createPackData(dto))
                .isInstanceOf(ModuleNotFoundException.class)
                .hasMessageContaining("99");

        verify(moduleRepository).findById(missingId);
        verifyNoInteractions(packDataRepository);
    }
}
