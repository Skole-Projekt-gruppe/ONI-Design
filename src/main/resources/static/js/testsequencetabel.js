// === KONSTANTER ===
const TS_API_URL    = "/api/testsequences";
const TR_API_URL    = "/api/testresults/sequence";
// const TR_DELETE_API = "/api/testresults"; // bruges ikke længere

const tsTbody = document.getElementById("testSequencesBody");

// Læs moduleId fra URL (?moduleId=2)
const urlParams = new URLSearchParams(window.location.search);
const currentModuleId = urlParams.get("moduleId");

// Hjælp: <td>-generator
function td(text) {
    const cell = document.createElement("td");
    cell.textContent = text ?? "";
    return cell;
}

// === Global "Create new TestResult"-knap ===
function initCreateButton() {
    const h1 = document.querySelector("h1");
    if (!h1) return;

    const createBtn = document.createElement("button");
    createBtn.type = "button";
    createBtn.classList.add("edit-btn");
    createBtn.style.margin = "0.5rem 0 1rem 0";
    createBtn.textContent = "Create new TestResult";

    createBtn.addEventListener("click", () => {
        window.location.href = `/TestSequenceLogging.html?moduleId=${currentModuleId}`;
    });

    h1.insertAdjacentElement("afterend", createBtn);
}

// === Hent TestResults for én sequence ===
async function loadTestResultsForSequence(sequenceId, container) {
    container.innerHTML = "";

    const h = document.createElement("strong");
    h.textContent = "TestResults";
    container.appendChild(h);

    const loading = document.createElement("p");
    loading.textContent = "Henter test results...";
    container.appendChild(loading);

    try {
        const res = await fetch(`${TR_API_URL}/${sequenceId}?sortField=testResultId&sortDir=asc`);

        if (res.status === 404) {
            loading.textContent = "Ingen test results tilknyttet.";
            return;
        }

        if (!res.ok) throw new Error("API-fejl: " + res.status);

        const results = await res.json();
        loading.remove();

        if (!results.length) {
            const p = document.createElement("p");
            p.textContent = "Ingen test results tilknyttet.";
            container.appendChild(p);
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

            // Ingen delete-knap pr. række længere

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

        // --- header-linje ---
        const headerLine = document.createElement("div");
        headerLine.style.display = "flex";
        headerLine.style.justifyContent = "space-between";
        headerLine.style.alignItems = "center";

        const title = document.createElement("h3");
        title.textContent = seq.name;

        const deleteSeqBtn = document.createElement("button");
        deleteSeqBtn.classList.add("delete-btn");
        deleteSeqBtn.textContent = "Delete sequence";

        deleteSeqBtn.addEventListener("click", async (e) => {
            e.stopPropagation();

            if (!confirm(`Slette hele TestSequence "${seq.name}"?`)) return;

            const res = await fetch(`${TS_API_URL}/${seq.testSequenceId}`, {
                method: "DELETE"
            });

            if (res.ok) tr.remove();
            else alert("Kunne ikke slette test sequence.");
        });

        headerLine.appendChild(title);
        headerLine.appendChild(deleteSeqBtn);

        box.appendChild(headerLine);

        // --- info ---
        const descP = document.createElement("p");
        descP.textContent = seq.description ?? "";
        box.appendChild(descP);

        const orderP = document.createElement("p");
        orderP.textContent = `Sequence order: ${seq.sequenceOrder}`;
        box.appendChild(orderP);

        // --- test results ---
        const resultsContainer = document.createElement("div");
        box.appendChild(resultsContainer);

        tdCell.appendChild(box);
        tr.appendChild(tdCell);
        tsTbody.appendChild(tr);

        loadTestResultsForSequence(seq.testSequenceId, resultsContainer);
    });
}

// === Hent sequences for modulet (INGEN SEARCH, INGEN SORT) ===
async function loadTestSequences() {
    if (!currentModuleId) {
        tsTbody.innerHTML = '<tr><td colspan="4">Intet moduleId angivet i URL\'en.</td></tr>';
        return;
    }

    try {
        const res = await fetch(`${TS_API_URL}?moduleId=${currentModuleId}`);

        if (!res.ok) {
            throw new Error("API-fejl: " + res.status);
        }

        const data = await res.json();
        renderTestSequenceTable(data);

    } catch (err) {
        console.error(err);
        tsTbody.innerHTML =
            '<tr><td colspan="4">Fejl ved hentning af test sequences.</td></tr>';
    }
}

// Init
initCreateButton();
loadTestSequences();
