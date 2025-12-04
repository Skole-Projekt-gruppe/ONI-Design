package dk.ek.onidesign.catalog.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "module")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // genererer selv ID
    @Column(name = "module_id")
    private int moduleId;

    // One-to-One PackData
    @OneToOne(mappedBy = "module")
    private PackData packData;

    // One-to-Many TestSequences
    @OneToMany(mappedBy = "module")
    private List<TestSequence> testSequences = new ArrayList<>();

    @Column(name = "module_name")
    private String moduleName;

    @Lob // Large Object
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "overview_image_url")
    private String overviewImageUrl;

    // LocalDateTime er equal til datetime i SQL
    @CreationTimestamp // sætter selv dato og tid, når den kreeres
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @UpdateTimestamp // sætter selv dato og tid, når den updates
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

