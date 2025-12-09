const BASE_URL = "http://localhost:8080";

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector("form");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const data = {
            // Module
            moduleId: null,
            moduleName: document.getElementById("moduleName").value,
            description: document.getElementById("description").value,
            overviewImageUrl: document.getElementById("overviewImageUrl").value,

            // PackData
            packDataId: null,
            cellQuantity: document.getElementById("cellQuantity").value,
            cellWeightKg: document.getElementById("cellWeightKg").value,
            grossWeightKg: document.getElementById("grossWeightKg").value,
            nominalCapacityKwh: document.getElementById("nominalCapacityKwh").value,
            peakCapacityKwh: document.getElementById("peakCapacityKwh").value,
            nominalVoltageV: document.getElementById("nominalVoltageV").value,
            peakVoltageV: document.getElementById("peakVoltageV").value,
            cutoffVoltageV: document.getElementById("cutoffVoltageV").value,
            nominalDischargeA: document.getElementById("nominalDischargeA").value,
            peakDischargeA: document.getElementById("peakDischargeA").value,
            nominalAcDcChargeA: document.getElementById("nominalAcDcChargeA").value,
            nominalChargeTimeMin: document.getElementById("nominalChargeTimeMin").value,
            peakDcChargeA: document.getElementById("peakDcChargeA").value,
            peakChargeTimeMin: document.getElementById("peakChargeTimeMin").value
        };

        try {
            const response = await fetch(BASE_URL + "/api/modules/packdata", {
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

        } catch (error) {
            console.log(error);
        }
    });
});
