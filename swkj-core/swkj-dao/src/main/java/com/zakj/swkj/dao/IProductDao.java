package com.zakj.swkj.dao;

import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.base.IBaseDao;
import com.zakj.swkj.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 15:34
 * Description:
 **/
public interface IProductDao extends IBaseDao<Product>{

    /**
     *  根据系列id查找该系列对应的静态页面的Url
     *
     * @param sid 系列id
     * @return 对应系列id的静态页面的Url
     */
    Series findSeriesBySid(Long sid);

    List<Product> findProductListBySid(Long sid);

    List<Product> findProductList(PageBean pageBean);
}
