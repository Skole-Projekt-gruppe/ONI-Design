package dk.ek.onidesign.service;

import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import dk.ek.onidesign.catalog.service.SearchService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SearchServiceTest {

    @Mock
    ModuleRepository moduleRepository;

    @InjectMocks
    SearchService searchService;

    @Test
    void getAll_noSearch_callsFindAll() {
        // Arrange
        List<Module> modules = List.of(new Module(), new Module());
        when(moduleRepository.findAll(any(Sort.class))).thenReturn(modules);

        // Act
        List<Module> result = searchService.getAll(null, null, null);

        // Assert
        assertEquals(modules, result);
        verify(moduleRepository).findAll(any(Sort.class));
        verify(moduleRepository, never())
                .findByModuleNameContainingIgnoreCase(anyString(), any(Sort.class));
    }

    @Test
    void getAll_withSearch_callsFindByModuleNameContainingIgnoreCase() {
        // Arrange
        String search = "test";
        List<Module> modules = List.of(new Module());
        when(moduleRepository.findByModuleNameContainingIgnoreCase(eq(search), any(Sort.class)))
                .thenReturn(modules);

        // Act
        List<Module> result = searchService.getAll(search, null, null);

        // Assert
        assertEquals(modules, result);
        verify(moduleRepository).findByModuleNameContainingIgnoreCase(eq(search), any(Sort.class));
        verify(moduleRepository, never()).findAll(any(Sort.class));
    }

    @Test
    void getAll_invalidSortDir_throwsIllegalArgumentException() {
        // Act + Assert
        assertThrows(IllegalArgumentException.class,
                () -> searchService.getAll(null, null, "forkert"));

        // Når der kastes exception, må repo slet ikke blive brugt
        verifyNoInteractions(moduleRepository);
    }
}