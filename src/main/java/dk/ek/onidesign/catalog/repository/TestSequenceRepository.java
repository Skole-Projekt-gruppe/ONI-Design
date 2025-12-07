package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.TestSequence;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestSequenceRepository extends JpaRepository<TestSequence, Long> {
}
