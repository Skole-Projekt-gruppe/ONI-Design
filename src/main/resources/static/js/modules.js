// === KONSTANTER ===
const API_URL     = "/api/modules";
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

// Byg én modul-række + én “detalje-række” (dropdown)
function createModuleRows(module) {
    // HOVED-rækken (modul-linjen)
    const mainTr = document.createElement("tr");
    mainTr.classList.add("module-row");

    // Kolonne 1: modulnavn
    mainTr.appendChild(td(module.moduleName));

    // Kolonne 2: kort beskrivelse
    mainTr.appendChild(td(module.description));

    // Kolonne 3: dato
    mainTr.appendChild(td(formatDate(module.createdAt)));

    // Kolonne 4: actions (kun pil)
    const actionsTd = document.createElement("td");

    // Lille “pil” der viser/skjuler detaljer
    const toggleBtn = document.createElement("button");
    toggleBtn.type = "button";
    toggleBtn.classList.add("toggle-btn");
    toggleBtn.textContent = "▶"; // lukket som standard

    actionsTd.appendChild(toggleBtn);
    mainTr.appendChild(actionsTd);

    // DETALJE-rækken (dropdown) – gemt som udgangspunkt
    const detailsTr = document.createElement("tr");
    detailsTr.classList.add("details-row", "hidden");

    const detailsTd = document.createElement("td");
    detailsTd.colSpan = 4; // matcher 4 kolonner i tabellen

    // === Indhold til PackData ===
    const box = document.createElement("div");
    box.classList.add("details-box");

    const h = document.createElement("strong");
    h.textContent = "PackData detaljer";
    box.appendChild(h);

    const pd = module.packData;

    if (pd) {
        // Laver en lille tabel med: Felt | Værdi | Edit-knap
        const table = document.createElement("table");
        table.classList.add("packdata-table");

        const thead = document.createElement("thead");
        thead.innerHTML = `
            <tr>
                <th>Felt</th>
                <th>Værdi</th>
                <th></th>
            </tr>
        `;
        table.appendChild(thead);

        const tbodyPd = document.createElement("tbody");

        // Hjælp til at lave en række
        function row(label, value, canEdit) {
            const tr = document.createElement("tr");

            // Kolonne 1: navn på feltet
            const labelTd = document.createElement("td");
            labelTd.textContent = label;
            tr.appendChild(labelTd);

            // Kolonne 2: selve værdien
            const valueTd = document.createElement("td");
            valueTd.textContent = value ?? "-";
            tr.appendChild(valueTd);

            // Kolonne 3: Edit-knap (kun hvis canEdit = true)
            const actionTd = document.createElement("td");
            if (canEdit) {
                const editBtn = document.createElement("button");
                editBtn.type = "button";
                editBtn.classList.add("edit-btn");
                editBtn.textContent = "Edit";

                editBtn.addEventListener("click", (e) => {
                    e.stopPropagation(); // så vi ikke også åbner/lukker dropdown
                    // TODO: her kan I senere åbne en rigtig form.
                    alert(`Edit '${label}' for module ${module.moduleName ?? module.moduleId}`);
                });

                actionTd.appendChild(editBtn);
            }
            tr.appendChild(actionTd);

            return tr;
        }

        // === Alle PackData-felter ===
        // ID → uden edit-knap
        tbodyPd.appendChild(row("PackData ID", pd.packDataId, false));

        // Resten → med edit-knap
        tbodyPd.appendChild(row("Cell quantity", pd.cellQuantity, true));
        tbodyPd.appendChild(row("Cell weight (kg)", pd.cellWeightKg, true));
        tbodyPd.appendChild(row("Gross weight (kg)", pd.grossWeightKg, true));
        tbodyPd.appendChild(row("Nominal capacity (kWh)", pd.nominalCapacityKwh, true));
        tbodyPd.appendChild(row("Peak capacity (kWh)", pd.peakCapacityKwh, true));
        tbodyPd.appendChild(row("Nominal voltage (V)", pd.nominalVoltageV, true));
        tbodyPd.appendChild(row("Peak voltage (V)", pd.peakVoltageV, true));
        tbodyPd.appendChild(row("Cutoff voltage (V)", pd.cutoffVoltageV, true));
        tbodyPd.appendChild(row("Nominal discharge (A)", pd.nominalDischargeA, true));
        tbodyPd.appendChild(row("Peak discharge (A)", pd.peakDischargeA, true));
        tbodyPd.appendChild(row("Nominal AC/DC charge (A)", pd.nominalAcDcChargeA, true));
        tbodyPd.appendChild(row("Nominal charge time (min)", pd.nominalChargeTimeMin, true));
        tbodyPd.appendChild(row("Peak DC charge (A)", pd.peakDcChargeA, true));
        tbodyPd.appendChild(row("Peak charge time (min)", pd.peakChargeTimeMin, true));

        table.appendChild(tbodyPd);
        box.appendChild(table);
    } else {
        const p = document.createElement("p");
        p.textContent = "Ingen packData tilknyttet.";
        box.appendChild(p);
    }

    detailsTd.appendChild(box);
    detailsTr.appendChild(detailsTd);

    // Samlet toggle-funktion der både viser/skjuler og opdaterer pilen
    function toggleDetails() {
        const isHidden = detailsTr.classList.contains("hidden");
        if (isHidden) {
            detailsTr.classList.remove("hidden");
            toggleBtn.textContent = "▼"; // åben
        } else {
            detailsTr.classList.add("hidden");
            toggleBtn.textContent = "▶"; // lukket
        }
    }

    // Klik på pilen
    toggleBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        toggleDetails();
    });

    // Klik på selve rækken gør det samme
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

    tbody.innerHTML = ""; // ryd

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

        // Hvis man klikker på samme felt → skift retning
        if (field === currentSortField) {
            currentSortDir = currentSortDir === "asc" ? "desc" : "asc";
        } else {
            // Nyt felt → altid start med ASC
            currentSortField = field;
            currentSortDir   = "asc";
        }

        loadModules();
    });
});