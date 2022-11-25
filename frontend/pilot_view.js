function getBackendUrl(){
    return "http://localhost:8080"
}

window.addEventListener('load', () =>{
    getPilotFromURL()
    getGetPilotLicenses()
})

function getPilotFromURL(){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayPilot(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/pilots/' + new URLSearchParams(window.location.search).get("pilotid"), true);
    xhttp.send();

    let license_link = document.getElementById("addLicenseLink")
    license_link.href += "?pilotid=" + new URLSearchParams(window.location.search).get("pilotid") 

}

function displayPilot(response) {
    let information_div = document.getElementsByClassName("pilotInformation")[0]
    information_div.appendChild(document.createTextNode("Informacje o pilocie:"))
    let list = document.createElement('ul')

    let el0 = document.createElement('li')
    el0.innerText = "ID: " + response.id
    list.appendChild(el0)

    let el1 = document.createElement('li')
    el1.innerText = "Imię: " + response.name
    list.appendChild(el1)

    let el2 = document.createElement('li')
    el2.innerText = "Nazwisko: " + response.surname
    list.appendChild(el2)

    let el3 = document.createElement('li')
    el3.innerText = "Data urodzenia: " + response.dateOfBirth
    list.appendChild(el3)

    information_div.appendChild(list)

}

function getGetPilotLicenses(){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
                displayLicenses(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/pilots/' + new URLSearchParams(window.location.search).get("pilotid") +"/licenses", true);
    xhttp.send();
}

function displayLicenses(response){
    let tableBody = document.getElementsByClassName("licenseTable")[0]

    while(tableBody.firstChild){
        tableBody.removeChild(tableBody.firstChild)
    }
    if (response.licenses.length > 0){
        let headerRow = tableBody.insertRow(0)
        let headerCell1 = document.createElement("th")
        headerCell1.innerText = "ID"
        headerRow.appendChild(headerCell1)
        let headerCell2 = document.createElement("th")
        headerCell2.innerText = "POZIOM UPRAWNIEŃ"
        headerRow.appendChild(headerCell2)
        let headerCell3 = document.createElement("th")
        headerCell3.innerText = "DATA UZYSKANIA"
        headerRow.appendChild(headerCell3)
        let headerCell4 = document.createElement("th")
        headerRow.appendChild(headerCell4)
        let headerCell5 = document.createElement("th")
        headerRow.appendChild(headerCell5)
        let headerCell6 = document.createElement("th")
        headerRow.appendChild(headerCell6)

        response.licenses.forEach(license => {
            createLicenseRow(license)
        })
    }
}

function createLicenseRow(license){
    let tableBody = document.getElementsByClassName('licenseTable')[0];

    let row = tableBody.insertRow(-1)
    let cell_id = document.createElement("td")
    cell_id.innerText = license.id
    row.appendChild(cell_id)

    let cell_plevel = document.createElement("td")
    cell_plevel.innerText = license.privilegeLevel
    row.appendChild(cell_plevel)

    let cell_date = document.createElement("td")
    cell_date.innerText = license.issueDate
    row.appendChild(cell_date)

    let link_cell = document.createElement("td")
    let details_link = document.createElement("a")
    details_link.href = "license_view.html?licenseid="+license.id
    details_link.innerText = "Szczegóły"
    link_cell.appendChild(details_link)
    row.appendChild(link_cell)

    let edit_cell = document.createElement("td")
    let edit_link = document.createElement("a")
    edit_link.innerText = "Edytuj"
    edit_link.href = "edit_license.html?pilotid="+new URLSearchParams(window.location.search).get("pilotid") +"&licenseid=" +license.id
    edit_cell.appendChild(edit_link)
    row.appendChild(edit_cell)

    let delete_cell = document.createElement("td")
    let delete_button = document.createElement("button")
    delete_button.innerText = "X"
    delete_button.addEventListener('click',  () => deleteLicense(license))
    delete_cell.appendChild(delete_button)

    row.appendChild(delete_cell)
}

function deleteLicense(license){
    const xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function () {
            if (this.readyState === 4 && this.status === 202) {
                getGetPilotLicenses()
            }
        };
        xhttp.open("DELETE",getBackendUrl() + '/api/pilots/' + new URLSearchParams(window.location.search).get("pilotid") +"/licenses/"+license.id, true);
        xhttp.send();
}