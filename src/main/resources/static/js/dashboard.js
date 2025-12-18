function formatNumber(value, decimals = 1) {
    if (value === null || value === undefined) return "–";
    const num = Number(value);
    if (Number.isNaN(num)) return String(value);
    return num.toFixed(decimals);
}

function getModuleIdFromUrl() {
    const params = new URLSearchParams(window.location.search);
    const raw = params.get("moduleId");
    const id = raw ? Number(raw) : 1;
    return Number.isFinite(id) && id > 0 ? id : 1;
}

async function loadModuleOptions(selectedId) {
    const select = document.getElementById("moduleSelect");
    if (!select) return;

    try {
        const res = await fetch("/api/modules/options");
        if (!res.ok) throw new Error("Could not load module options: " + res.status);

        const options = await res.json();

        select.innerHTML = "";
        options.forEach(o => {
            const opt = document.createElement("option");
            opt.value = o.moduleId;
            opt.textContent = o.moduleName;
            select.appendChild(opt);
        });

        select.value = String(selectedId);

    } catch (e) {
        console.error(e);
        select.innerHTML = `<option value="${selectedId}">Module ${selectedId}</option>`;
        select.value = String(selectedId);
    }

    select.addEventListener("change", () => {
        const newId = select.value;
        window.location.href = `/index.html?moduleId=${newId}`;
    });
}

async function loadDashboardData(moduleId) {
    const res = await fetch(`/api/dashboard?moduleId=${moduleId}`);
    if (!res.ok) throw new Error("Dashboard fetch failed: " + res.status);

    const data = await res.json();

    document.getElementById('module-name').textContent = (data.moduleName || "MODULE").toUpperCase();

    document.getElementById('capacity').textContent = formatNumber(data.nominalCapacityKwh, 1);
    document.getElementById('weight').textContent   = formatNumber(data.grossWeightKg, 1);
    document.getElementById('voltage').textContent  = formatNumber(data.nominalVoltageV, 0);

    document.getElementById('description').textContent = data.description || "";
}

document.addEventListener("DOMContentLoaded", async () => {
    const moduleId = getModuleIdFromUrl();

    await loadModuleOptions(moduleId);

    try {
        await loadDashboardData(moduleId);
    } catch (e) {
        console.error(e);
        document.getElementById('module-name').textContent = 'ERROR LOADING DASHBOARD';
    }
});
