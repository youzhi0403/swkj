package com.zakj.swkj.service;

import com.zakj.swkj.bean.Contact;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/13
 * Time: 14:36
 * Description:
 **/
public interface IContactService {
    boolean update(Contact model, HttpServletRequest request);

    Contact findContact(String id);
}
