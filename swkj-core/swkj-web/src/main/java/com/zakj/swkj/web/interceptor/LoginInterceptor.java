package com.zakj.swkj.web.interceptor;

import com.zakj.swkj.bean.User;
import com.zakj.swkj.bean.Constant;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;
import com.zakj.swkj.utils.CommonUtils;

/**
 * 废弃，用户的登录验证，交给shiro框架管理
 */
@Deprecated
public class LoginInterceptor extends MethodFilterInterceptor {

	private static final long serialVersionUID = -5216258948566570526L;

	@Override
	protected String doIntercept(ActionInvocation invocation) throws Exception {
		ActionProxy proxy = invocation.getProxy();
		String actionName = proxy.getActionName();
		String namespace = proxy.getNamespace();
		String url = namespace + actionName;
		System.out.println(url);
		//从session中获取用户对象
		User user = CommonUtils.getUser();
		if(user == null){
			//没有登录，跳转到登录页面
			return Constant.LOGIN;
		}
		//放行
		return invocation.invoke();
	}
}
