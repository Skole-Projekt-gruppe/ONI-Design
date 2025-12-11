package dk.ek.onidesign.catalog.service;

// import dk.ek.onidesign.catalog.exception.NotFoundException;
import dk.ek.onidesign.catalog.entity.Module;
import dk.ek.onidesign.catalog.repository.ModuleRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchService
{

    private final ModuleRepository repository;

    public SearchService(ModuleRepository repository) {
        this.repository = repository;
    }

    /**
     * Hent alle modules – med mulighed for søgning og sortering.
     *
     * @param search    søgetekst (kan være null/tom, søger i moduleName)
     * @param sortField felt der skal sorteres på (fx "moduleName" eller "createdAt")
     * @param sortDir   "asc" eller "desc"
     */
    public List<Module> getAll(String search, String sortField, String sortDir) {

        // 1) Valider sortDir
        if (sortDir != null &&
                !sortDir.equalsIgnoreCase("asc") &&
                !sortDir.equalsIgnoreCase("desc")) {
            throw new IllegalArgumentException("Ugyldig sorteringsretning: " + sortDir);
        }

        // 2) Bestem retning (default = asc)
        Sort.Direction direction =
                "desc".equalsIgnoreCase(sortDir)
                        ? Sort.Direction.DESC
                        : Sort.Direction.ASC;

        // 3) Bestem sorteringsfelt (default = moduleName)
        String sortProperty =
                (sortField == null || sortField.isBlank())
                        ? "moduleName"
                        : sortField;

        Sort sort = Sort.by(direction, sortProperty);

        // 4) Søgning + sortering
        if (search != null && !search.isBlank()) {
            return repository.findByModuleNameContainingIgnoreCase(search, sort);
        }

        // 5) Kun sortering
        return repository.findAll(sort);
    }
}