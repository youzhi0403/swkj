package com.zakj.swkj.dao.impl;

import com.zakj.swkj.bean.Function;
import com.zakj.swkj.dao.IFunctionDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 16:19
 * Description:
 **/
@Repository
public class FunctionDaoImpl extends BaseDaoImpl<Function> implements IFunctionDao {

    @Override
    public List<Object[]> getFunction() {
        Session session = null;
        session = currentSession();
        Query query = session.createQuery("select id,name from Function");
        List<Object[]> list = query.list();

        return list;
    }

    @Override
    public Function findById(String str) {
        Session session = null;
        session = currentSession();
        return session.get(Function.class,str);

    }
}
