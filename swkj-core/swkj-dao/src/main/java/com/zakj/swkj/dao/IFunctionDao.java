package com.zakj.swkj.dao;

import com.zakj.swkj.bean.Function;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: cwj
 * QQ: 815004130
 * Email: 15521384566@163.com
 * Date: 2017/10/11
 * Time: 16:19
 * Description:
 **/
public interface IFunctionDao {
    List<Object[]> getFunction();

    Function findById(String str);
}
