package com.zakj.swkj.dao;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.base.IBaseDao;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 14:45
 * Description:
 **/
public interface IHotProductDao extends IBaseDao<Product> {
    List<Product> findHotProduct(Product model, PageBean pageBean);

    int hotProductCount(Product model);

    Long findSeriesIdBySeriesName(String seriesName);

    List<Product> getNotHotProduct();

    int addHotProducts(String str);

    List<Product> findHotProductBySeriesId(Long seriesId);

    int deleteHotProduct(String delIds);

}
