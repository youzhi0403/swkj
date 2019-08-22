package com.zakj.swkj.dao;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/10
 * Time: 14:41
 * Description:
 **/
public interface IRoleDao {
    public Role findById(String id);

    List<Object> list(Role r, PageBean pageBean);

    int roleCount(Role model);

    int delete(String delIds);

    void add(Role model);

    void RoleUpdate(Role model);
}
