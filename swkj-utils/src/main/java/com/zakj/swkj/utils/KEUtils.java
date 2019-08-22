package com.zakj.swkj.utils;

import java.util.HashMap;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/23 0023
 * Time: 0:59
 * Description: KindEditor工具类
 **/
public final class KEUtils {

    /**
     * 失败的响应
     *
     * @param msg 响应信息
     * @return 失败的响应
     */
    public static String error(String msg) {
        /*用于返回kindeditor指定的response格式*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("error", 1);
        map.put("message", msg);
        return JsonUtils.parseObject(map);
    }

    /**
     * 成功的响应
     *
     * @param msg 响应信息
     * @return 成功的响应
     */
    public static String ok(String msg) {
         /*用于返回kindeditor指定的response格式*/
        HashMap<String, Object> map = new HashMap<>();
        map.put("error", 0);
        map.put("url", msg);
        return JsonUtils.parseObject(map);
    }

}
