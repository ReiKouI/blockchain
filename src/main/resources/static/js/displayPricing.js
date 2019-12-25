"use strict"
// prcing买的界面，展示all agent信息
function displayPrcingBuy(jsonAgent) {
    if (typeof jsonAgent === "string") {
        var agent;
        try {
            agent = JSON.parse(jsonAgent);
        } catch (e) {
            document.getElementById("msg").innerHTML = "Invalid response from server " + jsonBlock;
            return;
        }
    } else {
        agent = jsonAgent;
    }
    console.log(agent)
    if (agent.code == 0 ){
        $("#pricingBuy").empty();
        for(var i= 0;i<agent.data.length;i++){
            var itemTrans = agent.data[i]
            // var isPaid = "                            <td><span class=\"label gradient-4 rounded\">未记账</span>\n"
            // if(!itemTrans.valid){
            //     isPaid = "                            <td><span class=\"label gradient-2 rounded\">已记帐</span>\n"
            // }
            var tr= "<tr>\n" +
                "                                <td>"+itemTrans.name+"</a>\n" +
                "                                </td>\n" +
                "                                <td>"+itemTrans.address+"</td>\n" +
                "                                <td><span class=\"text-muted\">"+itemTrans.port+"</span>\n" +
                "                                </td>\n" +
                "                                <td>"+itemTrans.availableAccount+"</td>\n" +
                // "                                <td><span id='123' class=\"buyByAgent label gradient-2 rounded\" valueName=\'"+itemTrans.name+"\' valueAvailableAccount=\'"+itemTrans.availableAccount+"\' onclick='buyByAgent("+itemTrans.name+","+itemTrans.availableAccount+")'>买入</span>\n" +
                "                                <td><span class=\"label gradient-2 rounded\" onclick='buyByAgent(\""+itemTrans.name+"\",\""+itemTrans.availableAccount+"\")'>买入</span>\n" +
                "                                </td>\n" +
                "                            </tr>"
            $("#pricingBuy").append(tr);
        }

    }
}

function displayPrcingMySell(jsonAgent) {
    if (typeof jsonAgent === "string") {
        var agent;
        try {
            agent = JSON.parse(jsonAgent);
        } catch (e) {
            document.getElementById("msg").innerHTML = "Invalid response from server " + jsonBlock;
            return;
        }
    } else {
        agent = jsonAgent;
    }
    console.log(agent)
    var myTrans = agent.data
    if (agent.code == 0 ){
        $("#pricingSell").empty()
        for(var i= 0;i<myTrans.transactions.length;i++){
            var itemTrans = myTrans.transactions[i]
            console.log(i)
            console.log(itemTrans)
            console.log(itemTrans.senderName)

            // var isPaid = "                            <td><span class=\"label gradient-4 rounded\">未记账</span>\n"
            // if(!itemTrans.valid){
            //     isPaid = "                            <td><span class=\"label gradient-2 rounded\">已记帐</span>\n"
            // }
            var isValildWord = "                                <td><span class=\"label gradient-2 rounded\">"+itemTrans.valid+"</span></td>\n";
            if(!itemTrans.valid){
                isValildWord = "                                <td><span class=\"label gradient-4 rounded\">"+itemTrans.valid+"</span></td>\n"
            }
            var tr= "<tr>\n" +
                "                                <td>"+itemTrans.senderName+"</td>\n" +
                "                                <td>"+itemTrans.receiverName+"</td>\n" +
                "                                <td><span class=\"text-muted\">"+itemTrans.timeStamp+"</span></td>\n" +
                "                                <td>"+itemTrans.value+"</td>\n" +
                isValildWord +
                "                                <td class='signature'>"+itemTrans.signature+"</td>\n" +
                "                            </tr>"
            $("#pricingSell").append(tr);
        }

    }
}

$("#sellButton").live("click",function () {
    getMyAgentInfo()
})

$("#buyButton").on("click", function () {
    var sender = $(this).parent().parent().find("#buySellName").val();
    var num = $(this).parent().parent().find("#BuyNum").val();
    if(sender!==""&&num!=="") {
        if(window.sessionStorage.getItem("username")) {
            trans(sender, window.sessionStorage.getItem("username"), num);
        }else {
            alert("请先登陆")
        }
    }
    else {
        alert("用户名或密码不能为空");
    }
});

$("#sellBitButton").on("click", function () {
    var reciver = $(this).parent().parent().find("#sellReciverName").val();
    var num = $(this).parent().parent().find("#sellNum").val();
    if(reciver!==""&&num!=="") {
        if(window.sessionStorage.getItem("username")){
            trans(window.sessionStorage.getItem("username"),reciver,num);
        }else {
            alert("请先登陆")
        }
    }
    else {
        alert("用户名或密码不能为空");
    }
});

$(".buyByAgent").on("click",function(){
    console.log("buyByagent")
    var name = $(this).attr("valueName")
    var valueAvailableAccount = $(this).attr("valueAvailableAccount")
    console.log(name)
    console.log(valueAvailableAccount)
    if(name!==""&&valueAvailableAccount!=="") {
        if(window.sessionStorage.getItem("username")){
            trans(name,window.sessionStorage.getItem("username"),valueAvailableAccount);
        }else {
            alert("请先登陆")
        }
    }
    else {
        alert("用户名或密码不能为空");
    }
})
$('#123').on("click",function(){
    console.log("123")
})

function buyByAgent(name,valueAvailableAccount) {
    // console.log("funcbuyByagent")
    // var name = $(this).attr("valueName")
    // var valueAvailableAccount = $(this).attr("valueAvailableAccount")
    console.log(name)
    console.log(valueAvailableAccount)
    if(name!==""&&valueAvailableAccount!=="") {
        if(window.sessionStorage.getItem("username")){
            trans(name,window.sessionStorage.getItem("username"),valueAvailableAccount);
        }else {
            alert("请先登陆")
        }
    }
    else {
        alert("用户名或密码不能为空");
    }
}
