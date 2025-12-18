// === KONSTANTER ===
const API_URL      = "/api/modules";
const PACKDATA_API = "/api/packdatas";

const searchInput = document.getElementById("searchInput");
const searchBtn   = document.getElementById("searchBtn");
const resetBtn    = document.getElementById("resetBtn");
const tbody       = document.getElementById("modulesBody");

// === SORTERINGS-STATE (global) ===
let currentSortField = "moduleName"; // default-felt
let currentSortDir   = "asc";        // default-retning

// Hjælp: lav <td> med tekst
function td(text) {
    const cell = document.createElement("td");
    cell.textContent = text ?? "";
    return cell;
}

// Formatter dato lidt pænere (eller bare rå tekst)
function formatDate(isoString) {
    if (!isoString) return "";
    try {
        return new Date(isoString).toLocaleDateString("da-DK");
    } catch {
        return isoString;
    }
}

// === Hent packData for et moduleId og byg indholdet ===
async function loadPackDataIntoBox(module, box) {
    box.innerHTML = "";

    // === TOP-LINJE: overskrift + knapper ===
    const headerRow = document.createElement("div");
    headerRow.classList.add("packdata-header");

    const h = document.createElement("strong");
    h.textContent = "PackData detaljer";

    // Knap til TestSequences
    const goToTsBtn = document.createElement("button");
    goToTsBtn.type = "button";
    goToTsBtn.classList.add("edit-btn", "testsequence-btn");
    goToTsBtn.textContent = "Vis Test Sequences";

    goToTsBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        window.location.href = `/testsequencetabel.html?moduleId=${module.moduleId}`;
    });

    // Delete module-knap
    const deleteModuleBtn = document.createElement("button");
    deleteModuleBtn.type = "button";
    deleteModuleBtn.classList.add("delete-btn");
    deleteModuleBtn.textContent = "Delete module";

    deleteModuleBtn.addEventListener("click", async (e) => {
        e.stopPropagation();

        const ok = confirm(
            `Vil du slette modulet "${module.moduleName ?? module.moduleId}" ` +
            `og alle tilknyttede PackData / TestSequences / TestResults?`
        );
        if (!ok) return;

        try {
            const res = await fetch(`${API_URL}/${module.moduleId}`, {
                method: "DELETE"
            });

            if (!res.ok) {
                throw new Error("API-fejl: " + res.status);
            }

            // Når modulet er slettet, henter vi modul-listen igen
            await loadModules();
        } catch (err) {
            console.error(err);
            alert("Kunne ikke slette modulet.");
        }
    });

    // Pdf button
    const makePDFBtn = document.createElement("button");
    makePDFBtn.type = "button";
    makePDFBtn.classList.add("edit-btn", "pdf-btn");
    makePDFBtn.textContent = "Make PDF";

    makePDFBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        window.open(`/api/report/module/${module.moduleId}`, "_blank");

    });



    // Rækkefølgen i headeren – kan styles med flex i CSS
    headerRow.appendChild(h);
    headerRow.appendChild(goToTsBtn);
    headerRow.appendChild(makePDFBtn);
    headerRow.appendChild(deleteModuleBtn);

    box.appendChild(headerRow);

    // === Loading-tekst som før ===
    const loading = document.createElement("p");
    loading.textContent = "Henter data...";
    box.appendChild(loading);

    try {
        const res = await fetch(`${PACKDATA_API}/${module.moduleId}`);

        if (res.status === 404) {
            loading.textContent = "Ingen PackData tilknyttet.";
            return;
        }
        if (!res.ok) {
            throw new Error("Fejl fra API: " + res.status);
        }

        const pd = await res.json();
        loading.remove();

        // Tabel med: Felt | Værdi
        const table = document.createElement("table");
        table.classList.add("packdata-table");

        const thead = document.createElement("thead");
        thead.innerHTML = `
            <tr>
                <th>Felt</th>
                <th>Værdi</th>
            </tr>
        `;
        table.appendChild(thead);

        const tbodyPd = document.createElement("tbody");

        // Hjælp til at lave en række (KUN label + value)
        function row(label, value) {
            const tr = document.createElement("tr");

            const labelTd = document.createElement("td");
            labelTd.textContent = label;
            tr.appendChild(labelTd);

            const valueTd = document.createElement("td");
            valueTd.textContent = (value ?? value === 0) ? value : "-";
            tr.appendChild(valueTd);

            return tr;
        }

        // === Alle PackData-felter ===
        tbodyPd.appendChild(row("PackData ID", pd.packDataId));

        tbodyPd.appendChild(row("Cell quantity", pd.cellQuantity));
        tbodyPd.appendChild(row("Cell weight (kg)", pd.cellWeightKg));
        tbodyPd.appendChild(row("Gross weight (kg)", pd.grossWeightKg));
        tbodyPd.appendChild(row("Nominal capacity (kWh)", pd.nominalCapacityKwh));
        tbodyPd.appendChild(row("Peak capacity (kWh)", pd.peakCapacityKwh));
        tbodyPd.appendChild(row("Nominal voltage (V)", pd.nominalVoltageV));
        tbodyPd.appendChild(row("Peak voltage (V)", pd.peakVoltageV));
        tbodyPd.appendChild(row("Cutoff voltage (V)", pd.cutoffVoltageV));
        tbodyPd.appendChild(row("Nominal discharge (A)", pd.nominalDischargeA));
        tbodyPd.appendChild(row("Peak discharge (A)", pd.peakDischargeA));
        tbodyPd.appendChild(row("Nominal AC/DC charge (A)", pd.nominalAcDcChargeA));
        tbodyPd.appendChild(row("Nominal charge time (min)", pd.nominalChargeTimeMin));
        tbodyPd.appendChild(row("Peak DC charge (A)", pd.peakDcChargeA));
        tbodyPd.appendChild(row("Peak charge time (min)", pd.peakChargeTimeMin));

        table.appendChild(tbodyPd);
        box.appendChild(table);

    } catch (err) {
        console.error(err);
        loading.textContent = "Fejl ved hentning af PackData.";
    }
}

// Byg én modul-række + én “detalje-række” (dropdown)
function createModuleRows(module) {
    const mainTr = document.createElement("tr");
    mainTr.classList.add("module-row");

    // Kolonne 1: modulnavn
    mainTr.appendChild(td(module.moduleName));

    // Kolonne 2: kort beskrivelse
    mainTr.appendChild(td(module.description));

    // Kolonne 3: actions (kun pil)
    const actionsTd = document.createElement("td");

    const toggleBtn = document.createElement("button");
    toggleBtn.type = "button";
    toggleBtn.classList.add("toggle-btn");
    toggleBtn.textContent = "▶"; // lukket som standard

    actionsTd.appendChild(toggleBtn);
    mainTr.appendChild(actionsTd);

    // DETALJE-rækken (dropdown)
    const detailsTr = document.createElement("tr");
    detailsTr.classList.add("details-row", "hidden");

    const detailsTd = document.createElement("td");
    detailsTd.colSpan = 4;

    const box = document.createElement("div");
    box.classList.add("details-box");

    const h = document.createElement("strong");
    h.textContent = "PackData detaljer";
    box.appendChild(h);

    detailsTd.appendChild(box);
    detailsTr.appendChild(detailsTd);

    let packDataLoaded = false;

    async function toggleDetails() {
        const isHidden = detailsTr.classList.contains("hidden");
        if (isHidden) {
            detailsTr.classList.remove("hidden");
            toggleBtn.textContent = "▼";

            if (!packDataLoaded) {
                packDataLoaded = true;
                await loadPackDataIntoBox(module, box);
            }
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

// Render hele tabellen
function renderTable(modules) {
    if (!modules || modules.length === 0) {
        tbody.innerHTML = '<tr><td colspan="4">Ingen moduler fundet.</td></tr>';
        return;
    }

    tbody.innerHTML = "";

    modules.forEach(m => {
        const { mainTr, detailsTr } = createModuleRows(m);
        tbody.appendChild(mainTr);
        tbody.appendChild(detailsTr);
    });
}

// Hent data fra /api/modules (med søg + sort)
async function loadModules() {
    const search = searchInput.value.trim();

    let url = `${API_URL}?sortField=${currentSortField}&sortDir=${currentSortDir}`;
    if (search) {
        url += "&search=" + encodeURIComponent(search);
    }

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error("API-fejl: " + res.status);

        const data = await res.json();
        renderTable(data);

    } catch (err) {
        console.error(err);
        tbody.innerHTML = '<tr><td colspan="4">Fejl ved hentning af data.</td></tr>';
    }
}

// === EVENTS ===

// Første load
loadModules();

// Søg / reset / Enter
searchBtn.addEventListener("click", loadModules);

resetBtn.addEventListener("click", () => {
    searchInput.value = "";
    loadModules();
});

searchInput.addEventListener("keypress", (e) => {
    if (e.key === "Enter") {
        e.preventDefault();
        loadModules();
    }
});

// Klik på tabel-headere med data-sort for at sortere
document.querySelectorAll("th[data-sort]").forEach(th => {
    th.addEventListener("click", () => {
        const field = th.dataset.sort;

        if (field === currentSortField) {
            currentSortDir = currentSortDir === "asc" ? "desc" : "asc";
        } else {
            currentSortField = field;
            currentSortDir   = "asc";
        }

        loadModules();
    });
});

// Create new Module-knap øverst
function createbtn() {
    const btndiv = document.getElementById("button-div");
    const btn = document.createElement("button");

    btn.type = "button";
    btn.classList.add("edit-btn");
    btn.style.margin = "0.5rem 0 1rem 0";
    btn.textContent = "Create new Module";

    btn.addEventListener("click", () => {
        window.location.href = `/ModuleLogging.html`;
    });

    btndiv.appendChild(btn);
}

document.addEventListener("DOMContentLoaded", () => {
    createbtn();
});
