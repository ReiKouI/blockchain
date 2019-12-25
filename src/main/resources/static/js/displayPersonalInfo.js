function initialPersonalInfo(){
    var name = window.sessionStorage.getItem("username");
    var port = window.sessionStorage.getItem("port");
    console.log(name,port);
    document.getElementById("personal-name").innerHTML=name;
    document.getElementById("personal-address").innerHTML="localhost:"+port;
    getBalance(name);
}

function showBalance(returnJson){
    var response;
    if (typeof returnJson === "string") {

        try {
            response = JSON.parse(returnJson);
        } catch (e) {
            //document.getElementById("msg").innerHTML = "Invalid response from server " + jsonUserInfo;
            return;
        }
    } else {
        response = returnJson;
    }
    console.log(response);
    if(response.code===0){
        console.log(response.data);
        document.getElementById("personal-balance").innerHTML="$"+response.data;
    }

}

$("#mine-btn").on("click", function () {
    var name = window.sessionStorage.getItem("username");
    deposit(name);
    //$("#personal-balance").load(location.href + " #presonal-balance")
    window.location.reload();
});
