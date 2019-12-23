function addAgent(name,port) {
    var formData = new FormData();
    // formData.append("name", "A10");
    formData.append("name", name);
    // formData.append("port", "3031");
    formData.append("port", port);
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
    alert("注册成功！")
    window.location.href = 'index.html';
}

function sendHttpRequest(action, url, data, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            callback(xmlHttp.responseText);
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}
