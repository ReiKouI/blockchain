"use strict";

$("#signup").on("click", function () {
    var name = $(this).parent().find("#username").val();
    var pwd = $(this).parent().find("#password").val();
    if(name!==""&&pwd!=="") {
        addAgent(name, pwd);
    }
    else {
        alert("用户名或密码不能为空");
    }
});
