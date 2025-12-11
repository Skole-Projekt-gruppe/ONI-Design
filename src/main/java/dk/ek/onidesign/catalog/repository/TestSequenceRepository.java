package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.TestSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSequenceRepository extends JpaRepository<TestSequence, Long> {

    // Bruges af TestSequenceService.getByModuleId(...)
    // Henter alle TestSequences for et module, sorteret på sequenceOrder stigende
    List<TestSequence> findByModule_ModuleIdOrderBySequenceOrderAsc(Long moduleId);
}