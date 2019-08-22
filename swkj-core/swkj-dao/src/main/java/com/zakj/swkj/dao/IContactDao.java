package com.zakj.swkj.dao;

import com.zakj.swkj.bean.Contact;
import com.zakj.swkj.dao.base.IBaseDao;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/13
 * Time: 14:37
 * Description:
 **/
public interface IContactDao extends IBaseDao<Contact>{
    boolean updateContact(Contact model);

    String findContactPageUrl(String id);

    Contact findContact(String id);
}
