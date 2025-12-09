const TS_API_URL    = "/api/testsequences";
const tsSearchInput = document.getElementById("tsSearchInput");
const tsSearchBtn   = document.getElementById("tsSearchBtn");
const tsResetBtn    = document.getElementById("tsResetBtn");
const tsTbody       = document.getElementById("testSequencesBody");

let tsSortField = "name";
let tsSortDir   = "asc";

function td(text) {
    const cell = document.createElement("td");
    cell.textContent = text ?? "";
    return cell;
}

function createTestSequenceRows(seq) {
    const mainTr = document.createElement("tr");
    mainTr.classList.add("module-row");

    // kolonner til TestSequenceDto
    mainTr.appendChild(td(seq.name));
    mainTr.appendChild(td(seq.description));
    mainTr.appendChild(td(seq.sequenceOrder));

    const actionsTd = document.createElement("td");
    const toggleBtn = document.createElement("button");
    toggleBtn.type = "button";
    toggleBtn.classList.add("toggle-btn");
    toggleBtn.textContent = "▶";
    actionsTd.appendChild(toggleBtn);
    mainTr.appendChild(actionsTd);

    // details-row
    const detailsTr = document.createElement("tr");
    detailsTr.classList.add("details-row", "hidden");

    const detailsTd = document.createElement("td");
    detailsTd.colSpan = 4;

    const box = document.createElement("div");
    box.classList.add("details-box");

    const h = document.createElement("strong");
    h.textContent = "TestResults";
    box.appendChild(h);

    const results = seq.testResults;

    if (results && results.length > 0) {
        const table = document.createElement("table");
        table.classList.add("packdata-table");

        const thead = document.createElement("thead");
        thead.innerHTML = `
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
        `;
        table.appendChild(thead);

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

            const editTd = document.createElement("td");
            const editBtn = document.createElement("button");
            editBtn.type = "button";
            editBtn.classList.add("edit-btn");
            editBtn.textContent = "Edit";
            editBtn.addEventListener("click", (e) => {
                e.stopPropagation();
                alert("Edit TestResult " + tr.testResultId + " for sequence " + seq.name);
            });
            editTd.appendChild(editBtn);

            row.appendChild(editTd);
            tbody.appendChild(row);
        });

        table.appendChild(tbody);
        box.appendChild(table);
    } else {
        const p = document.createElement("p");
        p.textContent = "Ingen test results tilknyttet.";
        box.appendChild(p);
    }

    detailsTd.appendChild(box);
    detailsTr.appendChild(detailsTd);

    function toggleDetails() {
        const hidden = detailsTr.classList.contains("hidden");
        if (hidden) {
            detailsTr.classList.remove("hidden");
            toggleBtn.textContent = "▼";
        } else {
            detailsTr.classList.add("hidden");
            toggleBtn.textContent = "▶";
        }
    }

    toggleBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        toggleDetails();
    });

    mainTr.addEventListener("click", () => {
        toggleDetails();
    });

    return { mainTr, detailsTr };
}

function renderTestSequenceTable(sequences) {
    if (!sequences || sequences.length === 0) {
        tsTbody.innerHTML = '<tr><td colspan="4">Ingen test sequences fundet.</td></tr>';
        return;
    }

    tsTbody.innerHTML = "";
    sequences.forEach(seq => {
        const { mainTr, detailsTr } = createTestSequenceRows(seq);
        tsTbody.appendChild(mainTr);
        tsTbody.appendChild(detailsTr);
    });
}

async function loadTestSequences() {
    const search = tsSearchInput ? tsSearchInput.value.trim() : "";

    let url = `${TS_API_URL}?sortField=${tsSortField}&sortDir=${tsSortDir}`;
    if (search) {
        url += "&search=" + encodeURIComponent(search);
    }

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error("API-fejl: " + res.status);
        const data = await res.json();
        renderTestSequenceTable(data);

    } catch (err) {
        console.error(err);
        tsTbody.innerHTML = '<tr><td colspan="4">Fejl ved hentning af data.</td></tr>';
    }
}

// events
if (tsSearchBtn) {
    tsSearchBtn.addEventListener("click", loadTestSequences);
}
if (tsResetBtn) {
    tsResetBtn.addEventListener("click", () => {
        tsSearchInput.value = "";
        loadTestSequences();
    });
}
if (tsSearchInput) {
    tsSearchInput.addEventListener("keypress", (e) => {
        if (e.key === "Enter") {
            e.preventDefault();
            loadTestSequences();
        }
    });
}

// sortering på headers
document.querySelectorAll("th[data-sort]").forEach(th => {
    th.addEventListener("click", () => {
        const field = th.dataset.sort;

        if (field === tsSortField) {
            tsSortDir = tsSortDir === "asc" ? "desc" : "asc";
        } else {
            tsSortField = field;
            tsSortDir   = "asc";
        }

        loadTestSequences();
    });
});

// første load
loadTestSequences();