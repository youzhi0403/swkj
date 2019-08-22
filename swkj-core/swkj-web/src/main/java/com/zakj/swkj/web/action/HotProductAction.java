package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Product;
import com.zakj.swkj.service.IHotProductService;
import com.zakj.swkj.web.action.base.BaseAction;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 14:40
 * Description:
 **/
@Controller
@Scope("prototype")
public class HotProductAction extends BaseAction<Product> {

    private final IHotProductService service;

    @Autowired
    public HotProductAction(IHotProductService service) {
        this.service = service;
    }

    private String page;
    private String rows;

    public void setRows(String rows) {
        this.rows = rows;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String hotProductList(){
        PageBean pageBean = new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
        Logger.getLogger(this.getClass()).info("model:"+model);
        JSONObject jsonObject = service.findHotProduct(model,pageBean);
        try {
            writeJson(jsonObject.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String getNotHotProduct(){
        try {
            write(service.getNotHotProduct().toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }

    public String add(){
        Logger.getLogger(this.getClass()).info("model:"+model);
        try {
            writeJson(service.addHotProducts(model, ServletActionContext.getRequest()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return NONE;
    }

    private String delIds;
    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public String delete(){
        try {
            writeJson(service.delete(delIds, ServletActionContext.getRequest()).toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }

}
