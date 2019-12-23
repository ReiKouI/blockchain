"use strict";
const TRANS_TABLE_NAME = "transactionTable"
function displayTrans(jsonTrans) {
    console.log(jsonTrans)
    if (jsonTrans.code == 0 ){
        for(var i= 0;i<jsonTrans.data.length;i++){
            var itemTrans = jsonTrans.data[i]
            var isPaid = "                            <td><span class=\"label gradient-4 rounded\">未记账</span>\n"
            if(!itemTrans.valid){
                isPaid = "                            <td><span class=\"label gradient-2 rounded\">已记帐</span>\n"
            }
            var tr= "<tr>\n" +
            "                            <td class='signature'>"+itemTrans.signature+"</td>\n" +
            "                            <td>"+itemTrans.senderName+"</td>\n" +
            "                            <td><span class=\"text-muted\">"+itemTrans.timeStamp+"</span>\n" +
            "                            </td>\n" +
            "                            <td>"+itemTrans.value+"</td>\n" +
                isPaid+
            "                            </td>\n" +
            "                            <td></td>\n" +
            "                        </tr>"
            $("#transBody").append(tr);
            console.log(i)
        }

    }

}

