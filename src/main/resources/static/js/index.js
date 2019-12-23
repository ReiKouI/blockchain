function addAgent(name,port) {
    console.log(name,port);
    var formData = new FormData();
    formData.append("name", name);
    formData.append("port", port);
    console.log("addAgent");
    sendHttpRequest("POST", "agent", formData, saveUserInfo);

}
function showBalance(name){
    var formData = new FormData();
    formData.append("name",name);
    sendHttpRequest("POST","agent/balance",formData, null);
}

function saveUserInfo(data){
    console.log("save user info to session.");
    window.sessionStorage.setItem("username",data.name);
    window.sessionStorage.setItem("port",data.port);
}

function sendHttpRequest(action, url, data, callback) {
    console.log(data);
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        console.log(xmlHttp.readyState);
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            console.log(xmlHttp.responseText);
            callback(xmlHttp.responseText);
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}

