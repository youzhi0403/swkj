package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IProductDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.utils.HibernateHelper;
import com.zakj.swkj.utils.PageBean;
import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 15:36
 * Description: 产品模块的dao层
 **/
@Repository
public class ProductDaoImpl extends BaseDaoImpl<Product> implements IProductDao {

    @Override
    public Series findSeriesBySid(Long sid) {
        //创建高效查询Criteria的实例，只查询指定字段。
        Criteria criteria = HibernateHelper.buildEfficientCriteria(currentSession(),
                Series.class, new String[]{"id"},new Object[]{sid});
        return (Series) criteria.uniqueResult();
//        String hql = "from Series s where s.id = ?";
//        List<?> list = getHibernateTemplate().find(hql, sid);
//        return list != null && list.size() > 0 ? (Series) list.get(0) : null;
    }

    @Override
    public List<Product> findProductListBySid(Long sid) {
        Criteria criteria = HibernateHelper.buildEfficientCriteria(currentSession(),
                new Object[]{
                        Product.class,
                        null,
                        null,
                        new String[]{"product_name", "picture_url", "page_url"}
                },
                new Object[]{
                        "series",
                        new String[]{"id"},
                        new Long[]{sid},
                        null
                });
        return criteria.list();
    }

    @Override
    public List<Product> findProductList(PageBean pageBean) {

        return null;
    }
}
