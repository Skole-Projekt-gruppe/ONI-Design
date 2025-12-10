// === KONSTANTER ===
const TS_API_URL    = "/api/testsequences";
const TR_API_URL    = "/api/testresults/sequence";
const TR_DELETE_API = "/api/testresults";

const tsSearchInput = document.getElementById("tsSearchInput");
const tsSearchBtn   = document.getElementById("tsSearchBtn");
const tsResetBtn    = document.getElementById("tsResetBtn");
const tsTbody       = document.getElementById("testSequencesBody");

let tsSortField = "name";
let tsSortDir   = "asc";

// Hjælp: lav <td> med tekst
function td(text) {
    const cell = document.createElement("td");
    cell.textContent = text ?? "";
    return cell;
}

// Læs moduleId fra querystring
const urlParams = new URLSearchParams(window.location.search);
const currentModuleId = urlParams.get("moduleId");

// === Global "Create new TestResult"-knap under <h1> ===
function initCreateButton() {
    const h1 = document.querySelector("h1");
    if (!h1) return;

    // Lav knap
    const createBtn = document.createElement("button");
    createBtn.type = "button";
    createBtn.classList.add("edit-btn");
    createBtn.style.margin = "0.5rem 0 1rem 0";
    createBtn.textContent = "Create new TestResult";

    createBtn.addEventListener("click", () => {
        const moduleIdForUrl = currentModuleId ?? "";
        window.location.href = `/TestSequenceLogging.html?moduleId=${moduleIdForUrl}`;
    });

    // Indsæt knappen lige under H1
    h1.insertAdjacentElement("afterend", createBtn);
}

// === Hent TestResults for én sequence og put dem i en container ===
async function loadTestResultsForSequence(sequenceId, container) {
    container.innerHTML = "";

    const h = document.createElement("strong");
    h.textContent = "TestResults";
    container.appendChild(h);

    const loading = document.createElement("p");
    loading.textContent = "Henter test results...";
    container.appendChild(loading);

    try {
        const url = `${TR_API_URL}/${sequenceId}?sortField=testResultId&sortDir=asc`;
        const res = await fetch(url);

        if (res.status === 404) {
            loading.textContent = "Ingen test results tilknyttet.";
            return;
        }
        if (!res.ok) throw new Error("API-fejl: " + res.status);

        const results = await res.json();
        loading.remove();

        if (!results.length) {
            container.appendChild(Object.assign(document.createElement("p"), {
                textContent: "Ingen test results tilknyttet."
            }));
            return;
        }

        const table = document.createElement("table");
        table.classList.add("packdata-table");

        table.innerHTML = `
            <thead>
                <tr>
                    <th>Start V</th>
                    <th>Peak charge V</th>
                    <th>Discharge V</th>
                    <th>Max discharge A</th>
                    <th>Max discharge sek</th>
                    <th>Temp cutoff?</th>
                    <th>Faults</th>
                    <th>Fault type</th>
                    <th></th>
                </tr>
            </thead>
        `;

        const tbody = document.createElement("tbody");

        results.forEach(tr => {
            const row = document.createElement("tr");

            row.appendChild(td(tr.startingVoltageV));
            row.appendChild(td(tr.peakChargeVoltageV));
            row.appendChild(td(tr.dischargeVoltageV));
            row.appendChild(td(tr.maxDischargeA));
            row.appendChild(td(tr.sustainedMaxDischargeSec));
            row.appendChild(td(tr.tempCutoffReached ? "Ja" : "Nej"));
            row.appendChild(td(tr.faultsEncountered));
            row.appendChild(td(tr.faultType));

            const actionTd = document.createElement("td");

            // DELETE
            const deleteBtn = document.createElement("button");
            deleteBtn.classList.add("delete-btn");
            deleteBtn.textContent = "Delete";
            deleteBtn.addEventListener("click", async (e) => {
                e.stopPropagation();
                if (!confirm(`Slette TestResult ${tr.testResultId}?`)) return;

                const res = await fetch(`${TR_DELETE_API}/${tr.testResultId}`, {
                    method: "DELETE"
                });

                if (res.ok) row.remove();
                else alert("Kunne ikke slette.");
            });

            actionTd.appendChild(deleteBtn);
            row.appendChild(actionTd);
            tbody.appendChild(row);
        });

        table.appendChild(tbody);
        container.appendChild(table);

    } catch (err) {
        console.error(err);
        loading.textContent = "Fejl ved hentning af test results.";
    }
}

// === Render TestSequences ===
function renderTestSequenceTable(sequences) {
    if (!sequences || !sequences.length) {
        tsTbody.innerHTML = '<tr><td colspan="4">Ingen test sequences fundet.</td></tr>';
        return;
    }

    tsTbody.innerHTML = "";

    sequences.forEach(seq => {
        const tr = document.createElement("tr");
        const tdCell = document.createElement("td");
        tdCell.colSpan = 4;

        const box = document.createElement("div");
        box.classList.add("details-box");

        box.innerHTML = `
            <h3>${seq.name}</h3>
            <p>${seq.description ?? ""}</p>
            <p>Sequence order: ${seq.sequenceOrder}</p>
        `;

        const resultsContainer = document.createElement("div");
        box.appendChild(resultsContainer);

        tdCell.appendChild(box);
        tr.appendChild(tdCell);
        tsTbody.appendChild(tr);

        loadTestResultsForSequence(seq.testSequenceId, resultsContainer);
    });
}

// Hent sequences
async function loadTestSequences() {
    let url = `${TS_API_URL}?sortField=${tsSortField}&sortDir=${tsSortDir}`;

    if (currentModuleId) {
        url += `&moduleId=${currentModuleId}`;
    }

    const res = await fetch(url);
    const data = await res.json();
    renderTestSequenceTable(data);
}

// Init
initCreateButton();
loadTestSequences();
