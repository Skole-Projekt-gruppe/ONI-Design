package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {
}