"use strict";

var testMode = false;
function getBlockChain() {
    var formData = new FormData();
    // formData.append("name", "A3");
    console.log(window.sessionStorage.getItem("username"))
    formData.append("name", window.sessionStorage.getItem("username"));
    sendHttpRequest("POST", "agent/transactions", formData, displayTrans);
}



function sendHttpRequest(action, url, data, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            console.log(xmlHttp.responseText)
            callback(xmlHttp.responseText);
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}
function mine(miner,signature) {
    var formData = new FormData();
    formData.append("miner", miner);
    formData.append("signature", signature);
    sendHttpRequestMine("POST", "agent/mine", formData, getBlockChain);
}

function sendHttpRequestMine(action, url, data, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            console.log(xmlHttp.responseText)
            callback();
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}
