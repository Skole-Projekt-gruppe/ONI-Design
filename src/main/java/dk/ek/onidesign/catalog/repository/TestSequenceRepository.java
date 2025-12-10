package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.TestSequence;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestSequenceRepository extends JpaRepository<TestSequence, Long> {
    @Query("""
        SELECT ts
        FROM TestSequence ts
        WHERE (:search IS NULL OR :search = '' 
               OR LOWER(ts.name) LIKE LOWER(CONCAT('%', :search, '%')))
        """)
    List<TestSequence> searchByName(@Param("search") String search, Sort sort);
}