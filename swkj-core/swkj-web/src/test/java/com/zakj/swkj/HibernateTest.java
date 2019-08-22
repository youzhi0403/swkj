package com.zakj.swkj;

import com.zakj.swkj.bean.News;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.INewsDao;
import com.zakj.swkj.dao.IProductDao;
import com.zakj.swkj.utils.HibernateHelper;
import com.zakj.swkj.utils.JsonUtils;
import com.zakj.swkj.utils.LoggerUtils;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/24 0024
 * Time: 13:10
 * Description:
 **/
//帮我们创建容器
@RunWith(SpringJUnit4ClassRunner.class)
//指定使用哪个配置文件创建容器
@ContextConfiguration("classpath:applicationContext.xml")
@Transactional
@TransactionConfiguration(defaultRollback = true)
public class HibernateTest extends HibernateDaoSupport {

    @Resource // 根据类型注入spring工厂中的会话工厂对象sessionFactory
    public void setMySessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Test
    @Transactional
    public void test1() {
        Criteria criteria = HibernateHelper.buildEfficientCriteria(currentSession(),
                Product.class,
                "page_url", "product_name", "series");
        List<Product> list = criteria.list();
        for (Product o : list) {
            LoggerUtils.info(this,"product : " + o.getId() + "   ||   series : " + o.getSeries().getS_name());
        }
    }

    @Test
    @Transactional
    public void test2() {
        Criteria criteria = HibernateHelper.buildEfficientCriteria(currentSession(),
                Series.class);
        List<Series> series = criteria.list();
        for (Series s : series) {
            LoggerUtils.info(this,"series : " + s.getS_name());
            Set<Product> list = s.getProducts();
            for (Product o : list) {
                LoggerUtils.info(this,"product : " + o.getProduct_name());
            }
        }
        LoggerUtils.info(this,JsonUtils.parseArray(series));
    }

    @Test
    @Transactional
    public void test3() {
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
                        new Long[]{2L},
                        null
                });

        List<Product> products = criteria.list();
        for (Product p : products) {
            LoggerUtils.info(this,p);
            LoggerUtils.info(this,p.getSeries());
        }
        LoggerUtils.info(this,JsonUtils.parseArray(products));

//        List<Object> series = criteria.list();
//        for (Object o : series) {
//            LoggerUtils.info(this,o);
//        }
//        LoggerUtils.info(this,JsonUtils.parseArray(series));

//        List<Series> series = criteria.list();
//        for (Series s : series) {
//            LoggerUtils.info(this,"series : " + s.getS_name());
//            Set<Product> list = s.getProducts();
//            for (Product o : list) {
//                LoggerUtils.info(this,"product : " + o.getProduct_name());
//            }
//        }
    }

    @Test
    @Transactional
    public void test4() {
        String hql = "select p from Product as p , Series as s where p.series.id = s.id and s.id = ?";
        List<Product> list = (List<Product>) getHibernateTemplate().find(hql, 1L);
        LoggerUtils.info(this,JsonUtils.parseArray(list,"series"));
        LoggerUtils.info(this,JsonUtils.parseArray(JsonUtils.toList(JsonUtils.parseArray(list,"series"), Product.class)));
//        for (Product product : list) {
//            //这样查出来的Product，会包含series的代理对象实例，该代理对象实例包含一些没有get，set方法的属性，json解析时会报错：
//            //01:37:51,250  INFO JSONObject:769 - Property 'autoClear' of class org.hibernate.internal.SessionImpl has no read method. SKIPPED
//            //01:37:51,305  INFO JSONObject:769 - Property 'cascadeStyle' of class org.hibernate.persister.entity.SingleTableEntityPersister has no read method. SKIPPED
//            // has no read method. SKIPPED 表示没有get方法，
//            LoggerUtils.info(this,JsonUtils.parseObject(product,"series"));
//        }
    }

    @Resource
    private INewsDao dao;

    @Test
    @Transactional
    public void test05(){
        News news = new News();
        PageBean pageBean = new PageBean(1,10);
        List<Object> list = dao.newsList(news,pageBean);
        for (Object obj:list){
            News n = (News)obj;
            Logger.getLogger(this.getClass()).info("id:"+n.getId()+"  title:"+n.getTitle());
        }
    }

    @Resource
    private IProductDao productDao;

    @Test
    @Transactional
    public void test06(){
        Logger.getLogger(this.getClass()).info("异常前");
        try {
            Series series = new Series();
            series.setId(1L);
            Product product1 = new Product("150889587459852", "11", "11", "11",
                    "11", new BigDecimal("1"), "2", "3", "4", "1", series);
            productDao.save(product1);
            doException();
            List<Product> list = productDao.findProductListBySid(1L);
            for (Product product : list) {
                Logger.getLogger(this.getClass()).info(product);
            }
        } catch (Exception e){
            Logger.getLogger(this.getClass()).error(e.getMessage());
        }
        Logger.getLogger(this.getClass()).info("异常后");
    }

    private void doException(){
        throw new RuntimeException("发生异常");
    }

}
