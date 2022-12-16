function getBackendUrl(){
    return "http://localhost:8080"
}


window.addEventListener('load', () =>{
    let table = document.getElementsByClassName("pilotTable")[0]
    // clear table 
    while(table.firstChild){
        table.removeChild(table.firstChild)
    }

    //get and display pilots
    getAllPilots()
})


function getAllPilots(){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayPilots(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/pilots', true);
    xhttp.send();

}

function displayPilots(response) {
    let tableBody = document.getElementsByClassName('pilotTable')[0];
    while(tableBody.firstChild){
        tableBody.removeChild(tableBody.firstChild)
    }
    let headerRow = tableBody.insertRow(0)
    let headerCell1 = document.createElement("th")
    headerCell1.innerText = "ID"
    headerRow.appendChild(headerCell1)
    let headerCell2 = document.createElement("th")
    headerCell2.innerText = "IMIĘ"
    headerRow.appendChild(headerCell2)
    let headerCell3 = document.createElement("th")
    headerCell3.innerText = "NAZWISKO"
    headerRow.appendChild(headerCell3)
    let headerCell4 = document.createElement("th")
    headerCell4.innerText = "DATA URODZENIA"
    headerRow.appendChild(headerCell4)
    let headerCell5 = document.createElement("th")
    headerRow.appendChild(headerCell5)
    let headerCell6 = document.createElement("th")
    headerRow.appendChild(headerCell6)
    let headerCell7 = document.createElement("th")
    headerRow.appendChild(headerCell7)

    response.pilots.forEach(pilot => {
        createPilotRow(pilot)
    })
}

function createPilotRow(pilot){
    let tableBody = document.getElementsByClassName('pilotTable')[0];

    let row = tableBody.insertRow(-1)
    let cell_id = document.createElement("td")
    cell_id.innerText = pilot.id
    row.appendChild(cell_id)

    let cell_name = document.createElement("td")
    cell_name.innerText = pilot.name
    row.appendChild(cell_name)

    let cell_surname = document.createElement("td")
    cell_surname.innerText = pilot.surname
    row.appendChild(cell_surname)

    let cell_date = document.createElement("td")
    cell_date.innerText = pilot.dateOfBirth
    row.appendChild(cell_date)

    let cell_link = document.createElement('td')
    let details_link = document.createElement("a")
    details_link.href = "pilot_view.html?pilotid="+pilot.id
    details_link.innerText = "Szczegóły"
    cell_link.appendChild(details_link)
    row.appendChild(cell_link)

    let edit_cell = document.createElement("td")
    let edit_link = document.createElement("a")
    edit_link.innerText = "Edytuj"
    edit_link.href = "edit_pilot.html?pilotid=" +pilot.id
    edit_cell.appendChild(edit_link)
    row.appendChild(edit_cell)

    let delete_cell = document.createElement("td")
    let delete_button = document.createElement("button")
    delete_button.innerText = "X"
    delete_button.addEventListener('click',  () => deletePilot(pilot))
    delete_cell.appendChild(delete_button)

    row.appendChild(delete_cell)
}

function deletePilot(pilot){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            getAllPilots()
        }
    };
    xhttp.open("DELETE", getBackendUrl() + '/api/pilots/' + pilot.id, true);
    xhttp.send();
}

