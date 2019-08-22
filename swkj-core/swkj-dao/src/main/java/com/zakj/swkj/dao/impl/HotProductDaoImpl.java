package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IHotProductDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.utils.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 14:46
 * Description:
 **/
@Repository
@Scope("prototype")
public class HotProductDaoImpl extends BaseDaoImpl<Product> implements IHotProductDao {

    @Override
    public List<Product> findHotProduct(Product model, PageBean pageBean) {
        List<Product> list = null;
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("from Product p where isHot = '1'");
        if(StringUtil.isNotEmpty(model.getProduct_name())){
            sb.append(" and product_name like '%"+model.getProduct_name()+"%'");
        }
        if(model.getSeries().getId()!=0){
            sb.append(" and p.series.id="+model.getSeries().getId());
        }
        Query query = session.createQuery(sb.toString());
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getRows());
        }
        list = query.list();
        return list;
    }

    @Override
    public int hotProductCount(Product model) {
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("select count(*) from Product p where isHot = '1'");
        if(StringUtil.isNotEmpty(model.getProduct_name())){
            sb.append(" and product_name like '%"+model.getProduct_name()+"%'");
        }
        if(model.getSeries().getId()!=0){
            sb.append(" and p.series.id="+model.getSeries().getId());
        }
        Query query = session.createQuery(sb.toString());
        Object obj = query.uniqueResult();
        Long lobj = (Long)obj;
        int count = lobj.intValue();
        return count;
    }

    @Override
    public Long findSeriesIdBySeriesName(String seriesName) {
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("from Series where s_name='"+seriesName+"'");
        Query query = session.createQuery(sb.toString());
        List<Series> list = query.list();
        if(list.size()==0){
            return -1l;
        }
        return list.get(0).getId();
    }

    @Override
    public List<Product> getNotHotProduct() {
        Session session = null;
        session = currentSession();
        Query query = session.createQuery("from Product where isHot <> '1' or isHot is null");
        return query.list();
    }

    @Override
    public int addHotProducts(String str) {
        Session session = null;
        session = currentSession();
        //update Product p set p.isHot='1' where id in('150821069965738','150822184289953')
        String hql = "update Product p set p.isHot='1' where id in("+str+")";
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }

    @Override
    public List<Product> findHotProductBySeriesId(Long seriesId) {
        Session session = null;
        session = currentSession();
        String hql = "from Product p where p.isHot = '1' and p.series.id="+seriesId;
        Query query = session.createQuery(hql);
        return query.list();
    }

    @Override
    public int deleteHotProduct(String delIds) {
        Session session = null;
        session  = currentSession();
        String hql = "update Product p set p.isHot='0' where id in("+delIds+")";
        Query query = session.createQuery(hql);
        return query.executeUpdate();
    }
}
