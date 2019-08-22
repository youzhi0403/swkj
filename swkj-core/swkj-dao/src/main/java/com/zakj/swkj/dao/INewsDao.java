package com.zakj.swkj.dao;

import com.zakj.swkj.bean.News;
import com.zakj.swkj.bean.PageBean;

import java.sql.ResultSet;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/9/25
 * Time: 16:20
 * Description:
 **/
public interface INewsDao {
    public List<Object> newsList(News news, PageBean pageBean);

    public List<Object[]> newsListExceptDescription(News news, PageBean pageBean);

    public int newsCount(News news);

    int delete(String delIds);

    void add(News news);

    void update(News news);

    List newsListForSeriesPage(PageBean pageBean);

    String getNewsContentById(String id);

    String getNewsTitle_picture_urlById(String id);

    List<News> findNewsByIds(String delIds);

    News findLastNews();

    News findNewsByPage_url(String page_url);
}
