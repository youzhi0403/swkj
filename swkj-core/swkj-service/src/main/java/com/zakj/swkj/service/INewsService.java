package com.zakj.swkj.service;

import com.zakj.swkj.bean.News;
import com.zakj.swkj.bean.PageBean;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/9/25
 * Time: 17:31
 * Description:
 **/
public interface INewsService {

    public JSONObject newsListExceptDescription(News news, PageBean pageBean);

    int newsDelete(String delIds, HttpServletRequest request);


    boolean add(News news, HttpServletRequest request);

    boolean update(News news, HttpServletRequest request);

    void saveOrUpdatePage(HttpServletRequest request);

    String getNewsContentById(String id);
}
