package dk.ek.onidesign.catalog.dto;

public record TestSequenceDto(
        Long testSequenceId,
        Long moduleId,
        String name,
        String description,
        Integer sequenceOrder
) {}

