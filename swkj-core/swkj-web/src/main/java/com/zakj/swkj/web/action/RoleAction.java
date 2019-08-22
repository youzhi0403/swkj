package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.service.IRoleService;
import com.zakj.swkj.utils.StringUtil;
import com.zakj.swkj.web.action.base.BaseAction;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;


/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 11:19
 * Description:
 **/
@Controller
@Scope("prototype")
public class RoleAction extends BaseAction<Role> {
    private final IRoleService roleService;

    @Autowired
    public RoleAction(IRoleService roleService) {
        this.roleService = roleService;
    }

    private String page;
    private String rows;

    public void setPage(String page) {
        this.page = page;
    }
    public void setRows(String rows) {
        this.rows = rows;
    }

    public String list(){
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));

        try {
            Logger.getLogger(this.getClass()).info("model:"+model);
            writeJson(roleService.roleList(model, pageBean).toString());

        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass()).info(e);
        }

        return NONE;
    }

    private String delIds;
    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public String delete(){
        JSONObject result = new JSONObject();
        int delNums = roleService.roleDelete(delIds);
        if (delNums > 0) {
            result.put("success", "true");
            result.put("delNums", delNums);
        } else {
            result.put("errorMsg", "删除失败");
        }
        try {
            writeJson(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    private String authIdsOrNames;
    public void setAuthIdsOrNames(String authIdsOrNames) {
        this.authIdsOrNames = authIdsOrNames;
    }

    public String save(){
        if(StringUtil.isEmpty(model.getId())){
            //添加操作
            roleService.add(model,authIdsOrNames);
        }else{
            //更新操作
            roleService.update(model,authIdsOrNames);
        }
        return NONE;
    }



}
