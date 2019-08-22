package com.zakj.swkj.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/21
 * Time: 21:31
 * Description:
 **/
public final class DateUtils {

    public static String formatDate(String format, Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

}
