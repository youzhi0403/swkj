package com.zakj.swkj.bean;

import java.util.Set;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/19
 * Time: 19:12
 * Description:  产品系列表的实体类
 **/
public class Series {
    //系列id
    private Long id;
    //系列静态页面url
    private String page_url;
    //热销系列静态页面url
    private String hotPage_url;
    //系列图片
    private String picture_url;
    //系列名称
    private String s_name;
    //系列下的产品集合
    private Set<Product> products;

    public Series() {
    }

    public Series(Long id, String page_url, String hotPage_url, String picture_url, String s_name, Set<Product> products) {
        this.id = id;
        this.page_url = page_url;
        this.hotPage_url = hotPage_url;
        this.picture_url = picture_url;
        this.s_name = s_name;
        this.products = products;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public Set<Product> getProducts() {
        return products;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPage_url() {
        return page_url;
    }

    public void setPage_url(String page_url) {
        this.page_url = page_url;
    }

    public String getS_name() {
        return s_name;
    }

    public void setS_name(String s_name) {
        this.s_name = s_name;
    }

    public String getHotPage_url() {
        return hotPage_url;
    }

    public void setHotPage_url(String hotPage_url) {
        this.hotPage_url = hotPage_url;
    }

    @Override
    public String toString() {
        return "Series{" +
                "id=" + id +
                ", page_url='" + page_url + '\'' +
                ", s_name='" + s_name + '\'' +
                '}';
    }

}
