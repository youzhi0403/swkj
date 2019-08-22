package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IHotProductDao;
import com.zakj.swkj.dao.IHotSeriesDao;
import com.zakj.swkj.dao.IProductDao;
import com.zakj.swkj.dao.ISeriesDao;
import com.zakj.swkj.service.IHotProductService;
import com.zakj.swkj.service.IHotSeriesService;
import com.zakj.swkj.service.ISeriesService;
import com.zakj.swkj.utils.FreeMarkerUtils;
import com.zakj.swkj.utils.IDUtils;
import com.zakj.swkj.utils.PageBean;
import com.zakj.swkj.utils.PathUtils;
import exception.SeriesException;
import freemarker.template.TemplateException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.util.HSSFColor;
import org.hibernate.FlushMode;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
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
 * Date: 2017/10/23
 * Time: 11:26
 * Description:
 **/
@Service
@Transactional
public class SeriesServiceImpl implements ISeriesService {

    private final ISeriesDao dao;
    private final IProductDao productDao;
    private final IHotProductDao hotProductDao;
    private final IHotSeriesDao hotSeriesDao;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public SeriesServiceImpl(ISeriesDao dao, FreeMarkerConfigurer freeMarkerConfigurer,
                             IProductDao productDao, IHotProductDao hotProductDao,
                             IHotSeriesDao hotSeriesDao) {
        this.dao = dao;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        this.productDao = productDao;
        this.hotProductDao = hotProductDao;
        this.hotSeriesDao = hotSeriesDao;
    }

    @Override
    public void pageQuery(PageBean<Series> pageBean) {
        dao.pageQuery(pageBean);
    }

    @Override
    public void saveOrUpdate(Series series, HttpServletRequest request) throws IOException, TemplateException {
        HashMap<String, Object> map = new HashMap<>();
        Series seriesTemp = series;

        if (series.getId() != null) {
            //获取该系列的全部产品
            map.put("pList", productDao.findProductListBySid(series.getId()));
            seriesTemp = dao.findById(series.getId());
            seriesTemp.setS_name(series.getS_name());
            seriesTemp.setPicture_url(series.getPicture_url());
        } else {
            //生成该系列的“产品列表”静态页面的相对路径
            String uri = Constant.PRODUCT_LINE_HTML_OUT_DIR + "/" + IDUtils.getItemId() + ".html";
            //保存系列页面url
            seriesTemp.setPage_url(uri);
        }

        map.put("sname", series.getS_name());
        map.put("contextPath", request.getContextPath());
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "productLine.ftl",
                PathUtils.servletContextRealPath(request) + series.getPage_url());

        dao.saveOrUpdate(seriesTemp);

        if (series.getId() != null) {
            //更新热销系列相关的页面
            updateHotSeries(seriesTemp, request);
        }

        //更新系列列表页面
        updateProducts(request);
    }

    @Override
    public List<Series> findAllSeries() {
        return dao.findAll();
    }

    @Override
    public void deleteBatch(List<Series> series, HttpServletRequest request) throws IOException, TemplateException, SeriesException {

        for (Series s : series) {
            //删除系列页面
            new File(PathUtils.servletContextRealPath(request) + s.getPage_url()).delete();
            //删除热销系列页面
            new File(PathUtils.servletContextRealPath(request) + s.getHotPage_url()).delete();
            //删除系列图片
            new File(PathUtils.servletContextRealPath(request) + s.getPicture_url()).delete();

            //更新系列列表页面
            updateProducts(request);
        }
    }

    @Override
    public List<Series> deleteSeriesByIds(String[] ids) {
        ArrayList<Series> series = new ArrayList<>();

        //先删除系列数据，防止系列数据删除失败，但是页面已经被删除
        for (String id : ids) {
            Series s = dao.findById(Long.valueOf(id));
            dao.delete(s); //删除操作，需要在事务提交之后才会执行。
            series.add(s);
        }
        return series;
    }

    //更新系列列表页面
    private void updateProducts(HttpServletRequest request) throws IOException, TemplateException {
        HashMap<String, Object> map = new HashMap<>();
        //查询所有的系列信息
        List<Series> sLins = dao.findAll();
        //更新产品的“系列列表”静态页面
        map.put("contextPath", request.getContextPath());
        map.put("pLines", sLins);
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "products.ftl",
                PathUtils.servletContextRealPath(request) + Constant.PRODUCT_SERIES_LINE_HTML_OUT_DIR + "/products.html");
    }

    //更新热销系列相关的页面
    public void updateHotSeries(Series series, HttpServletRequest request) throws IOException, TemplateException {
        //更新“热销系列”静态页面
        //获得该系列所有的产品
        //重写该系列的网页（包含该系列的所有产品）
        HashMap<String, Object> map = new HashMap<>();
        map.put("series", series);
        map.put("list", hotProductDao.findHotProductBySeriesId(series.getId()));
        map.put("contextPath", request.getContextPath());
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "hotProductsOfASeries.ftl",
                PathUtils.servletContextRealPath(request) + series.getHotPage_url());

        //生成热销产品系列列表的页面
        //获取所有的系列
        map.clear();
        map.put("list", hotSeriesDao.findSeriesOfHotProduct());
        map.put("contextPath", request.getContextPath());
        //重写热销页面（包含热销的全部系列）
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "hotProducts.ftl",
                PathUtils.servletContextRealPath(request) + "selling.html");
    }
}
