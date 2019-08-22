package com.zakj.swkj.dao.impl;

import java.util.List;

import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.dao.IUserDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.utils.StringUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;


@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao {


	@Override
	public User findUserByUsername(String username) {
		/** TODO 笔记： hibernate建议通过“命名参数”或者“JPA占位符”的方式设置占位符， JPA占位符：“？1”（？+数字），
		 然后通过“serParameter(1, [具体参数值])”的方式给“JPA占位符”设置值。*/
		String hql = "FROM User u WHERE u.username = :name";
		List<User> users = (List<User>) getHibernateTemplate().findByNamedParam(hql, "name", username);
		if(users != null && users.size() > 0){
			return users.get(0);
		}
		return null;
	}

	@Override
	public List<Object> userList(User user, PageBean pageBean) {
		List<Object> list = null;
		Session session = null;
		session = currentSession();

		StringBuffer sb = new StringBuffer("from User");
		if (StringUtil.isNotEmpty(user.getUsername())) {
			sb.append(" where  username like '%" + user.getUsername() + "%'");
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
    public int userCount(User user) {
		Session session = null;
		session = currentSession();
		StringBuffer sb = new StringBuffer("select count(*) from User");
		if (StringUtil.isNotEmpty(user.getUsername())) {
			sb.append(" where username like '%" + user.getUsername() + "%'");
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
		StringBuffer sb = new StringBuffer("delete from User where id in("+delIds+")");
		Query query = session.createQuery(sb.toString());
		return query.executeUpdate();
    }

	@Override
	public List<Object> getRoles() {
		List<Object> list = null;
		Session session = null;
		session = currentSession();
		Query query = session.createQuery("from Role");
		list = query.list();
		return list;
	}

	@Override
	public void add(User model) {
		Session session = null;
		session = currentSession();
		session.save(model);
	}

	@Override
	public void updateUser(User model) {
		Session session = null;
		session = currentSession();
		User u = session.get(User.class,model.getId());
		u.setUsername(model.getUsername());
		u.setPassword(model.getPassword());
		u.setTelephone(model.getTelephone());
		u.setRoles(model.getRoles());
		session.save(u);

	}

	@Override
	public User getUserById(String id) {
		Session session = null;
		session = currentSession();
		return session.get(User.class,id);
	}
}
