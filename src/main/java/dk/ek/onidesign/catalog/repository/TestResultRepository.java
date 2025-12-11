package dk.ek.onidesign.catalog.repository;

import dk.ek.onidesign.catalog.entity.TestResult;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestResultRepository extends JpaRepository<TestResult, Long> {
    @Query("""
        SELECT tr
        FROM TestResult tr
        WHERE tr.testSequence.testSequenceId = :sequenceId
        ORDER BY tr.testResultId ASC
        """)
    List<TestResult> findBySequenceId(@Param("sequenceId") Long sequenceId);
}
