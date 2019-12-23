function initialPersonalInfo(){
    var name = window.sessionStorage.getItem("username");
    var port = window.sessionStorage.getItem("port");
    console.log(name,port);
    document.getElementById("personal-name").value=name;
    document.getElementById("personal-port").value=port;
    showBalance(name);
}