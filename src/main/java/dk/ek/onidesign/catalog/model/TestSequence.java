package dk.ek.onidesign.catalog.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "test_sequence")
public class TestSequence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_sequence_id")
    private int testSequenceId;

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
}
