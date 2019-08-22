package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.News;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.dao.INewsDao;
import com.zakj.swkj.service.INewsService;
import com.zakj.swkj.utils.FreeMarkerUtils;
import com.zakj.swkj.utils.IDUtils;
import com.zakj.swkj.utils.PathUtils;
import freemarker.template.TemplateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/9/26
 * Time: 9:18
 * Description:
 **/
@Service
@Transactional
public class NewsServiceImpl implements INewsService {

    private final INewsDao dao;

    private final FreeMarkerConfigurer freeMarkerConfigurer;

    @Autowired
    public NewsServiceImpl(INewsDao dao, FreeMarkerConfigurer freeMarkerConfigurer) {
        this.dao = dao;
        this.freeMarkerConfigurer = freeMarkerConfigurer;
    }

    @Override
    public JSONObject newsListExceptDescription(News news, PageBean pageBean) {
        //在这里把数据封装好给Action层
        List<Object[]> list = dao.newsListExceptDescription(news,pageBean);
        JSONObject result = new JSONObject();
        JSONArray rows = new JSONArray();
        for(Object[] objects:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("page_url",objects[1]);
            jsonObject.put("title_picture_url",objects[2]);
            jsonObject.put("create_date",objects[3]);
            jsonObject.put("title",objects[4]);
            jsonObject.put("origin",objects[5]);
            jsonObject.put("news_author",objects[6]);
            jsonObject.put("page_view",objects[7]);
            /*jsonObject.put("news_description",n.getNews_description());*/
            rows.add(jsonObject);
        }
        result.put("rows",rows);
        org.apache.log4j.Logger.getLogger(this.getClass()).info(dao.newsCount(news));
        result.put("total",dao.newsCount(news));
        return result;
    }

    @Override
    public int newsDelete(String delIds, HttpServletRequest request) {
        //根据delIds获取news的title_picture_url和page_url,

        List<News> list = dao.findNewsByIds(delIds);

        HashMap<Object,Object> map = new HashMap<>();
        for(News n:list){
            File file1 = new File(PathUtils.servletContextRealPath(request)+n.getPage_url());
            File file2 = new File(PathUtils.servletContextRealPath(request)+n.getTitle_picture_url());
            file1.delete();
            file2.delete();


            //每删除一个新闻，就做以下的一次操作，更新数据。
            //根据page_url(唯一)将PreNews和nextNews查出来
            News preNews = dao.findNewsByPage_url(n.getPreNews_page_url());
            News nextNews = dao.findNewsByPage_url(n.getNextNews_page_url());


            if(preNews==null && nextNews!=null){               //当该n是第一个的情况,此时nextNews为第一个
                nextNews.setPreNews_title("没有了");
                nextNews.setPreNews_page_url("#");
                dao.update(nextNews);
                //更新页面
                map.put("news",nextNews);
                map.put("contextPath", request.getContextPath());
                try {
                    FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                            PathUtils.servletContextRealPath(request) + nextNews.getPage_url() );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                map.clear();

            }else if(nextNews==null && preNews!=null){       //当n是最后一个的情况，此时preNews为最后一个
                preNews.setNextNews_title("没有了");
                preNews.setNextNews_page_url("#");
                dao.update(preNews);
                //更新页面
                map.put("news",preNews);
                map.put("contextPath", request.getContextPath());
                try {
                    FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                            PathUtils.servletContextRealPath(request) + preNews.getPage_url() );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                map.clear();

            }else if(nextNews!=null && preNews!=null){
                nextNews.setPreNews_title(preNews.getTitle());
                nextNews.setPreNews_page_url(preNews.getPage_url());
                preNews.setNextNews_title(nextNews.getTitle());
                preNews.setNextNews_page_url(nextNews.getPage_url());
                dao.update(nextNews);
                dao.update(preNews);

                //更新页面
                map.put("news",preNews);
                map.put("contextPath", request.getContextPath());
                try {
                    FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                            PathUtils.servletContextRealPath(request) + preNews.getPage_url() );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                map.clear();

                map.put("news",nextNews);
                map.put("contextPath", request.getContextPath());
                try {
                    FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                            PathUtils.servletContextRealPath(request) + nextNews.getPage_url() );
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (TemplateException e) {
                    e.printStackTrace();
                }
                map.clear();
            }

        }

        //更新新闻列表页面
        int delNum = dao.delete(delIds);

        //更新新闻列表页面
        PageBean pageBean = null;
        List list_news = dao.newsListForSeriesPage(pageBean);

        map.put("contextPath", request.getContextPath());
        map.put("list", list_news);
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "news.ftl",
                    PathUtils.servletContextRealPath(request)+ "news/news.html" );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }

        return delNum;

    }

    @Override
    public boolean add(News news, HttpServletRequest request) {

        news.setPage_view(0l);
        news.setOrigin("本站");
        news.setCreate_date(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));

        news.setNextNews_page_url("#");
        news.setNextNews_title("没有了");
        //查询数据库最后一条数据
        News lastNews = dao.findLastNews();
        if(lastNews==null){
            news.setPreNews_title("没有了");
            news.setPreNews_page_url("#");
        }else{
            //再赋值当前这条数据
            news.setPreNews_title(lastNews.getTitle());
            news.setPreNews_page_url(lastNews.getPage_url());
        }
        Logger.getLogger(this.getClass()).info("news.getPreNews_title()"+news.getPreNews_title());
        Logger.getLogger(this.getClass()).info("news.getPreNews_page_url()"+news.getPreNews_page_url());
        Logger.getLogger(this.getClass()).info("news.getNextNews_title()"+news.getNextNews_title());
        Logger.getLogger(this.getClass()).info("news.getNextNews_page_url()"+news.getNextNews_page_url());

        //处理有关page_url的逻辑
        //根据 ftl模版 生成产品静态页面
        String uri = Constant.NEWS_INFO_HTML_OUT_DIR + "/" + IDUtils.getItemId()+".html";
        HashMap<Object,Object> map = new HashMap<>();
        map.put("news",news);
        map.put("contextPath", request.getContextPath());
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                    PathUtils.servletContextRealPath(request) + uri );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        map.clear();
        news.setPage_url(uri);
        if(lastNews!=null){
            lastNews.setNextNews_title(news.getTitle());
            lastNews.setNextNews_page_url(news.getPage_url());
            dao.update(lastNews);
            //更新完数据库后，完成页面的替换（lastNews）

            map.put("news",lastNews);
            map.put("contextPath", request.getContextPath());
            try {
                FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                        PathUtils.servletContextRealPath(request) + lastNews.getPage_url() );
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TemplateException e) {
                e.printStackTrace();
            }
            map.clear();

        }

        dao.add(news);
        return true;
    }

    @Override
    public boolean update(News news, HttpServletRequest request) {
        //更新页面
        //先判断news的picture_url与数据库的是否一致，如果一致则不做操作，不一致则删除图片，再进行更新。
        if(!news.getTitle_picture_url().equals(dao.getNewsTitle_picture_urlById(news.getId()))){
            String delete_url = dao.getNewsTitle_picture_urlById(news.getId());
            //根据拿到的url进行删除。
            File file = new File(PathUtils.servletContextRealPath(request)+delete_url);
            Logger.getLogger(this.getClass()).info("path:"+PathUtils.servletContextRealPath(request) + delete_url);
            file.delete();
        }
        //前台传过来的News只有id,title_picture_url,news_description,news_author,title
        //通过page_url把news查出来
        News currentNews = dao.findNewsByPage_url(news.getPage_url());
        currentNews.setTitle(news.getTitle());
        currentNews.setTitle_picture_url(news.getTitle_picture_url());
        currentNews.setNews_author(news.getNews_author());
        currentNews.setNews_description(news.getNews_description());

        dao.update(currentNews);
        String uri = news.getPage_url();
        HashMap<Object,Object> map = new HashMap<>();
        map.put("news",currentNews);
        map.put("contextPath", request.getContextPath());
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer ,"newsInfo.ftl",
                    PathUtils.servletContextRealPath(request) + uri );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public void saveOrUpdatePage(HttpServletRequest request) {
        //先从数据库查出所有的数据，封装成list
        News news = null;
        PageBean pageBean = null;
        List list = dao.newsListForSeriesPage(pageBean);

        //将list传给freemaker

        //根据ftl模板生成产品静态页面
        HashMap<Object, Object> map = new HashMap<>();
        map.put("contextPath", request.getContextPath());
        map.put("list", list);
        try {
            FreeMarkerUtils.ftlToHtml(map, freeMarkerConfigurer, "news.ftl",
                    PathUtils.servletContextRealPath(request)+ "news/news.html" );
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getNewsContentById(String id) {
        return dao.getNewsContentById(id);
    }

}
