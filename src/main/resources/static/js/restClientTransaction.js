"use strict";

var testMode = false;
function getBlockChain() {
    var formData = new FormData();
    // formData.append("name", "A3");
    formData.append("name", window.sessionStorage.getItem("username"));
    sendHttpRequest("POST", "agent/transactions", formData, displayTrans);
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
