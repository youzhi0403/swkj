package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.News;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.dao.INewsDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.utils.StringUtil;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;



/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/9/25
 * Time: 16:22
 * Description:
 **/
@Repository
public class NewsDaoImpl extends BaseDaoImpl<News> implements INewsDao {

    public List<Object> newsList(News news, PageBean pageBean) {
        List<Object> list = null;
        Session session = null;
        session = currentSession();

        StringBuffer sb = new StringBuffer("from News");
        if (StringUtil.isNotEmpty(news.getTitle())) {
            sb.append(" and  title like '%" + news.getTitle() + "%'");
        }
        if (StringUtil.isNotEmpty(news.getNews_author())) {
            sb.append(" and news_author like '%" + news.getNews_author() + "%'");
        }
        String str = sb.toString().replaceFirst("and","where");

        Query query = session.createQuery(str);
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getRows());
        }
        list = query.list();

        return list;

    }

    @Override
    public List<Object[]> newsListExceptDescription(News news, PageBean pageBean) {
        List<Object[]> list = null;
        Session session = null;
        session = currentSession();

        StringBuffer sb = new StringBuffer("select id,page_url,title_picture_url,create_date,title,origin,news_author,page_view from News");
        if (StringUtil.isNotEmpty(news.getTitle())) {
            sb.append(" and  title like '%" + news.getTitle() + "%'");
        }
        if (StringUtil.isNotEmpty(news.getNews_author())) {
            sb.append(" and news_author like '%" + news.getNews_author() + "%'");
        }
        String str = sb.toString().replaceFirst("and","where");

        Query query = session.createQuery(str);
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getRows());
        }
        list = query.list();

        return list;
    }


    @Override
    public int newsCount(News news) {
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("select count(*) from News");
        if (StringUtil.isNotEmpty(news.getTitle())) {
            sb.append(" and  title like '%" + news.getTitle() + "%'");
        }
        if (StringUtil.isNotEmpty(news.getNews_author())) {
            sb.append(" and news_author like '%" + news.getNews_author() + "%'");
        }
        String str = sb.toString().replaceFirst("and","where");
        Query query = session.createQuery(str);
        Object obj = query.uniqueResult();
        Long lobj = (Long)obj;
        int count = lobj.intValue();
        return count;
    }

    @Override
    public int delete(String delIds) {
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("delete from News where id in("+delIds+")");
        Query query = session.createQuery(sb.toString());
        return query.executeUpdate();
    }

    @Override
    public void add(News news) {
        getHibernateTemplate().save(news);
    }

    public void update(News news){
        getHibernateTemplate().update(news);
    }

    @Override
    public List newsListForSeriesPage(PageBean pageBean) {
        List result = new ArrayList();

        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("select page_url,title_picture_url,title from News");
        Query query = session.createQuery(sb.toString());
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getRows());
        }
        List<Object[]> list = query.list();
        for(Object[] object:list){
            News news = new News();
            news.setPage_url((String)object[0]);
            news.setTitle_picture_url((String)object[1]);
            news.setTitle((String)object[2]);
            result.add(news);
        }
        return result;
    }

    @Override
    public String getNewsContentById(String id) {
        Session session = null;
        session = currentSession();
        News news = session.get(News.class,id);
        return news.getNews_description();
    }

    @Override
    public String getNewsTitle_picture_urlById(String id) {
        Session session = null;
        session = currentSession();
        String result = "";
        String hql = "select title_picture_url from News n where n.id='"+id+"'";
        Query query = session.createQuery(hql);
        List<String> list = query.list();
        for(String str:list){
            result = str;
        }

        return result;

    }

    @Override
    public List<News> findNewsByIds(String delIds) {
        Session session = null;
        session = currentSession();
        String hql = "from News n where n.id in("+delIds+")";
        Query query = session.createQuery(hql);

        return query.list();
    }

    @Override
    public News findLastNews() {
        Session session = null;
        session = currentSession();
        String hql = "from News order by id desc";
        Query query = session.createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(1);
        List<News> list = query.list();
        if (list.size()>0)
            return list.get(0);
        else
            return null;
    }

    @Override
    public News findNewsByPage_url(String page_url) {
        Session session = null;
        session = currentSession();
        String hql = "from News where page_url='"+page_url+"'";
        Query query = session.createQuery(hql);
        List list = query.list();
        if(list.size()>0){
            return (News)list.get(0);
        }else{
            return null;
        }
    }
}
