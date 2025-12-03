package dk.ek.onidesign.entity;

import jakarta.persistence.*;

@Entity
public class Module {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long moduleId;

    private String moduleName;

    @Column(length = 2000)
    private String description;

    private String overviewImageUrl;

    // getters & setters

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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
}