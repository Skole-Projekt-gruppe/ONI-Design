package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.TestResult;
import dk.ek.onidesign.catalog.entity.TestSequence;

public class TestResultMapper {

    public static TestResultDto toDto(TestResult tr) {
        return new TestResultDto(
                tr.getTestResultId(),
                tr.getTestSequence().getTestSequenceId(),
                tr.getStartingVoltageV(),
                tr.getPeakChargeVoltageV(),
                tr.getDischargeVoltageV(),
                tr.getVoltageImbalanceMaxV(),
                tr.getNominalTempC(),
                tr.getMaxTempC(),
                tr.getMinTempC(),
                tr.getMaxDischargeA(),
                tr.getSustainedMaxDischargeSec(),
                tr.isTempCutoffReached(),
                tr.getFaultsEncountered(),
                tr.getFaultType()
        );
    }

    public static TestResult toEntity(TestResultDto dto, TestSequence seq) {
        TestResult tr = new TestResult();
        tr.setTestResultId(dto.testResultId());
        tr.setTestSequence(seq);
        tr.setStartingVoltageV(dto.startingVoltageV());
        tr.setPeakChargeVoltageV(dto.peakChargeVoltageV());
        tr.setDischargeVoltageV(dto.dischargeVoltageV());
        tr.setVoltageImbalanceMaxV(dto.voltageImbalanceMaxV());
        tr.setNominalTempC(dto.nominalTempC());
        tr.setMaxTempC(dto.maxTempC());
        tr.setMinTempC(dto.minTempC());
        tr.setMaxDischargeA(dto.maxDischargeA());
        tr.setSustainedMaxDischargeSec(dto.sustainedMaxDischargeSec());
        tr.setTempCutoffReached(dto.tempCutoffReached());
        tr.setFaultsEncountered(dto.faultsEncountered());
        tr.setFaultType(dto.faultType());
        return tr;
    }

}
