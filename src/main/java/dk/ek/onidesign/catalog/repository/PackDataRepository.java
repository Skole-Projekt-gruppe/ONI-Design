package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.PackData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PackDataRepository extends JpaRepository<PackData, Long> {
    PackData findByModule_ModuleId(Long moduleId);
}

