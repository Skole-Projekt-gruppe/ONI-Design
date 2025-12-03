package dk.ek.onidesign.web;

import java.math.BigDecimal;

public class DashboardDto {

    private String moduleName;
    private BigDecimal nominalCapacityKwh;
    private BigDecimal grossWeightKg;
    private BigDecimal nominalVoltageV;
    private String description;

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public BigDecimal getNominalCapacityKwh() {
        return nominalCapacityKwh;
    }

    public void setNominalCapacityKwh(BigDecimal nominalCapacityKwh) {
        this.nominalCapacityKwh = nominalCapacityKwh;
    }

    public BigDecimal getGrossWeightKg() {
        return grossWeightKg;
    }

    public void setGrossWeightKg(BigDecimal grossWeightKg) {
        this.grossWeightKg = grossWeightKg;
    }

    public BigDecimal getNominalVoltageV() {
        return nominalVoltageV;
    }

    public void setNominalVoltageV(BigDecimal nominalVoltageV) {
        this.nominalVoltageV = nominalVoltageV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}