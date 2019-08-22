package com.zakj.swkj.bean;

import java.util.HashSet;
import java.util.Set;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/19
 * Time: 18:12
 * Description:  新闻表的实体类
 **/
public class News {
    //新闻的唯一id，使用“时间戳”作为id
    private String id;
    //静态页面url
    private String page_url;
    //标题图片，新闻展示页面中的新闻图片
    private String title_picture_url;
    //创建日期(包括年日月时分秒) - 格式：YY-MM-dd HH:mm:ss
    private String create_date;
    //新闻标题
    private String title;
    //新闻来源
    private String origin;
    //新闻发布者
    private String news_author;
    //新闻浏览量
    private Long page_view;
    //新闻描述
    private String news_description;
    //TODO 新闻图片集   完成新闻图片集的存储方式和url保存
    private Set<String> news_picture_urls = new HashSet<>();

    private String preNews_title;
    private String preNews_page_url;

    private String nextNews_title;
    private String nextNews_page_url;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getTitle_picture_url() {
        return title_picture_url;
    }

    public void setTitle_picture_url(String title_picture) {
        this.title_picture_url = title_picture;
    }

    public String getCreate_date() {
        return create_date;
    }

    public void setCreate_date(String create_date) {
        this.create_date = create_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getNews_author() {
        return news_author;
    }

    public void setNews_author(String news_author) {
        this.news_author = news_author;
    }

    public Long getPage_view() {
        return page_view;
    }

    public void setPage_view(Long page_view) {
        this.page_view = page_view;
    }

    public String getNews_description() {
        return news_description;
    }

    public void setNews_description(String news_description) {
        this.news_description = news_description;
    }

    public String getPreNews_page_url() {
        return preNews_page_url;
    }

    public void setPreNews_page_url(String preNews_page_url) {
        this.preNews_page_url = preNews_page_url;
    }

    public String getNextNews_page_url() {
        return nextNews_page_url;
    }

    public void setNextNews_page_url(String nextNews_page_url) {
        this.nextNews_page_url = nextNews_page_url;
    }

    public String getPreNews_title() {
        return preNews_title;
    }

    public void setPreNews_title(String preNews_title) {
        this.preNews_title = preNews_title;
    }

    public String getNextNews_title() {
        return nextNews_title;
    }

    public void setNextNews_title(String nextNews_title) {
        this.nextNews_title = nextNews_title;
    }
}
