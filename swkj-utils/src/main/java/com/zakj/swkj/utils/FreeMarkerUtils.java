package com.zakj.swkj.utils;

import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.*;
import java.nio.charset.Charset;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/23 0023
 * Time: 22:17
 * Description:  FreeMarker框架工具类
 **/
public final class FreeMarkerUtils {

    /**
     * 将ftl模板文件转换成html文件
     *
     * @param data 用于生成静态页面的数据
     * @param ftlName 模板名
     * @param htmlOutPath 生成的静态html文件保存的路径
     */
    public static void ftlToHtml(Object data, FreeMarkerConfigurer configurer, String ftlName, String htmlOutPath) throws IOException, TemplateException {
        Template template = configurer.getConfiguration().getTemplate(ftlName);
        //TODO 重点笔记：设置模板output-encoding字符集
        // 原因：官方说明：“模板使用的字符集和模板生成的输出内容的字符集是独立的”，所以需要将两个独立字符集设置统一，才不会出现乱码问题。
        //   设置模板字符集 - 可以在spring的配置文件中配置，详情看applicationContext.xml
        //指定文件输出的路径
        Writer w = new OutputStreamWriter(new FileOutputStream(new File(htmlOutPath)), "UTF-8");
        Environment env = template.createProcessingEnvironment(data, w);
        env.setOutputEncoding("UTF-8");
        env.process();
        w.close();
    }

}
