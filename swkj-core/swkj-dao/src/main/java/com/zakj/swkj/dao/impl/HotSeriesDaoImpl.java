package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IHotSeriesDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 17:02
 * Description:
 **/
@Repository
@Scope("prototype")
public class HotSeriesDaoImpl extends BaseDaoImpl<Series> implements IHotSeriesDao {

    @Override
    public List<Series> findSeriesOfHotProduct() {
        Session session  = null;
        session  = currentSession();
        List<Series> result = new ArrayList<>();
        String sql = "select DISTINCT series from Product where isHot = '1'";
        Query query = session.createQuery(sql);
        result = query.list();

        return result;
    }

    @Override
    public List<Series> findSeriesOfHotProductByIds(String ids) {
        Session session = null;
        session = currentSession();
        List<Series> result = new ArrayList<>();
        String hql = "select DISTINCT series from Product p where p.id in("+ids+")";
        Query query = session.createQuery(hql);
        result = query.list();

        return result;
    }
}
