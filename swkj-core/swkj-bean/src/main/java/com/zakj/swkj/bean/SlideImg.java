package com.zakj.swkj.bean;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/14
 * Time: 10:55
 * Description:  前台轮播图片的实体类
 **/
public class SlideImg {

    private Long id;  //自增主键

    private String img_url; //图片路径

    private String deltag; //是否删除标志

    private String img_index; //轮播图的下角标，用于决定图片轮播的先后顺序

    public SlideImg() {
    }

    public SlideImg(Long id, String img_url, String deltag, String img_index) {
        this.id = id;
        this.img_url = img_url;
        this.deltag = deltag;
        this.img_index = img_index;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    public String getDeltag() {
        return deltag;
    }

    public void setDeltag(String deltag) {
        this.deltag = deltag;
    }

    public String getImg_index() {
        return img_index;
    }

    public void setImg_index(String img_index) {
        this.img_index = img_index;
    }
}
