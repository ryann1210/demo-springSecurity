package com.ryan.base.response;

import com.alibaba.fastjson.JSON;
import lombok.Data;

@Data
public class ResponseJson {

    // 响应业务状态
    private Integer code;
    // 响应消息
    private String message;
    // 响应中的数据
    private Object data;

    public ResponseJson() {
    }
    public ResponseJson(Object data) {
        this.code = 200;
        this.message = "OK";
        this.data = data;
    }
    public ResponseJson(String message, Object data) {
        this.code = 200;
        this.message = message;
        this.data = data;
    }
    public ResponseJson(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static ResponseJson ok() {
        return new ResponseJson(null);
    }
    public static ResponseJson ok(String message) {
        return new ResponseJson(message, null);
    }
    public static ResponseJson ok(Object data) {
        return new ResponseJson(data);
    }
    public static ResponseJson ok(String message, Object data) {
        return new ResponseJson(message, data);
    }

    public static ResponseJson build(Integer code, String message) {
        return new ResponseJson(code, message, null);
    }

    public static ResponseJson build(Integer code, String message, Object data) {
        return new ResponseJson(code, message, data);
    }

    public String toJsonString() {
        return JSON.toJSONString(this);
    }


    /**
     * JSON字符串转成 ResponseJson 对象
     * @param json
     * @return
     */
    public static ResponseJson format(String json) {
        try {
            return JSON.parseObject(json, ResponseJson.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
