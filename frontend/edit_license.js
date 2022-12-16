function getBackendUrl(){
    return "http://localhost:8080"
}

window.addEventListener('load', () =>{
    let form = document.getElementsByClassName('addForm')[0]
    form.addEventListener('submit', event => editLicense(event))

    getLicense()
    let backLink = document.getElementById("backLink")
    backLink.href = 'pilot_view.html?pilotid='+new URLSearchParams(window.location.search).get("pilotid")
    

})

function getLicense(){
    let license_id = new URLSearchParams(window.location.search).get("licenseid")
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            license = JSON.parse(this.responseText)
            document.getElementById('id_form').value = license.id
            document.getElementById('plevel_form').value = license.privilegeLevel
            document.getElementById('date_form').value = license.issueDate
            document.getElementById('dsc_form').value = license.description
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/licenses/' + license_id, true);
    xhttp.send();
}

function editLicense(event){

    let pilot_id = new URLSearchParams(window.location.search).get("pilotid")
    let license_id = new URLSearchParams(window.location.search).get("licenseid")
    const xhttp = new XMLHttpRequest();
    xhttp.open("PUT", getBackendUrl() + '/api/pilots/' + pilot_id +'/licenses/' + license_id, true);
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 202) {
            alert("LICENSE EDITED")
            window.location.href='pilot_view.html?pilotid='+pilot_id
        }
    };

    event.preventDefault()
    let request_object = {
        'privilegeLevel' : document.getElementById('plevel_form').value,
        'issueDate' : document.getElementById('date_form').value,
        'description' : document.getElementById('dsc_form').value
    }

    xhttp.setRequestHeader('Content-Type', 'application/json');

    xhttp.send(JSON.stringify(request_object));  
      
    document.getElementById('id_form').value = ""
    document.getElementById('plevel_form').value = ""
    document.getElementById('date_form').value = ""
    document.getElementById('dsc_form').value = ""

}