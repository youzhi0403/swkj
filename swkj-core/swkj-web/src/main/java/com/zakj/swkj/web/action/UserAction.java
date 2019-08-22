package com.zakj.swkj.web.action;

import com.opensymphony.xwork2.ActionContext;
import com.zakj.swkj.bean.Constant;
import com.zakj.swkj.bean.PageBean;
import com.zakj.swkj.bean.User;
import com.zakj.swkj.service.IUserService;
import com.zakj.swkj.utils.LoggerUtils;
import com.zakj.swkj.utils.MD5Utils;
import com.zakj.swkj.utils.ResultUtils;
import com.zakj.swkj.utils.StringUtil;
import com.zakj.swkj.web.action.base.BaseAction;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
@Scope("prototype")
public class UserAction extends BaseAction<User> {
    private IUserService service;

    @Autowired
    public UserAction(IUserService service) {
        this.service = service;
    }


    private static final long serialVersionUID = -4967309543451748356L;

    //登录
    public String login() {
        LoggerUtils.info(this,"进来了...");
        System.out.println("123");
        //使用shiro框架提供的方式进行认证操作
        Subject subject = SecurityUtils.getSubject();//获得当前用户对象
        if (subject.isAuthenticated()) {
            //认证过，则直接跳转到首页
            User user = (User) subject.getPrincipal();
            Logger.getLogger(this.getClass()).info(user.getUsername()+"用户已经登录过了！");
            return Constant.HOME;
        }
        //如果传空值，直接跳回去登录页面
        if (StringUtils.isBlank(model.getUsername()) || StringUtils.isBlank(model.getPassword())){
            return Constant.LOGIN;
        }
        //创建认证token
        AuthenticationToken token =
                new UsernamePasswordToken(model.getUsername(), MD5Utils.md5(model.getPassword()));//创建用户名密码令牌对象
        try {
            subject.login(token);
            LoggerUtils.info(this,model.getUsername()+"用户登录了！");
            User user = (User) subject.getPrincipal();
            //将用户信息放到值栈的request域中，方便在jsp页面取值 --> <s:property value="#request.user.username"/>
            ActionContext.getContext().getSession().put(Constant.USER_KEY, user);
            return Constant.HOME;
        } catch (Exception e) {
            LoggerUtils.exception(this, e);
            if (e instanceof UnknownAccountException || e instanceof IncorrectCredentialsException) {
                this.addActionError("用户名或密码输入错误！");
            } else if (e instanceof LockedAccountException){
                this.addActionError("该账号已被查封！");
            } else {
                this.addActionError("其他错误！");
                e.printStackTrace();
            }
        }
        return Constant.LOGIN;
    }

    //退出登录
    public String logout(){
        Subject subject = SecurityUtils.getSubject();//获得当前用户对象
        if (subject.isAuthenticated()) {
            //撤销认证
            User user = (User) subject.getPrincipal();
            subject.logout();
            LoggerUtils.info(this,user.getUsername()+"用户退出！");
        }
        return LOGIN;
    }

    private String page;
    private String rows;
    public void setPage(String page) {
        this.page = page;
    }
    public void setRows(String rows) {
        this.rows = rows;
    }

    public String list(){
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        /*Logger.getLogger(this.getClass()).info();*/
        try {
            Logger.getLogger(this.getClass()).info("model:"+model);
            Logger.getLogger(this.getClass()).info(service);
            writeJson(service.userList(model, pageBean).toString());
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger(this.getClass()).info(e);
        }

        return NONE;
    }

    private String delIds;
    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public String delete(){
        JSONObject result = new JSONObject();
        int delNums = service.UserDelete(delIds);
        if (delNums > 0) {
            result.put("success", "true");
            result.put("delNums", delNums);
        } else {
            result.put("errorMsg", "删除失败");
        }
        try {
            writeJson(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String getRoles(){
        try {
            writeJson(service.getRoles().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }


    private String roleIdsOrNames;
    public void setRoleIdsOrNames(String roleIdsOrNames) {
        this.roleIdsOrNames = roleIdsOrNames;
    }

    public String save(){
//        Logger.getLogger(this.getClass()).info("model:"+model);
//        Logger.getLogger(this.getClass()).info("roleIdsOrNames:"+roleIdsOrNames);
//        Logger.getLogger(this.getClass()).info("length:"+roleIdsOrNames.length());

        if(StringUtil.isEmpty(model.getId())){
            //添加操作
            service.add(model,roleIdsOrNames);
        }else{
            //更新操作
            service.update(model,roleIdsOrNames);
        }

        return NONE;
    }

    public String getAuths(){
        try {
            writeJson(service.getAuths(model.getId()).toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return NONE;
    }

    public String editPassword() throws IOException {

        if(StringUtils.isBlank(model.getPassword())){
            writeJson(ResultUtils.failure("新密码不能为空！"));
        }

        try {
            User user = (User) SecurityUtils.getSubject().getPrincipal();
            user.setPassword(MD5Utils.md5(model.getPassword()));
            service.editPassword(user);
            writeJson(ResultUtils.ok(null));
        } catch (Exception e){
            writeJson(ResultUtils.failure("密码修改失败！"));
        }

        return NONE;
    }

    public static void main(String[] args) {
        System.out.println(MD5Utils.md5("admin"));

    }
}
