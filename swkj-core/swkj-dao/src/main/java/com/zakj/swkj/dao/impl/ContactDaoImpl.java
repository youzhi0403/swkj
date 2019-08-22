package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.Contact;
import com.zakj.swkj.dao.IContactDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.utils.HibernateHelper;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/13
 * Time: 14:37
 * Description:
 **/
@Repository
@Scope("prototype")
public class ContactDaoImpl extends BaseDaoImpl<Contact> implements IContactDao {

    @Override
    public boolean updateContact(Contact model) {
        Session session = null;
        session = currentSession();
        Contact contact = session.get(Contact.class,model.getId());

        contact.setCN_agency(model.getCN_agency());
        contact.setCN_email(model.getUSA_email());
        contact.setCN_office(model.getCN_office());
        contact.setFactory_address(model.getFactory_address());
        contact.setPhone(model.getPhone());
        contact.setPicture_url(model.getPicture_url());
        contact.setUSA_email(model.getUSA_email());
        contact.setUSA_office(model.getUSA_office());
        contact.setWebsite_address(model.getWebsite_address());

        session.saveOrUpdate(contact);
        return true;
    }

    @Override
    public String findContactPageUrl(String id) {
        String hql = "select c.page_url from Contact c where c.id = :id";
        return (String) getHibernateTemplate().findByNamedParam(hql, "id",id).get(0);
    }

    @Override
    public Contact findContact(String id) {
        return getHibernateTemplate().get(Contact.class, id);
    }
}
