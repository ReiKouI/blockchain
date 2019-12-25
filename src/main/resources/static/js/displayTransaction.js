"use strict";
function displayTrans(jsonTrans) {
    if (typeof jsonTrans === "string") {
        var trans;
        try {
            trans = JSON.parse(jsonTrans);
        } catch (e) {
            document.getElementById("msg").innerHTML = "Invalid response from server " + jsonBlock;
            return;
        }
    } else {
        trans = jsonTrans;
    }
    if (trans.code == 0 ){
        $("#transBody").empty()
        for(var i= 0;i<trans.data.length;i++){
            var itemTrans = trans.data[i]
            var isPaid = "                            <td><span class=\"label gradient-4 rounded\">未记账</span>\n"
            if(!itemTrans.valid){
                isPaid = "                            <td><span class=\"label gradient-2 rounded\">已记帐</span>\n"
            }
            var isMine = "                            <td><span class=\"mine label gradient-1 rounded\" value=\""+itemTrans.signature+"\">mine</span>\n"
            if(!itemTrans.valid){
                isMine = "                            <td>\n"
            }
            var tr= "<tr>\n" +
            "                            <td class='signature'>"+itemTrans.signature+"</td>\n" +
            "                            <td>"+itemTrans.senderName+"</td>\n" +
            "                            <td><span class=\"text-muted\">"+itemTrans.timeStamp+"</span>\n" +
            "                            </td>\n" +
            "                            <td>"+itemTrans.value+"</td>\n" +
                isPaid +
            "                            </td>\n" +
                isMine +
            "                            </td>\n" +
                "                        </tr>"
            $("#transBody").append(tr);
        }

    }

}

$(".mine").live("click",function () {
    console.log("mine");
    console.log($(this).attr("value"));
    // $(this).attr("class","label gradient-2 rounded");
    mine(window.sessionStorage.getItem("username"),$(this).attr("value"))
    $(this).remove();
})

