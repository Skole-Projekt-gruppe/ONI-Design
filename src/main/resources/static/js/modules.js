// === KONSTANTER ===
const API_URL     = "/api/modules";
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

    // === TOP-LINJE: knap + overskrift ===
    const headerRow = document.createElement("div");
    headerRow.classList.add("packdata-header");

    const goToTsBtn = document.createElement("button");
    goToTsBtn.type = "button";
    goToTsBtn.classList.add("edit-btn", "testsequence-btn");
    goToTsBtn.textContent = "Vis TestSequences";

    // Når vi klikker, sender vi brugeren videre til TestSequenceTabel med moduleId i URL'en
    goToTsBtn.addEventListener("click", (e) => {
        e.stopPropagation();
        // Læg mærke til .html her
        window.location.href = `/testsequencetabel.html?moduleId=${module.moduleId}`;
    });


    const h = document.createElement("strong");
    h.textContent = "PackData detaljer";

    headerRow.appendChild(goToTsBtn);
    headerRow.appendChild(h);
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
        // Fjern "henter..."-tekst
        loading.remove();

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
            valueTd.textContent = (value ?? value === 0) ? value : "-";
            tr.appendChild(valueTd);

            // Kolonne 3: Edit + Delete (kun hvis canEdit = true)
            const actionTd = document.createElement("td");
            if (canEdit) {
                // EDIT-knap (som før)
                const editBtn = document.createElement("button");
                editBtn.type = "button";
                editBtn.classList.add("edit-btn");
                editBtn.textContent = "Edit";

                editBtn.addEventListener("click", (e) => {
                    e.stopPropagation();
                    alert(`Edit '${label}' for module ${module.moduleName ?? module.moduleId}`);
                });
                actionTd.appendChild(editBtn);

                // NY: DELETE-knap
                const deleteBtn = document.createElement("button");
                deleteBtn.type = "button";
                deleteBtn.classList.add("delete-btn");
                deleteBtn.textContent = "Delete";

                deleteBtn.addEventListener("click", async (e) => {
                    e.stopPropagation(); // så vi ikke åbner/lukker dropdown

                    const ok = confirm(
                        `Vil du slette PackData (ID: ${pd.packDataId}) for module ${module.moduleName ?? module.moduleId}?`
                    );
                    if (!ok) return;

                    try {
                        const res = await fetch(`${PACKDATA_API}/${pd.packDataId}`, {
                            method: "DELETE"
                        });

                        if (!res.ok) {
                            throw new Error("API-fejl: " + res.status);
                        }

                        // Simpelt feedback – du kan gøre det pænere senere
                        box.innerHTML = "<p>PackData er slettet.</p>";
                    } catch (err) {
                        console.error(err);
                        alert("Kunne ikke slette PackData.");
                    }
                });
                actionTd.appendChild(deleteBtn);
            }

            tr.appendChild(actionTd);
            return tr;
        }


        // === Alle PackData-felter ===
        tbodyPd.appendChild(row("PackData ID", pd.packDataId, false));

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

    } catch (err) {
        console.error(err);
        loading.textContent = "Fejl ved hentning af PackData.";
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
    // mainTr.appendChild(td(formatDate(module.createdAt))); Bliver ikke brugt til noget

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

    const box = document.createElement("div");
    box.classList.add("details-box");

    // bare en tom overskrift – indholdet fyldes først når vi loader PackData
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
            toggleBtn.textContent = "▼"; // åben

            if (!packDataLoaded) {
                packDataLoaded = true;
                await loadPackDataIntoBox(module, box);
            }
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

function createbtn() {
    const btndiv =document.getElementById("button-div");
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
