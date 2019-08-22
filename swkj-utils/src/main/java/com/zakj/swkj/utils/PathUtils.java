package com.zakj.swkj.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/23 0023
 * Time: 15:21
 * Description:  获取Url、Uri、各种path的工具类(需要依赖Servlet容器)
 **/
public final class PathUtils {

    /**
     * 获取当前请求的上下文路径(例如："/WebDemo" )
     *
     * @param request 请求对象
     * @return 返回上下文路径
     */
    public static String contextPath(HttpServletRequest request){
        return request.getContextPath();
    }

    /**
     * 获取当前请求的URI（例如："http://localhost:8080/WebDemo/" ）
     *
     * @param request 请求对象
     * @return 返回当前请求的URI路径
     */
    public static String basePath(HttpServletRequest request){
        return request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+contextPath(request)+"/";
    }

    /**
     * 获取远程主机ip地址（例如："127.0.0.1" ）
     *
     * @param request 请求对象
     * @return 远程主机ip地址
     */
    public static String remoteAddr(HttpServletRequest request){
        return request.getRemoteAddr();
    }

    /**
     * 获取当前请求的servlet的路径（例如："/welcome.jsp" ）
     *
     * @param request 请求对象
     * @return 当前请求的servlet的路径
     */
    public static String servletPath(HttpServletRequest request){
        return request.getServletPath();
    }

    /**
     * 当前请求的上下文真实路径（例如："D:\apache-tomcat-6.0.13\webapps\WebDemo\" ）
     *
     * @param request 请求对象
     * @return 请求的上下文真实路径
     */
    public static String servletContextRealPath(HttpServletRequest request){
        return request.getSession().getServletContext().getRealPath("/");
    }

    /**
     * 获取当前请求的URL（例如："http://localhost:8683/WebDemo/welcome.jsp" ）
     *
     * @param request 请求对象
     * @return 当前请求的URL
     */
    public static String requestURL(HttpServletRequest request){
        return request.getRequestURL().toString();
    }

    /**
     * 获取当前请求的URI（例如："/WebDemo/welcome.jsp" ）
     *
     * @param request 请求对象
     * @return 当前请求的URI
     */
    public static String requestURI(HttpServletRequest request){
        return request.getRequestURI();
    }

    /**
     * 获取当前请求的查询条件（例如："username=John" ）
     *
     * @param request 请求对象
     * @return 当前请求的查询条件
     */
    public static String queryString(HttpServletRequest request){
        return request.getQueryString();
    }
}
