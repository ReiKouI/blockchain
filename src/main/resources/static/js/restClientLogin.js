function addAgent(name,port) {
    var formData = new FormData();
    formData.append("name", "A3");
    // formData.append("name", name);
    formData.append("port", "3031");
    // formData.append("port", port);
    sendHttpRequest("POST", "agent", formData, saveUserInfo);

}

function saveUserInfo(jsonUserInfo){
    if (typeof jsonUserInfo === "string") {
        var userInfo;
        try {
            userInfo = JSON.parse(jsonUserInfo);
        } catch (e) {
            document.getElementById("msg").innerHTML = "Invalid response from server " + jsonUserInfo;
            return;
        }
    } else {
        userInfo = jsonUserInfo;
    }
    window.sessionStorage.setItem("username",userInfo.data.name);
    window.sessionStorage.setItem("port",userInfo.data.port);
    alert("注册成功！")
    window.location.href = 'index.html';
}

function getBalance(name){
    var formData = new FormData();
    formData.append("name",name);
    sendHttpRequest("POST","agent/balance",formData, showBalance);
}

function deposit(name){
    var formData = new FormData();
    formData.append("name", name);
    sendHttpRequest("POST", "agent/blank", formData, null);
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
