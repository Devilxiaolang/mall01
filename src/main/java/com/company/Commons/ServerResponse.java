package com.company.Commons;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.Serializable;

/*通过设置JsonSerialize对象
    当回传成功状态码0时,msg和data为null，
    当回传错误信息（status，msg）时,data为null，
    使用如下注解，则jaskson不对值为null的属性进行包含和处理，key和value在返回值中都不存在
 */
@JsonSerialize
public class ServerResponse<T> implements Serializable {
    private int status;
    private String msg;
    private T data;

    public ServerResponse() {
    }

    public ServerResponse(int status) {
        this.status = status;
    }

    public ServerResponse(int status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ServerResponse(int status, T data) {
        this.status = status;
        this.data = data;
    }

    public ServerResponse(int status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }
    /*
    isSuccess方法方法返回boolean值会被序列化后放入json数据中
    通过添加JsonIgnore注解，该属性将不会在json序列化结果中出现
     */
    @JsonIgnore
    public boolean isSuccess(){return this.status==ResponseCode.SUCCESS.getCode();}

    public int getStatus() {
        return status;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public static<T> ServerResponse<T> createSuccessResponse(){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode());
    }

    public static<T> ServerResponse<T> createSuccessResponse(T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),data);
    }

    public static<T> ServerResponse<T> createSuccessResponse(String msg,T data){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg,data);
    }
    public static<T> ServerResponse<T> createSuccessMessageResponse(String msg){
        return new ServerResponse<T>(ResponseCode.SUCCESS.getCode(),msg);
    }

    public static<T> ServerResponse<T> createErrorResponse(){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode());
    }

    public static<T> ServerResponse<T> createErrorResponse(T data){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),data);
    }

    public static<T> ServerResponse<T> createErrorResponse(String msg,T data){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg,data);
    }
    public static<T> ServerResponse<T> createErrorMessageResponse(String msg){
        return new ServerResponse<T>(ResponseCode.ERROR.getCode(),msg);
    }

    public static<T> ServerResponse<T> createErrorCodeMsg(int errCode,String errMsg){
        return new ServerResponse<T>(errCode,errMsg);
    }
}
