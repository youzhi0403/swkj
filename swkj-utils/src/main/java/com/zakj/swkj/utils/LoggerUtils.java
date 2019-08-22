package com.zakj.swkj.utils;

import org.apache.log4j.Logger;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/21
 * Time: 20:06
 * Description:  日志输出工具类，通过log4j.properties配置文件配置日志输出的位置（控制台/文本文件）
 **/
public class LoggerUtils {

    /**
     * 输出info级别的信息。
     *
     * @param msg 打印的信息
     * @param obj 打印日志所在的类，一般填this即可
     */
    public static void info(Object obj, Object msg){
        Logger.getLogger(obj.getClass()).info(msg);
    }

    /**
     * 输出error级别的信息
     *
     * @param msg 打印的信息
     * @param obj 打印日志所在的类，一般填this即可
     */
    public static void error(Object obj, Object msg) {
        Logger.getLogger(obj.getClass()).error(msg);
    }

    /**
     * 输出异常类的信息（包括 异常类型 和 异常描述信息）
     *
     * @param obj 打印日志所在的类，一般填this即可
     * @param e 一切继承与Exception的异常类型
     */
    public static void exception(Object obj, Exception e){
        error(obj, e.getClass().getSimpleName() +" - "+ e.getMessage());
    }
}
