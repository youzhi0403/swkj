package com.zakj.swkj.service.impl;

import com.zakj.swkj.dao.IFunctionDao;
import com.zakj.swkj.service.IFunctionService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 16:18
 * Description:
 **/
@Service
@Scope("prototype")
public class FunctionServiceImpl implements IFunctionService {
    private final IFunctionDao functionDao;

    @Autowired
    public FunctionServiceImpl(IFunctionDao functionDao) {
        this.functionDao = functionDao;
    }

    @Override
    public JSONArray getFunction() {
        List<Object[]> list = functionDao.getFunction();
        JSONArray jsonArray = new JSONArray();
        for(Object[] objects:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",objects[0]);
            jsonObject.put("name",objects[1]);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
