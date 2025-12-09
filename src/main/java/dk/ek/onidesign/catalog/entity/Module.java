package dk.ek.onidesign.catalog.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // genererer selv ID
    @Column(name = "module_id")
    private Long moduleId;

    @OneToOne(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
    private PackData packData;

    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL, orphanRemoval = true)
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

    public Module() {
    }

    public Module(String moduleName, String description, String overviewImageUrl) {
        this.moduleName = moduleName;
        this.description = description;
        this.overviewImageUrl = overviewImageUrl;
    }

    public void addTestSequence(TestSequence sequence) {
        testSequences.add(sequence);
        if (sequence.getModule() != this) {
            sequence.setModule(this);
        }
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public PackData getPackData() {
        return packData;
    }

    // sikrer bi-direktionalitet.
    // hvis packdata ikke er det her, så sæt den til det her.
    public void setPackData(PackData packData) {
        this.packData = packData;
        if (packData.getModule() != this) {
            packData.setModule(this);
        }
    }

    public List<TestSequence> getTestSequences() {
        return testSequences;
    }

    public void setTestSequences(List<TestSequence> testSequences) {
        this.testSequences = testSequences;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOverviewImageUrl() {
        return overviewImageUrl;
    }

    public void setOverviewImageUrl(String overviewImageUrl) {
        this.overviewImageUrl = overviewImageUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}

