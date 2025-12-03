package dk.ek.onidesign.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
public class PackData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long packDataId;

    @OneToOne
    @JoinColumn(name = "module_id")
    private Module module;

    private Integer cellQuantity;
    private BigDecimal grossWeightKg;
    private BigDecimal nominalCapacityKwh;
    private BigDecimal peakCapacityKwh;
    private BigDecimal nominalVoltageV;

    // getters and setters

    public Long getPackDataId() {
        return packDataId;
    }

    public void setPackDataId(Long packDataId) {
        this.packDataId = packDataId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Integer getCellQuantity() {
        return cellQuantity;
    }

    public void setCellQuantity(Integer cellQuantity) {
        this.cellQuantity = cellQuantity;
    }

    public BigDecimal getGrossWeightKg() {
        return grossWeightKg;
    }

    public void setGrossWeightKg(BigDecimal grossWeightKg) {
        this.grossWeightKg = grossWeightKg;
    }

    public BigDecimal getNominalCapacityKwh() {
        return nominalCapacityKwh;
    }

    public void setNominalCapacityKwh(BigDecimal nominalCapacityKwh) {
        this.nominalCapacityKwh = nominalCapacityKwh;
    }

    public BigDecimal getPeakCapacityKwh() {
        return peakCapacityKwh;
    }

    public void setPeakCapacityKwh(BigDecimal peakCapacityKwh) {
        this.peakCapacityKwh = peakCapacityKwh;
    }

    public BigDecimal getNominalVoltageV() {
        return nominalVoltageV;
    }

    public void setNominalVoltageV(BigDecimal nominalVoltageV) {
        this.nominalVoltageV = nominalVoltageV;
    }
}
