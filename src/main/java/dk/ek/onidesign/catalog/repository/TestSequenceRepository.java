package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.TestSequence;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSequenceRepository extends JpaRepository<TestSequence, Long> {
    List<TestSequence> findByNameContainingIgnoreCase(String name, Sort sort);
}
