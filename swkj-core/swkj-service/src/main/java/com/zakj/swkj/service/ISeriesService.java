package com.zakj.swkj.service;

import com.zakj.swkj.bean.Series;
import com.zakj.swkj.utils.PageBean;
import exception.SeriesException;
import freemarker.template.TemplateException;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/23
 * Time: 11:25
 * Description:
 **/
public interface ISeriesService {

    void pageQuery(PageBean<Series> pageBean);

    void saveOrUpdate(Series model, HttpServletRequest request) throws IOException, TemplateException;

    List<Series> findAllSeries();

    void deleteBatch(List<Series> series, HttpServletRequest request) throws IOException, TemplateException, SeriesException;

    List<Series> deleteSeriesByIds(String[] ids);

    void updateHotSeries(Series series, HttpServletRequest request) throws IOException, TemplateException;
}
