package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Function;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;
import com.zakj.swkj.dao.IFunctionDao;
import com.zakj.swkj.dao.IRoleDao;
import com.zakj.swkj.service.IRoleService;
import com.zakj.swkj.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 11:23
 * Description:
 **/
@Service
@Scope("prototype")
@Transactional
public class RoleServiceImpl implements IRoleService{
    private final IRoleDao roleDao;
    private final IFunctionDao functionDao;

    @Autowired
    public RoleServiceImpl(IRoleDao roleDao, IFunctionDao functionDao) {
        this.roleDao = roleDao;
        this.functionDao = functionDao;
    }

    @Override
    public JSONObject roleList(Role model, PageBean pageBean) {
        JSONObject jsonObject = new JSONObject();
        JSONArray rows = new JSONArray();
        List<Object> list = roleDao.list(model,pageBean);
        for(Object o:list){
            Role r = (Role)o;
            JSONObject jo = new JSONObject();
            jo.put("id",r.getId());
            jo.put("name",r.getName());
            jo.put("code",r.getCode());
            jo.put("description",r.getDescription());
            StringBuffer sb = new StringBuffer("");
            for(Function f:r.getFunctions()){
                sb.append(f.getId()+",");
            }
            if(StringUtil.isEmpty(sb.toString())){
                jo.put("authIds","");
            }else {
                jo.put("authIds",sb.substring(0,sb.length()-1));
            }
            rows.add(jo);
        }
        jsonObject.put("rows",rows);
        jsonObject.put("total",roleDao.roleCount(model));
        return jsonObject;
    }

    @Override
    public int roleDelete(String delIds) {
        return roleDao.delete(delIds);
    }

    @Override
    public void add(Role model, String authIdsOrNames) {
        String[] authIds = authIdsOrNames.split(",");
        model.setId(UUID.randomUUID().toString());
        Set<Function> set = new HashSet<>();

        for(String str:authIds){
            Function function = functionDao.findById(str.trim());
            set.add(function);
        }

        model.setFunctions(set);
        roleDao.add(model);
    }

    @Override
    public void update(Role model, String authIdsOrNames) {
        String[] authIds = authIdsOrNames.split(",");
        Set<Function> set = new HashSet<>();

        for(String str:authIds){
            Function function = functionDao.findById(str.trim());
            set.add(function);
        }

        model.setFunctions(set);
        roleDao.RoleUpdate(model);
    }
}
