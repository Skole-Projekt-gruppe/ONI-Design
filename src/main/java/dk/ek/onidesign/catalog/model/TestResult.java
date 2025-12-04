package dk.ek.onidesign.catalog.model;

import jakarta.persistence.*;

import javax.naming.Name;
import java.math.BigDecimal;

@Entity
@Table(name = "test_result")
public class TestResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "test_result_id")
    private int testResultId;

    // Many-to-One TestSequence
    @ManyToOne
    @JoinColumn(name = "test_sequence_id")
    private TestSequence testSequence;

    @Column(name = "starting_voltage_v", precision = 5, scale = 2)
    private BigDecimal StartingVoltageV;

    @Column(name = "peak_charge_voltage_v", precision = 5, scale = 2)
    private BigDecimal peakChargeVoltageV;

    @Column(name = "discharge_voltage_v", precision = 5, scale = 2)
    private BigDecimal dischargeVoltageV;

    @Column(name = "voltage_imbalance_max_v", precision = 5, scale = 2)
    private BigDecimal voltageImbalanceMaxV;

    @Column(name = "nominal_temp_c", precision = 4, scale = 1)
    private BigDecimal nominalTempC;

    @Column(name = "max_temp_c", precision = 4, scale = 1)
    private BigDecimal maxTempC;

    @Column(name = "min_temp_c", precision = 4, scale = 1)
    private BigDecimal minTempC;

    @Column(name = "max_discharge_a", precision = 6, scale = 1)
    private BigDecimal maxDischargeA;

    @Column(name = "sustained_max_discharge_sec")
    private int sustainedMaxDischargeSec;

    @Column(name = "temp_cutoff_reached")
    private boolean tempCutoffReached;

    @Column(name = "faults_encountered")
    private int faultsEncountered;

    @Column(name = "fault_type")
    private String faultType;

    public int getTestResultId() {
        return testResultId;
    }

    public void setTestResultId(int testResultId) {
        this.testResultId = testResultId;
    }

    public TestSequence getTestSequence() {
        return testSequence;
    }

    public void setTestSequence(TestSequence testSequence) {
        this.testSequence = testSequence;
    }

    public BigDecimal getStartingVoltageV() {
        return StartingVoltageV;
    }

    public void setStartingVoltageV(BigDecimal startingVoltageV) {
        StartingVoltageV = startingVoltageV;
    }

    public BigDecimal getPeakChargeVoltageV() {
        return peakChargeVoltageV;
    }

    public void setPeakChargeVoltageV(BigDecimal peakChargeVoltageV) {
        this.peakChargeVoltageV = peakChargeVoltageV;
    }

    public BigDecimal getDischargeVoltageV() {
        return dischargeVoltageV;
    }

    public void setDischargeVoltageV(BigDecimal dischargeVoltageV) {
        this.dischargeVoltageV = dischargeVoltageV;
    }

    public BigDecimal getVoltageImbalanceMaxV() {
        return voltageImbalanceMaxV;
    }

    public void setVoltageImbalanceMaxV(BigDecimal voltageImbalanceMaxV) {
        this.voltageImbalanceMaxV = voltageImbalanceMaxV;
    }

    public BigDecimal getNominalTempC() {
        return nominalTempC;
    }

    public void setNominalTempC(BigDecimal nominalTempC) {
        this.nominalTempC = nominalTempC;
    }

    public BigDecimal getMaxTempC() {
        return maxTempC;
    }

    public void setMaxTempC(BigDecimal maxTempC) {
        this.maxTempC = maxTempC;
    }

    public BigDecimal getMinTempC() {
        return minTempC;
    }

    public void setMinTempC(BigDecimal minTempC) {
        this.minTempC = minTempC;
    }

    public BigDecimal getMaxDischargeA() {
        return maxDischargeA;
    }

    public void setMaxDischargeA(BigDecimal maxDischargeA) {
        this.maxDischargeA = maxDischargeA;
    }

    public int getSustainedMaxDischargeSec() {
        return sustainedMaxDischargeSec;
    }

    public void setSustainedMaxDischargeSec(int sustainedMaxDischargeSec) {
        this.sustainedMaxDischargeSec = sustainedMaxDischargeSec;
    }

    public boolean isTempCutoffReached() {
        return tempCutoffReached;
    }

    public void setTempCutoffReached(boolean tempCutoffReached) {
        this.tempCutoffReached = tempCutoffReached;
    }

    public int getFaultsEncountered() {
        return faultsEncountered;
    }

    public void setFaultsEncountered(int faultsEncountered) {
        this.faultsEncountered = faultsEncountered;
    }

    public String getFaultType() {
        return faultType;
    }

    public void setFaultType(String faultType) {
        this.faultType = faultType;
    }
}
