package com.zakj.swkj.dao;


import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.dao.base.IBaseDao;

import java.util.List;

public interface IUserDao extends IBaseDao<User>{

    User findUserByUsername(String username);

    List<Object> userList(User user, PageBean pageBean);

    int userCount(User user);

    int delete(String delIds);

    List<Object> getRoles();

    void add(User model);

    void updateUser(User model);

    User getUserById(String id);
}
