package com.zakj.swkj.utils;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.User;
import org.apache.struts2.ServletActionContext;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/23 0023
 * Time: 23:49
 * Description:   项目相关的一些公共工具类，需要依赖该项目使用
 **/
public final class CommonUtils {

    public static User getUser(){
        return (User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER_KEY);
    }

}
