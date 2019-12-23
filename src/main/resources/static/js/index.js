function addAgent() {
    var formData = new FormData();
    formData.append("name", "A3");
    formData.append("port", "3001");
    console.log("addAgent")
    sendHttpRequest("POST", "agent", formData, null);

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
