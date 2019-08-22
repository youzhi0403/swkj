package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IProductDao;
import com.zakj.swkj.service.IFrontService;
import com.zakj.swkj.service.IProductService;
import com.zakj.swkj.service.ISeriesService;
import com.zakj.swkj.utils.FreeMarkerUtils;
import com.zakj.swkj.utils.IDUtils;
import com.zakj.swkj.utils.PageBean;
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
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 15:32
 * Description: 产品模板逻辑处理层
 **/
@Service
@Transactional
public class ProductServiceImpl implements IProductService {

    private final IProductDao dao;
    private ISeriesService seriesService;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    private final IFrontService frontService;

    @Autowired
    public ProductServiceImpl(IProductDao dao, ISeriesService seriesService, FreeMarkerConfigurer freeMarkerConfigurer, IFrontService frontService) {
        this.dao = dao;
        this.seriesService = seriesService;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
        this.frontService = frontService;
    }

    /**
     * 保存产品数据，并生成一个产品信息展示的静态页面。如果该产品是属于一个新的系列，还要生成产品系列展示的静态页面，
     *  因为需要更新一下产品系列展示页面的系列列表。
     *
     * @param product 封装了产品数据的实体类
     */
    public void saveProduct(Product product, HttpServletRequest request) throws IOException, TemplateException {

        //根据ftl模板生成“产品详情”静态页面
        String uri = Constant.PRODUCT_INFO_HTML_OUT_DIR +"/"+IDUtils.getItemId()+".html"; //生成的静态页面的相对路径
        //处理一下文本数据，将回车（\r\n）替换成<br>，让文本数据在html页面中有回车的效果
        product.setProduct_desc(product.getProduct_desc().replace("\r\n","<br>"));
        product.setDirection(product.getDirection().replace("\r\n","<br>"));
        product.setUsage(product.getUsage().replace("\r\n","<br>"));
        product.setTarget_problem(product.getTarget_problem().replace("\r\n","<br>"));
        product.setFunction(product.getFunction().replace("\r\n","<br>"));
        //封装模板需要的数据
        HashMap<Object, Object> map = new HashMap<>();
        map.put("contextPath", request.getContextPath());
        map.put("product", product);
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "productInfo.ftl",
                PathUtils.servletContextRealPath(request)+ uri );
        //保存“产品详情”静态页面的访问uri
        product.setPage_url(uri);

        //恢复回车效果
        product.setProduct_desc(product.getProduct_desc().replace("<br>","\r\n"));
        product.setDirection(product.getDirection().replace("<br>","\r\n"));
        product.setUsage(product.getUsage().replace("<br>","\r\n"));
        product.setTarget_problem(product.getTarget_problem().replace("<br>","\r\n"));
        product.setFunction(product.getFunction().replace("<br>","\r\n"));

        //先清除之前的数据
        map.clear();
        //保存产品的添加时间
        product.setCreate_time(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));

        if (StringUtils.isBlank(product.getId())) {
            //添加一个唯一的id
            product.setId(IDUtils.getItemId());
        }

        dao.saveOrUpdate(product);

        //判断更新的产品是否为推荐产品
        if (product.getIsRec().equals("1")) {
            //更新推荐产品列表
            frontService.makeIndexHtml(request);
        }

        //判断是不是热销产品
        if (product.getIsHot().equals("1")){
            //查找该产品的系列信息
            Series series = dao.findSeriesBySid(product.getSeries().getId());
            //判断该系列之前是否为热销产品的系列，即其HotPage_url是否为null
            if (StringUtils.isBlank(series.getHotPage_url())) {
                series.setHotPage_url(Constant.HOTPRODUCT_SERIES_HTML_OUT_DIR + "/" + IDUtils.getItemId() + ".html");
            }
            //更新热销系列相关页面
            seriesService.updateHotSeries(series, request);
        }

        //更新产品列表页面
        updateProductLine(product, request);
    }

    @Override
    public void findProductList(PageBean pageBean) {
        dao.pageQuery(pageBean);
    }

    @Override
    public void deleteProducts(List<Product> products, HttpServletRequest request) throws IOException, TemplateException {
        //先查出需要删除的产品的信息。
        ArrayList<Product> list = new ArrayList<>();
        for (Product product : products) {
            product = dao.findById(product.getId());
            if (Objects.equals(product.getIsRec(), "1")) {
                throw new RuntimeException("产品“"+product.getProduct_name()+"”是推荐产品，不能删除！");
            }
            if (Objects.equals(product.getIsHot(), "1")){
                throw new RuntimeException("产品“"+product.getProduct_name()+"”是热销产品，不能删除！");
            }
            list.add(product);
        }

        //删除数据库中的产品
        dao.deleteAll(list);

        ArrayList<Long> sIds = new ArrayList<>();//保存所删除的产品所在的系列的id
        for (Product product : list) {
            if (!sIds.contains(product.getSeries().getId())) {
                //更新产品列表页面，
                updateProductLine(product, request);
                sIds.add(product.getSeries().getId());
            }

            //删除页面和图片
            new File(PathUtils.servletContextRealPath(request)+product.getPage_url()).delete();
            new File(PathUtils.servletContextRealPath(request)+product.getPicture_url()).delete();
        }

    }

    //更新产品列表页面
    private void updateProductLine(Product product, HttpServletRequest request) throws IOException, TemplateException {
        HashMap<String, Object> map = new HashMap<>();
        //根据系列id查询该系列的所有信息
        Series series = dao.findSeriesBySid(product.getSeries().getId());
        //根据系列id查询该系列的所有产品数据（图片路径，产品名称）
        List<Product> products = dao.findProductListBySid(product.getSeries().getId());
        //封装模板需要的数据
        map.put("contextPath", request.getContextPath());
        map.put("sname", series.getS_name());
        map.put("pList", products);
        System.out.println(series.getPage_url());
        //更新该系列的“产品列表”静态页面
        FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "productLine.ftl",
                PathUtils.servletContextRealPath(request)+ series.getPage_url());
    }

}
