"use strict";

var testMode = false;
function getBlockChain() {
    var formData = new FormData();
    formData.append("name", "A3");
    sendHttpRequest("POST", "agent/transactions", formData, displayTrans);
}



function sendHttpRequest(action, url, data, callback) {
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status === 200) {
            console.log(xmlHttp.responseText)
            var aa = {
                "code": 0,
                "msg": "success",
                "data": [
                    {
                        "timeStamp": 1577099300363,
                        "sender": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAj7HNwzNME3PWOB2ecufxUr/rx6IB3/iJCachkDoZgtgfXhSiWVZO4se3KQ9R4nscUzbTTk37SlcbvanfhXFKsSlAgj6VKl3NEhc8VqPLmZxB/GiiNHFRDzOJmJIrKw79gjIhstMYlTxoSC3wTRT85PzwmpBUEKIx/vpBprt87JBEKSaWZgw/u1mLvcEm8xJBI7Rid3/j4ZrQnTC0LY8CofhnLG6bCEMqKySZCWgHUgod7De517aeIqz5bpOxWNUF1QIo02JDALU+Ki5mHbmtqLBFvPWpL0fQL/IWtE4Ig5IGz6BiQMBiSYgEKY9s4XpVcEJxa83/VZnTos8QcCnckwIDAQAB",
                        "senderName": "A1",
                        "receiver": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8CGzTlqcLVZNEsy7NZvhTgabADoqw3hZQcyMLc/J/BSLs5peHAPg0Bgqd42ltTPJHJFiEOn97PTFgeCSzHVy7AzJTWq6FTPKLzjEHTASQKsYDvJDd4Gi6UjQ5J4+zaG77H7ecR7CWduQWFi9qFyc2LXLtmX0/zEtxtRWkTsVNAqVmjIbl+oM58fhI69L9Z/b4MGbYsbBNgpj4XhJPJMZwF6QYfhpHdu3MJmysSBrfSrwYKARD8SWZ42n/Rt5k6lcbUV+JsZVDZPfx7rbIVJOOEerryu+VJ5LQUiLafgGtnQ1CN9o1t8IeaeWvptnXB0gBSiHcdRMGhJMWK6fmZYYbwIDAQAB",
                        "receiverName": "A2",
                        "value": 9,
                        "signature": "MuPKFrIMblckgRvQaqhdcRThgZzwcBobxJyuiLdS4+vAOr9K0O8ynxzl8stJXKIGUP9bWPeo14WxztwX9wOMGaG+FAwxS5nTQFVdNy66TK7yTOkOYYjNc3OR8O6Bm+4GQXJGuun5GgEAAU8appJAsnIXWoQyGrgXKbbIJY+9ZI8Cie6h+NqJWqiJNcUeMJDe6A+mr70ywHiDfYk0Z2uTWIHQf8C3dAkcdlBTrfOAbO/leR27MMYZQ0sBOC35un6GJvww4XIyVMcFjYEULT4mqrKQ4EuBGE681tFsEc9bY8Z238cEthweY1Gw7nbSv8RzUMWonXt/6F6pgn5/PwHKbQ==",
                        "valid": false,
                        "signVerified": true
                    },
                    {
                        "timeStamp": 1577099314909,
                        "sender": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2LH4fiXZIgJmmRS1h8uJ0J01PiTY5s7ZtPhQ/GLL9+NiflyGVVBUg34mzubCx1HGbJkxYDEawJAcw5BFud3lSmCq/7h+xCRn817f+Oter89L7m40y2y/EfSu01WFqvj2z4XuTj79EjfoQJ+DJxRE1FFpttz1DV4hYpqj3UgOtjRCw36GfEr5aONqmSvr28WiAM1s++iHZcb8bHnVLIMqSVx/2o9Z8Cg+CpcrzDIF7JknNi+1v8VVZlY20VX3zJHX8nQKctzs+9bKKfxSYiBy54rdCRYudITgmc3hDpsr+Ytq5z5aEzUMyrAg1NdqHDolQjPpOUImLtTpT/8chd4kQwIDAQAB",
                        "senderName": "A3",
                        "receiver": "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA8CGzTlqcLVZNEsy7NZvhTgabADoqw3hZQcyMLc/J/BSLs5peHAPg0Bgqd42ltTPJHJFiEOn97PTFgeCSzHVy7AzJTWq6FTPKLzjEHTASQKsYDvJDd4Gi6UjQ5J4+zaG77H7ecR7CWduQWFi9qFyc2LXLtmX0/zEtxtRWkTsVNAqVmjIbl+oM58fhI69L9Z/b4MGbYsbBNgpj4XhJPJMZwF6QYfhpHdu3MJmysSBrfSrwYKARD8SWZ42n/Rt5k6lcbUV+JsZVDZPfx7rbIVJOOEerryu+VJ5LQUiLafgGtnQ1CN9o1t8IeaeWvptnXB0gBSiHcdRMGhJMWK6fmZYYbwIDAQAB",
                        "receiverName": "A2",
                        "value": 8,
                        "signature": "b32E8C4qGduBboLNhOygCUJ2yc9wEnf/oqOkG8zcEyhf7rDTw520XjrOL30RK+1PaJZUEXD2qReGCFb5xc1kfBRpjmhKmiAlDpA5xlElSkSbcC3yNZGVXUSxkcVPApQfIlRC0W8JRO34gFt3PCivvbzSF6kmP6lT8FCXdVQJ9hLqeGwBmBJmDvzC0LklsAgaQDpwZ0EQ1tPvfRlqamF98l/PxpXjFJEMRrJ7tQgtm1eV06HZUc3GPzQs2lgUONy7igpilu0VMmmXeilQcWPCx33Zw7pts/V2YWFrUec0UdcMSdsj8pXaQkpSmfNz+U85LyKuc82q9ijUiHThWuoedg==",
                        "valid": true,
                        "signVerified": true
                    }
                ]
            }
            callback(aa);
        }
    };
    xmlHttp.open(action, url, true);
    xmlHttp.send(data);
}
