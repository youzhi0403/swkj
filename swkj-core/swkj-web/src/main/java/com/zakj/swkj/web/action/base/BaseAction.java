package com.zakj.swkj.web.action.base;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.utils.PageBean;
import org.apache.struts2.ServletActionContext;
import org.hibernate.criterion.DetachedCriteria;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class BaseAction<T> extends ActionSupport implements ModelDriven<T> {

	protected T model;

	protected PageBean<T> pageBean = new PageBean<>();
	//创建离线提交查询对象
	DetachedCriteria detachedCriteria = null;

	public void setPage(int page) {
		pageBean.setCurrentPage(page);
	}

	public void setRows(int rows) {
		pageBean.setPageSize(rows);
	}

	//在构造方法中动态获取实体类型，通过反射创建model对象
	public BaseAction() {
		ParameterizedType genericSuperclass = (ParameterizedType) this.getClass().getGenericSuperclass();
		//获得BaseAction上声明的泛型数组
		Type[] actualTypeArguments = genericSuperclass.getActualTypeArguments();
		Class<T> entityClass = (Class<T>) actualTypeArguments[0];
		detachedCriteria = DetachedCriteria.forClass(entityClass);
		pageBean.setDetachedCriteria(detachedCriteria);
		//通过反射创建对象
		try {
			model = entityClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	@Override
	public T getModel() {
		return model;
	}

	/**
	 * 获取当前请求的上下文路径(例如："/WebDemo" )
	 *
	 * @return 返回上下文路径
	 */
	public  String contextPath(){
		return ServletActionContext.getRequest().getContextPath();
	}

	/**
	 * 获取当前请求的URI（例如："http://localhost:8080/WebDemo/" ）
	 *
	 * @return 返回当前请求的URI路径
	 */
	public String basePath(){
		return ServletActionContext.getRequest().getScheme()+"://"+ServletActionContext.getRequest().getServerName()+":"
				+ServletActionContext.getRequest().getServerPort()+contextPath()+"/";
	}

	/**
	 * 获取远程主机ip地址（例如："127.0.0.1" ）
	 *
	 * @return 远程主机ip地址
	 */
	public  String remoteAddr(){
		return ServletActionContext.getRequest().getRemoteAddr();
	}

	/**
	 * 获取当前请求的servlet的路径（例如："/welcome.jsp" ）
	 *
	 * @return 当前请求的servlet的路径
	 */
	public  String servletPath(){
		return ServletActionContext.getRequest().getServletPath();
	}

	/**
	 * 当前请求的上下文真实路径（例如："D:/apache-tomcat-6.0.13/webapps/WebDemo/" ）
	 *
	 * @return 请求的上下文真实路径
	 */
	public  String servletContextRealPath(){
		return ServletActionContext.getRequest().getSession().getServletContext().getRealPath("/");
	}
	
	/**
	 * 获取指定虚拟路径的真是路径（例如："D:/apache-tomcat-6.0.13/webapps/WebDemo/WEB-INF/pages/" ）
	 *
	 * @return 虚拟路径的真实路径
	 */
	public  String realPath(String virtualPath){
		return ServletActionContext.getRequest().getSession().getServletContext().getRealPath(virtualPath);
	}

	/**
	 * 获取当前请求的URL（例如："http://localhost:8683/WebDemo/welcome.jsp" ）
	 *
	 * @return 当前请求的URL
	 */
	public  String requestURL(){
		return ServletActionContext.getRequest().getRequestURL().toString();
	}

	/**
	 * 获取当前请求的URI（例如："/WebDemo/welcome.jsp" ）
	 *
	 * @return 当前请求的URI
	 */
	public  String requestURI(){
		return ServletActionContext.getRequest().getRequestURI();
	}

	/**
	 * 获取当前请求的查询条件（例如："username=John" ）
	 *
	 * @return 当前请求的查询条件
	 */
	@Deprecated
	public  String queryString(){
		return ServletActionContext.getRequest().getQueryString();
	}

	/**
	 * 响应字符串数据
	 *
	 * @param data String类型数据
	 * @throws IOException io异常
	 */
	public void write(String data) throws IOException {
		ServletActionContext.getResponse().getWriter().write(data);
	}

	/**
	 * 响应json数据
	 *
	 * @param json json串
	 * @throws IOException io异常
	 */
	public void writeJson(String json) throws IOException {
		ServletActionContext.getResponse().setContentType("application/json; charset=utf-8");
		write(json);
	}


	/**
	 * 获取当前登录的用户User对象
	 *
	 * @return 当前登录过的用户User对象
	 */
	public User getUser(){
		return (User) ServletActionContext.getRequest().getSession().getAttribute(Constant.USER_KEY);
	}
}
