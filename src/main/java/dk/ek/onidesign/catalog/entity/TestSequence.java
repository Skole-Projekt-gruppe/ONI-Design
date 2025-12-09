package dk.ek.onidesign.catalog.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_sequence")
public class TestSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_sequence_id")
    private Long testSequenceId;

    // Many-to-One Module
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    // One-to-Many TestResults
    @OneToMany(mappedBy = "testSequence")
    private List<TestResult> testResults = new ArrayList<>();

    @Column(name = "name")
    private String name;

    @Lob // Large Object
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "sequence_order")
    private int sequenceOrder;

    public TestSequence() {
    }

    public TestSequence(Long testSequenceId, Module module, List<TestResult> testResults, String name, String description, int sequenceOrder) {
        this.testSequenceId = testSequenceId;
        this.module = module;
        this.testResults = testResults;
        this.name = name;
        this.description = description;
        this.sequenceOrder = sequenceOrder;
    }

    public Long getTestSequenceId() {
        return testSequenceId;
    }

    public void setTestSequenceId(Long testSequenceId) {
        this.testSequenceId = testSequenceId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<TestResult> getTestResults() {
        return testResults;
    }

    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getSequenceOrder() {
        return sequenceOrder;
    }

    public void setSequenceOrder(int sequenceOrder) {
        this.sequenceOrder = sequenceOrder;
    }
}
