package com.zakj.swkj.dao;

import com.zakj.swkj.bean.Series;
import com.zakj.swkj.dao.base.IBaseDao;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/16
 * Time: 17:02
 * Description:
 **/
public interface IHotSeriesDao extends IBaseDao<Series> {
    List<Series> findSeriesOfHotProduct();

    List<Series> findSeriesOfHotProductByIds(String ids);
}
