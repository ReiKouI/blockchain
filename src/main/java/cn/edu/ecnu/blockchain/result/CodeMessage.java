package cn.edu.ecnu.blockchain.result;

public class CodeMessage {

    public static CodeMessage SUCCESS = new CodeMessage(0, "success");
    public static CodeMessage SERVER_ERROR = new CodeMessage(-1, "服务端异常");
    public static CodeMessage PORT_UESD = new CodeMessage(-2, "该端口已经被使用");
    public static CodeMessage INSUFFICIENT_BALANCE = new CodeMessage(101, "余额不足");

    private int code;
    private String msg;

    CodeMessage(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public CodeMessage fillMessage(Object... args) {
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMessage(code, message);
    }

    @Override
    public String toString() {
        return "{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }

}
