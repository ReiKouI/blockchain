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
                "                                <td><span >买入</span>\n" +
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
        for(var i= 0;i<myTrans.transactions.length;i++){
            var itemTrans = myTrans.transactions[i]
            console.log(i)
            console.log(itemTrans)
            console.log(itemTrans.senderName)

            // var isPaid = "                            <td><span class=\"label gradient-4 rounded\">未记账</span>\n"
            // if(!itemTrans.valid){
            //     isPaid = "                            <td><span class=\"label gradient-2 rounded\">已记帐</span>\n"
            // }
            var tr= "<tr>\n" +
                "                                <td>"+itemTrans.senderName+"</td>\n" +
                "                                <td>"+itemTrans.receiverName+"</td>\n" +
                "                                <td><span class=\"text-muted\">"+itemTrans.timeStamp+"</span></td>\n" +
                "                                <td>"+itemTrans.value+"</td>\n" +
                "                                <td><span class=\"label gradient-1 rounded\">"+itemTrans.valid+"</span></td>\n" +
                "                                <td>"+itemTrans.signature+"</td>\n" +
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
        trans(sender,window.sessionStorage.getItem("username"),num);
    }
    else {
        alert("用户名或密码不能为空");
    }
});

$("#sellBitButton").on("click", function () {
    var reciver = $(this).parent().parent().find("#buySellName").val();
    var num = $(this).parent().parent().find("#BuyNum").val();
    if(sender!==""&&num!=="") {
        trans(window.sessionStorage.getItem("username"),reciver,num);
    }
    else {
        alert("用户名或密码不能为空");
    }
});
