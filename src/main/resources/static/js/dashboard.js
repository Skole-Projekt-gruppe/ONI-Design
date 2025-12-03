function loadDashboard() {
    fetch('/api/dashboard')
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok: ' + response.status);
            }
            return response.json();
        })
        .then(data => {
            document.getElementById('module-name').textContent = data.moduleName;
            document.getElementById('capacity').textContent = data.nominalCapacityKwh;
            document.getElementById('weight').textContent = data.grossWeightKg;
            document.getElementById('voltage').textContent = data.nominalVoltageV;
            document.getElementById('description').textContent = data.description;
        })
        .catch(error => {
            console.error('Error loading dashboard:', error);
            document.getElementById('module-name').textContent = 'Error loading dashboard';
        });
}

// Kør når HTML er loaded
document.addEventListener('DOMContentLoaded', loadDashboard);
