package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.SlideImg;
import com.zakj.swkj.dao.IFrontDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/14
 * Time: 11:31
 * Description:
 **/
@Repository
public class FrontDaoImpl extends BaseDaoImpl<SlideImg> implements IFrontDao {
    @Override
    public long getSlideImgCount() {
        String hql = "select count(*) from SlideImg s where s.deltag = :deltag";
        List<Long> countList =
                (List<Long>) getHibernateTemplate().findByNamedParam(hql,"deltag", "0");
        return countList.get(0);
    }

    @Override
    public List<SlideImg> findSlideImgsByIndex() {
        String hql = "from SlideImg s where s.deltag = :deltag order by s.img_index asc";
        return (List<SlideImg>) getHibernateTemplate().findByNamedParam(hql, "deltag", "0");
    }

    @Override
    public List findRecommendProductList() {
        String hql = "select p.id, p.product_name, p.picture_url, p.page_url from Product p where p.isRec = :isRec";
        return getHibernateTemplate().findByNamedParam(hql, "isRec", "1");
    }

    @Override
    public List findProductIdAndNameList() {
        String hql = "select p.id, p.product_name, p.picture_url from Product p ";
        return getHibernateTemplate().find(hql);
    }

    @Override
    public void updateProductById(String rec_pro_id, String isRec) {
        String hql = "update Product p set p.isRec = :isRec where p.id = :id";
        Query query = currentSession().createQuery(hql);
        query.setParameter("isRec", isRec);
        query.setParameter("id", rec_pro_id);
        query.executeUpdate();
    }

    @Override
    public long getRecProCount(String isRec) {
        String hql = "select count(*) from Product p where p.isRec = :isRec";
        List<Long> list = (List<Long>) getHibernateTemplate().findByNamedParam(hql, "isRec", isRec);
        return list.get(0);
    }
}
