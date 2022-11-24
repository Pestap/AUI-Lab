function getBackendUrl(){
    return "http://localhost:8080"
}

window.addEventListener('load', () =>{
    getLicenseFromURL()

})

function getLicenseFromURL(){
    const xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState === 4 && this.status === 200) {
            displayLicense(JSON.parse(this.responseText))
        }
    };
    xhttp.open("GET", getBackendUrl() + '/api/licenses/' + new URLSearchParams(window.location.search).get("licenseid"), true);
    xhttp.send();

}

function displayLicense(response) {
    let information_div = document.getElementsByClassName("licenseInformation")[0]
    information_div.appendChild(document.createTextNode("Informacje o licencji:"))
    let list = document.createElement('ul')

    console.log(response.pilot)
    let link = document.getElementById('back')
    link.href = "pilot_view.html?pilotid=" + response.pilotId

    let el0 = document.createElement('li')
    el0.innerText = "ID: " + response.id
    list.appendChild(el0)

    let el1 = document.createElement('li')
    el1.innerText = "Poziom uprawnień: " + response.privilegeLevel
    list.appendChild(el1)

    let el2 = document.createElement('li')
    el2.innerText = "Data wydania: " + response.issueDate
    list.appendChild(el2)

    
    let el3 = document.createElement('li')
    el3.innerText = "ID pilota: " + response.pilotId
    list.appendChild(el3)

    information_div.appendChild(list)

    description = document.createElement('p')
    description.innerText = response.description
    information_div.appendChild(description)
}