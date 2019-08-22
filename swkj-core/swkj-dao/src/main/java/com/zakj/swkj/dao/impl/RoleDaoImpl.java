package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;
import com.zakj.swkj.dao.IRoleDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.utils.StringUtil;
import org.apache.poi.ss.formula.functions.T;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/10
 * Time: 14:42
 * Description:
 **/
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao {

    @Override
    public Role findById(String id) {
        Session session = null;
        session = currentSession();
        return session.get(Role.class,id);

    }

    @Override
    public List<Object> list(Role r, PageBean pageBean) {
        List<Object> list = null;
        Session session = null;
        session = currentSession();

        StringBuffer sb = new StringBuffer("from Role");
        if (StringUtil.isNotEmpty(r.getName())) {
            sb.append(" where  name like '%" + r.getName() + "%'");
        }
        Query query = session.createQuery(sb.toString());
        if (pageBean != null) {
            query.setFirstResult(pageBean.getStart());
            query.setMaxResults(pageBean.getRows());
        }
        list = query.list();

        return list;

    }

    @Override
    public int roleCount(Role model) {
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("select count(*) from Role");
        if (StringUtil.isNotEmpty(model.getName())) {
            sb.append(" where username like '%" + model.getName() + "%'");
        }

        Query query = session.createQuery(sb.toString());
        Object obj = query.uniqueResult();
        Long lobj = (Long)obj;
        int count = lobj.intValue();
        return count;
    }

    @Override
    public int delete(String delIds) {
        Session session = null;
        session = currentSession();
        StringBuffer sb = new StringBuffer("delete from Role where id in("+delIds+")");
        Query query = session.createQuery(sb.toString());
        return query.executeUpdate();
    }

    @Override
    public void add(Role model) {
        Session session = null;
        session = currentSession();
        session.save(model);
    }

    @Override
    public void RoleUpdate(Role model) {
        Session session = null;
        session = currentSession();
        Role role = session.get(Role.class,model.getId());
        role.setName(model.getName());
        role.setDescription(model.getDescription());
        role.setFunctions(model.getFunctions());
        session.save(role);
    }
}
