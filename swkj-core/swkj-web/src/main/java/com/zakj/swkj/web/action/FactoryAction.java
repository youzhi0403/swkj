package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Factory;
import com.zakj.swkj.service.IFactoryService;
import com.zakj.swkj.utils.LoggerUtils;
import com.zakj.swkj.utils.ResultUtils;
import com.zakj.swkj.web.action.base.BaseAction;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/16
 * Time: 15:23
 * Description:  处理工厂模块相关请求的action
 **/
@Controller
@Scope("prototype")
public class FactoryAction extends BaseAction<Factory> {

    private final IFactoryService service;

    @Autowired
    public FactoryAction(IFactoryService service) {
        this.service = service;
    }

    public String uploadContent() throws IOException {
        try {
            service.save(model, ServletActionContext.getRequest());
            writeJson(ResultUtils.ok(null));
        } catch (Exception e){
            writeJson(ResultUtils.failure("保存失败！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

    public String getContent() throws IOException {
        try {
            if(service.getContent().size() > 0) {
                writeJson(ResultUtils.ok(service.getContent().get(0).getContent()));
            } else {
                writeJson(ResultUtils.ok(""));
            }
        } catch (Exception e){
            writeJson(ResultUtils.failure("获取内容失败！"));
            LoggerUtils.error(this, e.getMessage());
        }
        return NONE;
    }

}
