package com.zakj.swkj.service;

import com.zakj.swkj.bean.SlideImg;
import com.zakj.swkj.utils.PageBean;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/14
 * Time: 10:12
 * Description:
 **/
public interface IFrontService {

    void findSlideImgList(PageBean pageBean);

    long getSlideImgCount();

    void save(SlideImg slideImg, HttpServletRequest request) throws IOException, TemplateException;

    void deleteImgsFromDiskAndDB(List<SlideImg> slideImgs, HttpServletRequest request) throws IOException, TemplateException;

    void updateImg(SlideImg slideImg, HttpServletRequest request) throws IOException, TemplateException;

    List findRecommendProductList();

    List findProductIdAndNameList();

    void addRecProduct(String rec_pro_id, String isRec, HttpServletRequest request) throws IOException, TemplateException;

    void deleteRecPro(List<String> strId, HttpServletRequest request) throws IOException, TemplateException;

    long getRecProCount(String isRec);

    void makeIndexHtml(HttpServletRequest request) throws IOException, TemplateException;
}
