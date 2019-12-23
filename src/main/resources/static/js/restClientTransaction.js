"use strict";

var testMode = false;
function getBlockChain() {
    var formData = new FormData();
    formData.append("name", "A3");
    sendHttpRequest("POST", "agent/transactions", formData, null);
    // if (testMode){
    //     displayAllAgents('')
    // }
}



function sendHttpRequest(action, url, data, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            console.log(xmlHttp.responseText)
            // callback(xmlHttp.responseText);
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}
