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
            displayUsers(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/pilots', true);
    xhttp.send();

}

function displayUsers(response) {
    let tableBody = document.getElementsByClassName('pilotTable')[0];
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

    let delete_cell = document.createElement("td")
    let deleteButton = document.createElement("button")
    deleteButton.addEventListener('click',  deletePilot(pilot))
    deleteButton.innerText = "X"
    delete_cell.appendChild(deleteButton)
    row.appendChild(delete_cell)
}

function deletePilot(pilot){
    console.log(pilot.name)
}