package com.zakj.swkj.service;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.dao.base.IBaseDao;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IUserService {
    public JSONObject userList(User user, PageBean pageBean);

    int UserDelete(String delIds);

    JSONArray getRoles();

    void add(User model, String roleIdsOrNames);

    void update(User model, String roleIdsOrNames);

    JSONObject getAuths(String id);

    void editPassword(User user);
}
