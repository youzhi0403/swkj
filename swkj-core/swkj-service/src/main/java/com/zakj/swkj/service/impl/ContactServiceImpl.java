package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Contact;
import com.zakj.swkj.dao.IContactDao;
import com.zakj.swkj.service.IContactService;
import com.zakj.swkj.utils.FreeMarkerUtils;
import com.zakj.swkj.utils.PathUtils;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/13
 * Time: 14:36
 * Description:
 **/
@Service
@Transactional
public class ContactServiceImpl implements IContactService {
    private final IContactDao dao;
    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public ContactServiceImpl(IContactDao dao, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.dao = dao;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }


    @Override
    public boolean update(Contact model, HttpServletRequest request) {
        //更新数据库之前,先获取数据库的picture_url，然后进行删除
        if (StringUtils.isBlank(model.getId())) {
            model.setId("1");
        } else {
            File file = new File(PathUtils.servletContextRealPath(request) + dao.findContactPageUrl("1"));
            file.delete();
        }

        dao.saveOrUpdate(model);
        //把contact的数据load出来，

            /*String uri = Constant.NEWS_INFO_HTML_OUT_DIR + "/" + IDUtils.getItemId()+".html";*/
        //更新页面
        HashMap<Object, Object> map = new HashMap<>();
        map.put("contact", model);
        map.put("contextPath", request.getContextPath());
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "contact.ftl",
                    PathUtils.servletContextRealPath(request) + "/contact.html");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Contact findContact(String id) {
        return dao.findContact(id);
    }
}
