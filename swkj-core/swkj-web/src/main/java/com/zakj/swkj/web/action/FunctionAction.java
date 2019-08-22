package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Function;
import com.zakj.swkj.service.IFunctionService;
import com.zakj.swkj.web.action.base.BaseAction;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 16:15
 * Description:
 **/
@Controller
@Scope("prototype")
public class FunctionAction extends BaseAction<Function>{
    private final IFunctionService functionService;

    @Autowired
    public FunctionAction(IFunctionService functionService) {
        this.functionService = functionService;
    }

    public String getFunciton(){
        try{
            writeJson(functionService.getFunction().toString());
        }catch (Exception e){
            e.printStackTrace();

        }
        return NONE;
    }


}
