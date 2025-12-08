const BASE_URL = "http://localhost:8080"


addEventListener("submit",postModulesPackData)

async function postModulesPackData(ModulePackData) {
    const response = await fetch(BASE_URL + "/api/modulepackdatas", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(ModulePackData),
        }
    );
    console.log("john");
    return await response.json();
}
