package dk.ek.onidesign.catalog.dto;

import dk.ek.onidesign.catalog.entity.TestResult;

public class TestResultMapper {

    public static TestResultDto toDto(TestResult testResult) {
        return new TestResultDto(
                testResult.getTestResultId(),
                // TestSequenceMapper.toDto(testResult.getTestSequence()), Fjernes for at undgå JSON-cirkler og bliver derfor sat til nu
                null, // For at undgår et uendeligt loop
                testResult.getStartingVoltageV(),
                testResult.getPeakChargeVoltageV(),
                testResult.getDischargeVoltageV(),
                testResult.getVoltageImbalanceMaxV(),
                testResult.getNominalTempC(),
                testResult.getMaxTempC(),
                testResult.getMinTempC(),
                testResult.getMaxDischargeA(),
                testResult.getSustainedMaxDischargeSec(),
                testResult.isTempCutoffReached(),
                testResult.getFaultsEncountered(),
                testResult.getFaultType()
        );
    }

    public static TestResult toEntity(TestResultDto testResultDto) {
        TestResult testResult = new TestResult();

        testResult.setTestResultId(testResultDto.testResultId());
        // testResult.setTestSequence(TestSequenceMapper.toEntity(testResultDto.testSequence())); // Fjernes Fordi den fra TestResultDto for at undgå JSON-cirkler.
        testResult.setStartingVoltageV(testResultDto.startingVoltageV());
        testResult.setPeakChargeVoltageV(testResultDto.peakChargeVoltageV());
        testResult.setDischargeVoltageV(testResultDto.dischargeVoltageV());
        testResult.setVoltageImbalanceMaxV(testResultDto.voltageImbalanceMaxV());
        testResult.setNominalTempC(testResultDto.nominalTempC());
        testResult.setMaxTempC(testResultDto.maxTempC());
        testResult.setMinTempC(testResultDto.minTempC());
        testResult.setMaxDischargeA(testResultDto.maxDischargeA());
        testResult.setSustainedMaxDischargeSec(testResultDto.sustainedMaxDischargeSec());
        testResult.setTempCutoffReached(testResultDto.tempCutoffReached());
        testResult.setFaultsEncountered(testResultDto.faultsEncountered());
        testResult.setFaultType(testResultDto.faultType());

        return testResult;
    }
}

