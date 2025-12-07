package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.model.Module;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long> {

    // Søge i modulnavn
    List<Module> findByModuleNameContainingIgnoreCase(String moduleName);

    // Søg + sort på modulnavn
    List<Module> findByModuleNameContainingIgnoreCase(String moduleName, Sort sort);
}