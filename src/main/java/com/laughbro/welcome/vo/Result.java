package com.laughbro.welcome.vo;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;
    private String msg;
    private Object data;

    /**
     * 成功的返回
     * @param data 数据，不存在填null
     * @return 包含code=200 和 msg为success 以及data的json
     */
    public static Result success(Object data)
    {
        return new Result( 200, "success", data);
    }

    /**
     * 失败的返回
     * @param code 消息编码
     * @param msg 错误解释
     * @param data 数据，不存在填null
     * @return 包含自定义code和msg以及data的json
     */
    public static Result fail(int code, String msg,Object data){
        return new Result(code, msg, data);
    }
}
