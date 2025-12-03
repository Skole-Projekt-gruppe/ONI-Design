package dk.ek.onidesign.repository;

import dk.ek.onidesign.entity.Module;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<Module, Long> {
}