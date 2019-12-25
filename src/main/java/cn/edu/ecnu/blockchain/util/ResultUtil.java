package cn.edu.ecnu.blockchain.util;

import cn.edu.ecnu.blockchain.result.CodeMessage;
import cn.edu.ecnu.blockchain.result.Result;

public class ResultUtil {

    public static Result success(Object data) {
        return new Result(CodeMessage.SUCCESS, data);
    }

    public static Result error(CodeMessage codeMessage) {
        return new Result(codeMessage, null);
    }

}