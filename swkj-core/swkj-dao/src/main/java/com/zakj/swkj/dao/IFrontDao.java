package com.zakj.swkj.dao;

import com.zakj.swkj.bean.SlideImg;
import com.zakj.swkj.dao.base.IBaseDao;

import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/10/14
 * Time: 10:14
 * Description:
 **/
public interface IFrontDao extends IBaseDao<SlideImg>{
    long getSlideImgCount();

    List<SlideImg> findSlideImgsByIndex();

    List<?> findRecommendProductList();

    List findProductIdAndNameList();

    void updateProductById(String rec_pro_id, String isRec);

    long getRecProCount(String isRec);
}
