package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.model.PackData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackDataRepository extends JpaRepository<PackData, Long> {
    PackData findByModule_ModuleId(Long moduleId);
}

