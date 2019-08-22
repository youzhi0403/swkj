package com.zakj.swkj.service;

import com.zakj.swkj.bean.Product;
import com.zakj.swkj.bean.Series;
import com.zakj.swkj.utils.PageBean;
import freemarker.template.TemplateException;
import org.hibernate.criterion.DetachedCriteria;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 15:30
 * Description:
 **/
public interface IProductService {

    void saveProduct(Product product, HttpServletRequest request) throws IOException, TemplateException;

    void findProductList(PageBean pageBean);

    void deleteProducts(List<Product> products, HttpServletRequest request) throws IOException, TemplateException;
}
