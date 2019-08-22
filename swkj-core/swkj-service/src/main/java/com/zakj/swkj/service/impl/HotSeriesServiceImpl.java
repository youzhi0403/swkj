package com.zakj.swkj.service.impl;

import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.IHotProductDao;
import com.zakj.swkj.dao.IHotSeriesDao;
import com.zakj.swkj.service.IHotSeriesService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 17:01
 * Description:
 **/
@Service
@Scope("prototype")
@Transactional
public class HotSeriesServiceImpl implements IHotSeriesService {
    private final IHotSeriesDao hotSeriesDao;
    private final IHotProductDao hotProductDao;
    @Autowired
    public HotSeriesServiceImpl(IHotSeriesDao hotSeriesDao, IHotProductDao hotProductDao) {
        this.hotSeriesDao = hotSeriesDao;
        this.hotProductDao = hotProductDao;
    }

    @Override
    public JSONArray findHotSeries() {
        //先查出所有hotProduct的系列,
        List<Series> list = hotSeriesDao.findSeriesOfHotProduct();
        JSONArray jsonArray = new JSONArray();
        for(Series s:list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",s.getId());
            jsonObject.put("name",s.getS_name());
            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }
}
