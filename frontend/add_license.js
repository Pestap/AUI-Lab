function getBackendUrl(){
    return "http://localhost:8080"
}

window.addEventListener('load', () =>{
    let form = document.getElementsByClassName('addForm')[0]
    form.addEventListener('submit', event => createLicense(event))
    let backLink = document.getElementById("backLink")
    backLink.href = 'pilot_view.html?pilotid='+new URLSearchParams(window.location.search).get("pilotid")
})

function createLicense(event){
    let pilotID = new URLSearchParams(window.location.search).get("pilotid")

    const xhttp = new XMLHttpRequest();
    xhttp.open("PUT", getBackendUrl() + '/api/pilots/' + pilotID + '/licenses', true);
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 201) {
            alert("LICENSE CREATED")
            window.location.href='pilot_view.html?pilotid='+pilotID
        }
    };

    event.preventDefault()
    let request_object = {
        'id' : document.getElementById('id_form').value,
        'privilegeLevel' : document.getElementById('plevel_form').value,
        'issueDate' : document.getElementById('date_form').value,
        'description' : document.getElementById('dsc_form').value,

    }

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request_object));  

    document.getElementById('id_form').value = ""
    document.getElementById('plevel_form').value = ""
    document.getElementById('date_form').value = ""
    document.getElementById('dsc_form').value = ""

}