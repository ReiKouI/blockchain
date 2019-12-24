"use strict";

var testMode = false;
function getAgentInfo() {
    sendHttpRequest("GET", "agent/all", null, displayPrcingBuy);
}



function sendHttpRequest(action, url, data, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (callback&&(xmlHttp.readyState === 4 && xmlHttp.status === 200)) {
            callback(xmlHttp.responseText);
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}

function getMyAgentInfo() {
    var formData = new FormData();
    formData.append("name",window.sessionStorage.getItem("username"))
    sendHttpRequest("GET","agent?name="+window.sessionStorage.getItem("username"),null,displayPrcingMySell)
}

function trans(sender,receiver,value) {
    var formData = new FormData();
    formData.append("sender",sender)
    formData.append("receiver",receiver)
    formData.append("value",value)
    sendHttpRequest("POST", "agent/transaction", formData, null);
    alert(sender+"转账给"+receiver+"交易成功！")
}
