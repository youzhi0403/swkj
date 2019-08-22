package com.zakj.swkj.service.realm;

import com.zakj.swkj.bean.User;
import com.zakj.swkj.dao.IUserDao;
import com.zakj.swkj.utils.LoggerUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;


public class CustomRealm extends AuthorizingRealm{

	@Autowired
	private IUserDao userDao;
	
	//认证方法
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		LoggerUtils.info(this,"开始认证用户信息");
		UsernamePasswordToken passwordToken = (UsernamePasswordToken)token;
		//获得页面输入的用户名
		String username = passwordToken.getUsername();
		//根据用户名查询数据库中的密码
		User user = userDao.findUserByUsername(username);
		if (user == null) {
			// 用户名不存在抛出异常
			throw new UnknownAccountException();
		}
		//TODO 给用户表添加一个是否锁定字段
//		if (user.getLocked() == 0) {
//			// 用户被管理员锁定抛出异常
//			throw new LockedAccountException();
//		}
		//简单认证信息对象
		//框架负责比对数据库中的密码和页面输入的密码是否一致
		return new SimpleAuthenticationInfo(user, user.getPassword(), this.getName());
	}

	//授权方法
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		//为用户授权
		info.addStringPermission("staff-list");
		
		//TODO 后期需要修改为根据当前登录用户查询数据库，获取实际对应的权限
		User user1 = (User) SecurityUtils.getSubject().getPrincipal();
		User user2 = (User) principals.getPrimaryPrincipal();
		System.out.println(user1 == user2);//两者获取的user对象都是一样的
		return info;
	}
}
