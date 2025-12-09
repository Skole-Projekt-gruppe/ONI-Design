package dk.ek.onidesign.catalog.dto;


import java.time.LocalDateTime;
import java.util.List;

public record ModuleDto(
        Long moduleId,
        PackDataDto packData,// DTO til Dropdown med PackData info
        List<TestSequenceDto> testSequences,
        String moduleName,
        String description,
        String overviewImageUrl,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {}
