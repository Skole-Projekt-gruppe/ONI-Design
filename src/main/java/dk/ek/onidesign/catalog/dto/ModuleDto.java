package dk.ek.onidesign.catalog.dto;

import jakarta.validation.constraints.NotBlank;

public record ModuleDto(
        Long moduleId,

        @NotBlank(message = "Module name must not be blank")
        String moduleName,
        String description,
        String overviewImageUrl
) {}

