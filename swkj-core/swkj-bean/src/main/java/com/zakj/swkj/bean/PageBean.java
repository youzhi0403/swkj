package com.zakj.swkj.bean;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/9/25
 * Time: 15:17
 * Description:
 **/
public class PageBean {
    private int page;
    private int rows;
    private int start;


    public PageBean(int page, int rows) {
        super();
        this.page = page;
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }
    public void setPage(int page) {
        this.page = page;
    }
    public int getRows() {
        return rows;
    }
    public void setRows(int rows) {
        this.rows = rows;
    }
    public int getStart() {
        return (page-1)*rows;
    }
}
