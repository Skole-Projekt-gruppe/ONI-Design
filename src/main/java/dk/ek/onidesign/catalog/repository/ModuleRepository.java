package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.model.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}