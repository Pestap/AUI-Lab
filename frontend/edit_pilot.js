function getBackendUrl(){
    return "http://localhost:8080"
}

window.addEventListener('load', () =>{
    let form = document.getElementsByClassName('addForm')[0]
    form.addEventListener('submit', event => editPilot(event))

    getPilot()
    

})

function getPilot(){
    let pilot_id = new URLSearchParams(window.location.search).get("pilotid")
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            pilot = JSON.parse(this.responseText)
            document.getElementById('id_form').value = pilot.id
            document.getElementById('name_form').value = pilot.name
            document.getElementById('surname_form').value = pilot.surname
            document.getElementById('date_form').value = pilot.dateOfBirth
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/pilots/' + pilot_id, true);
    xhttp.send();
}

function editPilot(event){

    let pilot_id = new URLSearchParams(window.location.search).get("pilotid")
    const xhttp = new XMLHttpRequest();
    xhttp.open("PUT", getBackendUrl() + '/api/pilots/' + pilot_id, true);
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            alert("PILOT EDITED")
            window.location.href='pilots.html'
        }
    };

    event.preventDefault()
    let request_object = {
        'name' : document.getElementById('name_form').value,
        'surname' : document.getElementById('surname_form').value,
        'dateOfBirth' : document.getElementById('date_form').value
    }

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request_object));  
      
    document.getElementById('id_form').value = ""
    document.getElementById('name_form').value = ""
    document.getElementById('surname_form').value = ""
    document.getElementById('date_form').value = ""

}