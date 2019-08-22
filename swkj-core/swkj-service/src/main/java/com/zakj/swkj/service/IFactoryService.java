package com.zakj.swkj.service;


import com.zakj.swkj.bean.Factory;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/16
 * Time: 16:23
 * Description:
 **/
public interface IFactoryService {

    void save(Factory content, HttpServletRequest request) throws IOException, TemplateException;

    List<Factory> getContent();
}
