package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Factory;
import com.zakj.swkj.dao.IFactoryDao;
import com.zakj.swkj.service.IFactoryService;
import com.zakj.swkj.utils.FreeMarkerUtils;
import com.zakj.swkj.utils.IDUtils;
import com.zakj.swkj.utils.PathUtils;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
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
@Service
@Transactional
public class FactoryServiceImpl implements IFactoryService {

    private final IFactoryDao dao;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public FactoryServiceImpl(IFactoryDao dao, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.dao = dao;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public void save(Factory factory, HttpServletRequest request) throws IOException, TemplateException {
        factory.setId("1");
        dao.saveOrUpdate(factory);

        //生成静态页面
        HashMap<String, String> map = new HashMap<>();
        map.put("contextPath", request.getContextPath());
        map.put("content", factory.getContent());
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "factory.ftl",
                PathUtils.servletContextRealPath(request)+"/factory.html");
    }

    @Override
    public List<Factory> getContent() {
        return dao.findAll();
    }
}
