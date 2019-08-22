package com.zakj.swkj.service;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;
import com.zakj.swkj.bean.User;
import net.sf.json.JSONObject;

import javax.naming.NamingException;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 11:23
 * Description:
 **/
public interface IRoleService {
    JSONObject roleList(Role model, PageBean pageBean);

    int roleDelete(String delIds);

    void add(Role model, String authIdsOrNames);

    void update(Role model, String authIdsOrNames);
}
