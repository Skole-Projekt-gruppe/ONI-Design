package dk.ek.onidesign.catalog.model;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "pack_data")
public class PackData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pack_data_id")
    private int packDataId;

    // Owner Module
    @OneToOne
    @JoinColumn(name = "module_id")
    private Module module;

    @Column(name = "cell_quantity")
    private int cellQuantity;

    // Precision er max antal cifre
    // Scale er antal cifre efter kommaet
    // BigDecimal er equal til decimal(precision,scale) i SQL
    @Column(name = "cell_weight_kg", precision = 5, scale = 2)
    private BigDecimal cellWeightKg;

    @Column(name = "gross_weight_kg", precision = 5, scale = 2)
    private BigDecimal grossWeightKg;

    @Column(name = "nominal_capacity_kwh", precision = 5, scale = 2)
    private BigDecimal nominalCapacityKwh;

    @Column(name = "peak_capacity_kwh", precision = 5, scale = 2)
    private BigDecimal peakCapacityKwh;

    @Column(name = "nominal_voltage_v", precision = 5, scale = 2)
    private BigDecimal nominalVoltageV;

    @Column(name = "peak_voltage_v", precision = 5, scale = 2)
    private BigDecimal peakVoltageV;

    @Column(name = "cutoff_voltage_v", precision = 5, scale = 2)
    private BigDecimal cutoffVoltageV;

    @Column(name = "nominal_discharge_a", precision = 6, scale = 1)
    private BigDecimal nominalDischargeA;

    @Column(name = "peak_discharge_a", precision = 6, scale = 1)
    private BigDecimal peakDischargeA;

    @Column(name = "nominal_ac_dc_charge_a", precision = 6, scale = 1)
    private BigDecimal nominalAcDcChargeA;

    @Column(name = "nominal_charge_time_min")
    private int nominalChargeTimeMin;

    @Column(name = "peak_dc_charge_a", precision = 6, scale = 1)
    private BigDecimal peakDcChargeA;

    @Column(name = "peak_charge_time_min")
    private int peakChargeTimeMin;

    public int getPackDataId() {
        return packDataId;
    }

    public void setPackDataId(int packDataId) {
        this.packDataId = packDataId;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public int getCellQuantity() {
        return cellQuantity;
    }

    public void setCellQuantity(int cellQuantity) {
        this.cellQuantity = cellQuantity;
    }

    public BigDecimal getCellWeightKg() {
        return cellWeightKg;
    }

    public void setCellWeightKg(BigDecimal cellWeightKg) {
        this.cellWeightKg = cellWeightKg;
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

    public BigDecimal getPeakVoltageV() {
        return peakVoltageV;
    }

    public void setPeakVoltageV(BigDecimal peakVoltageV) {
        this.peakVoltageV = peakVoltageV;
    }

    public BigDecimal getCutoffVoltageV() {
        return cutoffVoltageV;
    }

    public void setCutoffVoltageV(BigDecimal cutoffVoltageV) {
        this.cutoffVoltageV = cutoffVoltageV;
    }

    public BigDecimal getNominalDischargeA() {
        return nominalDischargeA;
    }

    public void setNominalDischargeA(BigDecimal nominalDischargeA) {
        this.nominalDischargeA = nominalDischargeA;
    }

    public BigDecimal getPeakDischargeA() {
        return peakDischargeA;
    }

    public void setPeakDischargeA(BigDecimal peakDischargeA) {
        this.peakDischargeA = peakDischargeA;
    }

    public BigDecimal getNominalAcDcChargeA() {
        return nominalAcDcChargeA;
    }

    public void setNominalAcDcChargeA(BigDecimal nominalAcDcChargeA) {
        this.nominalAcDcChargeA = nominalAcDcChargeA;
    }

    public int getNominalChargeTimeMin() {
        return nominalChargeTimeMin;
    }

    public void setNominalChargeTimeMin(int nominalChargeTimeMin) {
        this.nominalChargeTimeMin = nominalChargeTimeMin;
    }

    public BigDecimal getPeakDcChargeA() {
        return peakDcChargeA;
    }

    public void setPeakDcChargeA(BigDecimal peakDcChargeA) {
        this.peakDcChargeA = peakDcChargeA;
    }

    public int getPeakChargeTimeMin() {
        return peakChargeTimeMin;
    }

    public void setPeakChargeTimeMin(int peakChargeTimeMin) {
        this.peakChargeTimeMin = peakChargeTimeMin;
    }
}
