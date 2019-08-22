package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Function;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.Role;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.dao.IRoleDao;
import com.zakj.swkj.dao.IUserDao;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.service.IUserService;
import com.zakj.swkj.utils.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	private final IUserDao dao;
	private final IRoleDao roleDao;

	@Autowired
	public UserServiceImpl(IUserDao dao,IRoleDao roleDao) {
		this.dao = dao;
		this.roleDao = roleDao;
	}

    @Override
    public JSONObject userList(User user, PageBean pageBean) {
		List<Object> list = dao.userList(user,pageBean);
		JSONObject result = new JSONObject();
		JSONArray rows = new JSONArray();
		for(Object object:list){
			StringBuffer sb1 = new StringBuffer("");
			StringBuffer sb2 = new StringBuffer("");
			JSONObject jsonObject = new JSONObject();
			User u = (User)object;
			jsonObject.put("id",u.getId());
			jsonObject.put("username",u.getUsername());
			jsonObject.put("password",u.getPassword());
			jsonObject.put("telephone",u.getTelephone());
			//当user没有role的时候会
			for(Role role:u.getRoles()){
				sb1.append(role.getId()+",");
				sb2.append(role.getName()+",");
			}
			if(StringUtil.isEmpty(sb1.toString())){
				jsonObject.put("roleIds","");
				jsonObject.put("roleNames","");
			}else{
				jsonObject.put("roleIds",sb1.substring(0,sb1.length()-1));
				jsonObject.put("roleNames",sb2.substring(0,sb2.length()-1));
			}

			rows.add(jsonObject);
		}
		result.put("rows",rows);
		result.put("total",dao.userCount(user));

		return result;
    }

    @Override
    public int UserDelete(String delIds) {
		return dao.delete(delIds);
    }

	@Override
	public JSONArray getRoles() {
		List<Object> list = dao.getRoles();
		JSONArray result = new JSONArray();
		for(Object o:list){
			Role r = (Role)o;
			JSONObject jsonObject = new JSONObject();
			jsonObject.put("id",r.getId());
			jsonObject.put("name",r.getName());
			result.add(jsonObject);
		}
		return result;
	}

	@Override
	public void add(User model, String roleIdsOrNames) {
		String[] roleIds = roleIdsOrNames.split(",");
		model.setId(UUID.randomUUID().toString());
		Set<Role> set = new HashSet();

		for(String str:roleIds){
			Role role = roleDao.findById(str.trim());
			set.add(role);
		}

		model.setRoles(set);
		dao.add(model);

	}

	@Override
	public void update(User model, String roleIdsOrNames) {
		String[] roleIds = roleIdsOrNames.split(",");

		Set<Role> set = new HashSet();

		for(String str:roleIds){
			Role role = roleDao.findById(str.trim());
			set.add(role);
		}

		model.setRoles(set);
		dao.updateUser(model);
	}

    @Override
    public JSONObject getAuths(String id) {
		JSONObject jsonObject = new JSONObject();
		User user = dao.getUserById(id);
		for(Role r:user.getRoles()){
			for(Function f:r.getFunctions()){
				jsonObject.put(f.getCode(),true);
			}
		}
        return jsonObject;
    }

    @Override
    public void editPassword(User user) {
        dao.saveOrUpdate(user);
    }


}
