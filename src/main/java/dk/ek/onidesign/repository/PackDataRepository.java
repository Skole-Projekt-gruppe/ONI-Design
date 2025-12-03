package dk.ek.onidesign.repository;

import dk.ek.onidesign.entity.PackData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PackDataRepository extends JpaRepository<PackData, Long> {
    PackData findByModule_ModuleId(Long moduleId);
}

