
package dk.ek.onidesign.catalog.dto;

public record ModuleDto(
        Long moduleId,
        String moduleName,
        String description,
        String overviewImageUrl
) {}