package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IHotProductDao;
import com.zakj.swkj.dao.IHotSeriesDao;
import com.zakj.swkj.service.IHotProductService;
import com.zakj.swkj.utils.*;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 14:44
 * Description:
 **/
@Service
@Scope("prototype")
@Transactional
public class HotProductServiceImpl implements IHotProductService {
    private final IHotProductDao dao;
    private final IHotSeriesDao seriesDao;
    private final FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    public HotProductServiceImpl(IHotProductDao dao, IHotSeriesDao seriesDao, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.dao = dao;
        this.seriesDao = seriesDao;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public JSONObject findHotProduct(Product model, PageBean pageBean) {
        //根据系列名字查出系列id，如果没有系列id，则将id赋值为0
        Long id;
        if(StringUtil.isEmpty(model.getSeriesName())){
            id = -0l;
        }else {
            id = dao.findSeriesIdBySeriesName(model.getSeriesName());
        }

        Series series = new Series();
        series.setId(id);
        model.setSeries(series);
        List<Product> list = dao.findHotProduct(model,pageBean);

        JSONObject result = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        for(Product p:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",p.getId());
            jsonObject.put("product_name",p.getProduct_name());
            jsonObject.put("product_number",p.getProduct_number());
            jsonObject.put("capacity",p.getCapacity());
            jsonObject.put("product_desc",p.getProduct_desc());
            jsonObject.put("seriesName",p.getSeries().getS_name());
            jsonArray.add(jsonObject);
        }
        result.put("rows",jsonArray);
        result.put("total",dao.hotProductCount(model));
        return result;
    }

    @Override
    public JSONArray getNotHotProduct() {
        List<Product> list = dao.getNotHotProduct();
        JSONArray result = new JSONArray();
        for(Product p:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",p.getId());
            jsonObject.put("nameAndSeries",p.getProduct_name()+"("+p.getSeries().getS_name()+")");
            result.add(jsonObject);
        }
        return result;
    }

    @Override
    public JSONObject addHotProducts(Product model, HttpServletRequest request) {
        String[] ids = model.getId().split(",");
        //每个id的前后带着空格，再使用前先用trim()处理
        StringBuffer sb = new StringBuffer("");
        for(int i=0;i<ids.length;i++){
            sb.append("'"+ids[i].trim()+"',");
        }
        String str = sb.toString().substring(0,sb.length()-1);
        //将整理好的数据给dao层进行处理
        int num = dao.addHotProducts(str);          //将cp04的isHot改为1，
        JSONObject result = new JSONObject();
        result.put("addNum",num);

        //更改成功后，使用freemaker生成网页
        //获取所有的系列
        List<Series> list = seriesDao.findSeriesOfHotProduct();    //应该是获取两个系列：xilie01,系列02
        //将系列使用freemaker生成hotProducts.html页面
        HashMap<Object,Object> map = new HashMap<>();
        //获取指定的系列页面，重写那个系列页面
        //获取series,然后获取该系列的热销路径，查询出所有该系列的热销产品，然后重写系列
        List<Series> list_series =  seriesDao.findSeriesOfHotProductByIds(str);   //获取一个系列：系列02
        //根据系列查出该系列的产品，然后重新生成
        for(Series s:list_series){                   //循环一次
            //获得该系列所有的产品
            List<Product> list_product = dao.findHotProductBySeriesId(s.getId());  //拿到产品cp04
            //重写该系列的网页（包含该系列的所有产品）
            map.put("series",s);
            map.put("list",list_product);
            map.put("contextPath", request.getContextPath());
            try {
                if(s.getHotPage_url() != null) {
                    FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "hotProductsOfASeries.ftl",
                            PathUtils.servletContextRealPath(request) + s.getHotPage_url());
                } else {
                    String hotProduct_uri = Constant.HOTPRODUCT_SERIES_HTML_OUT_DIR+"/"+ IDUtils.getItemId()+".html";
                    FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "hotProductsOfASeries.ftl",
                            PathUtils.servletContextRealPath(request)+ hotProduct_uri);
                    s.setHotPage_url(hotProduct_uri);
                    seriesDao.saveOrUpdate(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            map.clear();
        }

        //生成热销产品系列列表的页面
        map.clear();
        map.put("list",list);
        map.put("contextPath", request.getContextPath());
        //重写热销页面（包含热销的全部系列）
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"hotProducts.ftl",
                    PathUtils.servletContextRealPath(request) + "selling.html" );

        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        result.put("success",true);
        return result;
    }

    @Override
    public JSONObject delete(String delIds, HttpServletRequest request) {
        //先去数据库做操作，根据id将指定的product的isHot值设置为false
        int num = dao.deleteHotProduct(delIds);
        JSONObject result = new JSONObject();
        result.put("delNums",num);
        //拿到所有HotProduct，获取所有HotSeries，重新生成热门系列的网页
        //所有热门系列
        List<Series> list = seriesDao.findSeriesOfHotProduct();
        HashMap<Object,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("contextPath", request.getContextPath());
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"hotProducts.ftl",
                    PathUtils.servletContextRealPath(request) + "selling.html" );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        map.clear();

        //拿到被删的HotProduct，获取系列，找到系列的热门产品（isHot=1）,然后重新生成该系列页面
        List<Series> list_delSeries = seriesDao.findSeriesOfHotProductByIds(delIds);
        for(Series s:list_delSeries){
            List<Product> list_product = dao.findHotProductBySeriesId(s.getId());
            map.put("series",s);
            map.put("list",list_product);
            map.put("contextPath", request.getContextPath());
            try {
                FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"hotProductsOfASeries.ftl",
                        PathUtils.servletContextRealPath(request)+s.getHotPage_url() );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            map.clear();
        }
        result.put("success",true);
        return result;
    }

}
