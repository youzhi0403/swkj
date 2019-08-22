package com.zakj.swkj.utils;

import net.sf.json.JSON;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 15:02
 * Description:  统一返回的json响应格式的工具类
 **/
public class ResultUtils {
    /**
     * {
     *     "status":200
     *     "msg":
     *     "data":
     * }
     */
    //响应状态码
    private Integer status;

    //响应状态信息
    private String msg;

    //响应的数据
    private Object data;

    private ResultUtils() {
    }

    public ResultUtils(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ResultUtils(Object data) {
        this.status = 200;
        this.msg = "ok";
        this.data = data;
    }

    /**
     * 产生一个符合响应格式的json串
     *
     * @param status 响应状态码
     * @param msg    响应的状态信息
     * @param data   响应的数据
     * @return 一个符合响应格式的json串
     */
    public static String build(Integer status, String msg, Object data) {
        return JsonUtils.parseObject(new ResultUtils(status, msg, data));
    }

    /**
     * 产生一个成功的响应的json串。
     *
     * @param data 响应的数据
     * @return 一个成功的响应的json
     */
    public static String ok(Object data) {
        return JsonUtils.parseObject(new ResultUtils(data));
    }

    /**
     * 产生一个默认错误码的响应json串
     *
     * @param msg
     * @return
     */
    public static String failure(String msg){
        return JsonUtils.parseObject(new ResultUtils(-1,msg,null));
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
