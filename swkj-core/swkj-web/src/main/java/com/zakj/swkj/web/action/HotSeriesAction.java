package com.zakj.swkj.web.action;

import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.base.impl.BaseDaoImpl;
import com.zakj.swkj.service.IHotSeriesService;
import com.zakj.swkj.web.action.base.BaseAction;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 16:56
 * Description:
 **/
@Controller
@Scope("prototype")
public class HotSeriesAction extends BaseAction<Series>{
    private final IHotSeriesService service;
    @Autowired
    public HotSeriesAction(IHotSeriesService service) {
        this.service = service;
    }

    public String getHotSeries(){
        JSONArray jsonArray = new JSONArray();
        jsonArray = service.findHotSeries();
        try {
            write(jsonArray.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return NONE;
    }
}
