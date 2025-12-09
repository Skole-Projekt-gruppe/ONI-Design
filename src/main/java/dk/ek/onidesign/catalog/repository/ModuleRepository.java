package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.Module;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Long> {

}