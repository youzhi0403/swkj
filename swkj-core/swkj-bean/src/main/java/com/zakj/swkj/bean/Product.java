package com.zakj.swkj.bean;

import java.math.BigDecimal;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/19
 * Time: 19:44
 * Description:  产品表的实体类
 **/
public class Product {
    //产品id - 时间戳(考虑UUID)
    private String id;
    //产品静态页面
    private String page_url;
    //产品名称
    private String product_name;
    //产品编号
    private String product_number;
    //产品描述
    private String product_desc;
    //容量
    private BigDecimal capacity;
    //功能
    private String function;
    //使用方法
    private String usage;
    //图片url
    private String picture_url;
    //针对（解决目标问题）
    private String target_problem;
    //方向
    private String direction;
    //产品（静态页面）创建时间
    private String create_time;
    //产品对应的系列
    private Series series;

    //产品是否为推荐产品（首页推荐产品）：1 是   0 否
    private String isRec;
    //产品是否为热销产品（热销模块）：1 是   0 否
    private String isHot;

    //不属于数据库字段，为了方便接收数据，转化为json
    private String seriesName;
    private Long sid;  //系列id

    public Long getSid() {
        return sid;
    }

    public void setSid(Long sid) {
        this.sid = sid;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public Product() {
    }

    public Product(String id, String page_url, String product_name, String product_number, String product_desc, BigDecimal capacity, String function, String usage, String picture_url, String create_time, Series series) {
        this.id = id;
        this.page_url = page_url;
        this.product_name = product_name;
        this.product_number = product_number;
        this.product_desc = product_desc;
        this.capacity = capacity;
        this.function = function;
        this.usage = usage;
        this.picture_url = picture_url;
        this.create_time = create_time;
        this.series = series;
    }

    public String getTarget_problem() {
        return target_problem;
    }

    public void setTarget_problem(String target_problem) {
        this.target_problem = target_problem;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public String getProduct_desc() {
        return product_desc;
    }

    public void setProduct_desc(String product_desc) {
        this.product_desc = product_desc;
    }

    public String getProduct_number() {
        return product_number;
    }

    public void setProduct_number(String product_number) {
        this.product_number = product_number;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

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

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public BigDecimal getCapacity() {
        return capacity;
    }

    public void setCapacity(BigDecimal capacity) {
        this.capacity = capacity;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getUsage() {
        return usage;
    }

    public void setUsage(String uer_method) {
        this.usage = uer_method;
    }

    public String getPicture_url() {
        return picture_url;
    }

    public void setPicture_url(String picture_url) {
        this.picture_url = picture_url;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getIsRec() {
        return isRec;
    }

    public void setIsRec(String isRec) {
        this.isRec = isRec;
    }

    public String getIsHot() {
        return isHot;
    }

    public void setIsHot(String isHot) {
        this.isHot = isHot;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", page_url='" + page_url + '\'' +
                ", product_name='" + product_name + '\'' +
                ", product_number='" + product_number + '\'' +
                ", product_desc='" + product_desc + '\'' +
                ", capacity=" + capacity +
                ", function='" + function + '\'' +
                ", usage='" + usage + '\'' +
                ", picture_url='" + picture_url + '\'' +
                ", create_time='" + create_time + '\'' +
                '}';
    }
}
