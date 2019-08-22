package com.zakj.swkj.service;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 14:44
 * Description:
 **/
public interface IHotProductService {
    JSONObject findHotProduct(Product model, PageBean pageBean);

    JSONArray getNotHotProduct();

    JSONObject addHotProducts(Product model, HttpServletRequest request);

    JSONObject delete(String delIds, HttpServletRequest request);
}
