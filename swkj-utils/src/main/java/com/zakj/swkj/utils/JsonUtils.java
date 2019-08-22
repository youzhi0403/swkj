package com.zakj.swkj.utils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 11:32
 * Description: json解析工具类（json-lib），负责将json串转换成对象和将对象转换成json串
 **/
public final class JsonUtils {

    /**
     * 将一个bean对象或者map对象，转换成json对象字符串
     *
     * @param obj 任意普通java对象或者map对象
     * @return 返回一个json对象字符串
     */
    public static <T> String parseObject(T obj) {
        //反正出现死循环
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return JSONObject.fromObject(obj,jsonConfig).toString();
    }

    /**
     * 将一个bean对象，转换成json对象字符串，并指定转换时不包含哪些属性
     *
     * @param obj      任意普通java对象或者map对象
     * @param excludes 不需要转换成json的属性的数组
     * @return 返回一个json对象字符串
     */
    public static <T> String parseObject(T obj, String... excludes) {
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes); //设置不包含json转换时哪些属性
        //阻止出现死循环
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return JSONObject.fromObject(obj, config).toString();
    }

    /**
     * 将数组（Set,Collection,Array）转换成一个json数组字符串
     *
     * @param obj      任意Set,Collection对象，或者Array数组（如String[]）
     * @param excludes 不需要转换成json的属性的数组
     * @return 返回一个json数组字符串
     */
    public static <T> String parseArray(T obj, String... excludes) {
        JsonConfig config = new JsonConfig();
        config.setExcludes(excludes); //设置不包含json转换时哪些属性
        //阻止出现死循环
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return JSONArray.fromObject(obj, config).toString();
    }

    /**
     * 将数组（Set,Collection,Array）转换成一个json数组字符串
     *
     * @param obj 任意Set,Collection对象，或者Array数组（如String[]）
     * @return 返回一个json数组字符串
     */
    public static <T> String parseArray(T obj) {
        //阻止出现死循环
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        return JSONArray.fromObject(obj, jsonConfig).toString();
    }

    /**
     * 将json对象字符串转换成一个普通java对象
     *
     * @param json  json对象字符串
     * @param clazz 普通java对象的Class对象
     * @return 封装解析了json数据的普通java对象
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        return (T) JSONObject.toBean(JSONObject.fromObject(json), clazz);
    }

    /**
     * 将json数组字符串转换成一个List对象
     *
     * @param json json数组字符串
     * @return 一个List对象
     */
    public static <T> List<T> toList(String json, Class<T> clazz) {
        return Arrays.asList((T[]) JSONArray.toArray(JSONArray.fromObject(json), clazz));
    }
}
