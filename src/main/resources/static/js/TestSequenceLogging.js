const BASE_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const urlParams = new URLSearchParams(window.location.search);
        const currentModuleId = urlParams.get("moduleId");

        const data = {
            // Test Sequence
            moduleId: currentModuleId,
            testSequenceId: null,
            name: document.getElementById("testSequenceName").value,
            description: document.getElementById("testSequenceDescription").value,
            sequenceOrder: document.getElementById("sequenceOrder").value,

            // Test Result
            startingVoltageV: document.getElementById("startingVoltageV").value,
            peakChargeVoltageV: document.getElementById("peakChargeVoltageV").value,
            dischargeVoltageV: document.getElementById("dischargeVoltageV").value,
            voltageImbalanceMaxV: document.getElementById("voltageImbalanceMaxV").value,
            nominalTempC: document.getElementById("nominalTempC").value,
            maxTempC: document.getElementById("maxTempC").value,
            minTempC: document.getElementById("minTempC").value,
            maxDischargeA: document.getElementById("maxDischargeA").value,
            sustainedMaxDischargeSec: document.getElementById("sustainedMaxDischargeSec").value,
            tempCutoffReached: document.getElementById("tempCutoffReached").checked,
            faultsEncountered: document.getElementById("faultsEncountered").value,
            faultType: document.getElementById("faultType").value
        };
        try {
            const response = await fetch(BASE_URL + "/api/testsequences/testresult", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(data)
            });

            if (!response.ok) {
                throw new Error("HTTP error: " + response.status);
            }

            const result = await response.json();
            console.log(result);
            form.reset();
            window.location.href = "/TestSequenceTabel.html?moduleId=" + currentModuleId;

        } catch (error) {
            console.log(error);
        }
    });
});
