package com.swagger.doc.admin.dto;

/**
 * Created by IntelliJ IDEA.
 * User: wk
 * Date: 2017-03-21 下午1:08
 */
public class ResponseEntity<T> {
    /**
     *
     */
    private static final long serialVersionUID = -651472627567993426L;

    public static final Integer DEFAULT_SUCCESS_CODE = 200;
    public static final Integer DEFAULT_ERROR_CODE = -100;
    public static final Integer PARAMS_ERROR_CODE = -200;                                                   //参数错误
    public static final Integer DATA_NOT_EXISTS_ERROR_CODE = -300;                                                   //数据不存在，该返回值为了兼容老接口主要是
    /**
     * 数据
     */
    private T data;

    /**
     * 返回的code
     */
    private Integer code = DEFAULT_SUCCESS_CODE;
    /**
     * 具体消息
     */
    private String msg;

    public ResponseEntity() {
    }

    public ResponseEntity(T data, Integer code, String msg) {
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public static <T> ResponseEntity<T> success(Object data) {
        return new ResponseEntity(data, (DEFAULT_SUCCESS_CODE), "");
    }

    public static <T> ResponseEntity<T> error(String msg) {
        return new ResponseEntity(null, DEFAULT_ERROR_CODE, msg);
    }

    public static <T> ResponseEntity<T> error(String msg, Integer code) {
        return new ResponseEntity<T>(null, code, msg);
    }

    public static <T> ResponseEntity<T> error(String msg, Integer code, T obj) {
        return new ResponseEntity<T>(obj, code, msg);
    }

    public static <T> ResponseEntity<T> success() {
        return new ResponseEntity(null, (DEFAULT_SUCCESS_CODE), "");
    }

    public static <T> ResponseEntity<T> success(Integer code, String msg) {
        return new ResponseEntity(null, code, msg);
    }

    public static <T> ResponseEntity<T> success(Integer code, String msg, Object data) {
        return new ResponseEntity(data, code, msg);
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
