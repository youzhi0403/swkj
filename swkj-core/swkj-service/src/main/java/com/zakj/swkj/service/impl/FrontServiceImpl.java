package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.SlideImg;
import com.zakj.swkj.dao.IFrontDao;
import com.zakj.swkj.service.IFrontService;
import com.zakj.swkj.utils.FreeMarkerUtils;
import com.zakj.swkj.utils.PageBean;
import com.zakj.swkj.utils.PathUtils;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/14
 * Time: 10:13
 * Description:
 **/
@Service
@Transactional
public class FrontServiceImpl implements IFrontService {

    private final IFrontDao dao;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public FrontServiceImpl(IFrontDao dao, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.dao = dao;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public void findSlideImgList(PageBean pageBean) {
        dao.pageQuery(pageBean);
    }

    @Override
    public long getSlideImgCount() {
        return dao.getSlideImgCount();
    }

    @Override
    public void save(SlideImg slideImg, HttpServletRequest request) throws IOException, TemplateException {
        dao.save(slideImg);

        //生成静态页面
        makeIndexHtml(request);
    }

    @Override
    public void deleteImgsFromDiskAndDB(List<SlideImg> slideImgs, HttpServletRequest request) throws IOException, TemplateException {
        //先从数据库中删除
        dao.deleteAll(slideImgs);

        //在从硬盘中， 删除硬盘中的图片
        for (SlideImg slideImg : slideImgs) {
            File file = new File(PathUtils.servletContextRealPath(request) + slideImg.getImg_url());
            file.delete();
        }

        //生成静态页面
        makeIndexHtml(request);
    }

    @Override
    public void updateImg(SlideImg slideImg, HttpServletRequest request) throws IOException, TemplateException {
        dao.update(slideImg);

        //生成静态页面
        makeIndexHtml(request);
    }

    @Override
    public List findRecommendProductList() {
        return dao.findRecommendProductList();
    }

    @Override
    public List findProductIdAndNameList() {
        return dao.findProductIdAndNameList();
    }

    @Override
    public void addRecProduct(String rec_pro_id, String isRec, HttpServletRequest request) throws IOException, TemplateException {
        dao.updateProductById(rec_pro_id, isRec);

        //生成静态页面
        makeIndexHtml(request);
    }

    @Override
    public void deleteRecPro(List<String> strIds, HttpServletRequest request) throws IOException, TemplateException {
        for (String id : strIds) {
            dao.updateProductById(id, "0");
        }

        //生成静态页面
        makeIndexHtml(request);
    }

    @Override
    public long getRecProCount(String isRec) {
        return dao.getRecProCount(isRec);
    }

    //生成前端“首页”的静态页面
    public void makeIndexHtml(HttpServletRequest request) throws IOException, TemplateException {
        String uri = "/index.html"; //生成新的相对路径
        HashMap<String, Object> map = new HashMap<>();
        map.put("slideImgs", dao.findSlideImgsByIndex());
        map.put("contextPath", request.getContextPath());

        HashMap<String, String> recProMap;
        ArrayList<HashMap<String, String>> recProList = new ArrayList<>();
        List<Object[]> list = (List<Object[]>) dao.findRecommendProductList();
        for (Object[] objects : list){
            recProMap = new HashMap<>();
            recProMap.put("product_name", (String) objects[1]);
            recProMap.put("picture_url", (String) objects[2]);
            recProMap.put("page_url", (String) objects[3]);
            recProList.add(recProMap);
        }
        map.put("recProducts", recProList);
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "index.ftl",
                PathUtils.servletContextRealPath(request)+ uri);
    }
}
