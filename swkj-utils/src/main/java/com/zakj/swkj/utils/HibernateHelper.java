package com.zakj.swkj.utils;

import com.zakj.swkj.bean.Series;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.*;
import org.hibernate.transform.Transformers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * create by IntelliJ IDEA
 * User: HuangRZ
 * QQ: 917647409
 * Email: huangrz11@163.com
 * Date: 2017/9/22
 * Time: 16:29
 * Description:  hibernate框架使用的工具类
 **/
public final class HibernateHelper {

    private DetachedCriteria dc;
    private ProjectionList pList;

    public HibernateHelper() {
    }

    public HibernateHelper(Class clazz) {
        dc = DetachedCriteria.forClass(clazz);
        dc.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
    }

    public HibernateHelper(String entityName) {
        dc = DetachedCriteria.forEntityName(entityName);
        dc.setResultTransformer(DetachedCriteria.ROOT_ENTITY);
    }

    /**
     * 添加指定的查询字段
     *
     * @param properties 指定的查询字段
     * @return
     */
    public HibernateHelper addProperties(String... properties) {
        if (pList == null) {
            pList = Projections.projectionList();
        }
        for (String property : properties) {
            pList.add(Projections.property(property).as(property));
        }
        return this;
    }

    /**
     * 添加查询条件（key = value）
     *
     * @param conditions
     * @param conditionValues
     * @return
     */
    public HibernateHelper addConditions(String[] conditions, String... conditionValues) {
        if (conditions.length != conditionValues.length) {
            throw new RuntimeException("条件数组和对应条件的值的数组不一致！");
        }
        if (pList == null) {
            pList = Projections.projectionList();
        }
        //指定查询条件
        if (conditions.length > 0) {
            for (int i = 0; i < conditionValues.length; i++) {
                dc.add(Restrictions.eq(conditions[i], conditionValues[i]));
            }
        }
        return this;
    }

    /**
     * 模糊查询条件
     *
     * @param likeCondition
     * @param likeConditionValue
     * @return
     */
    public HibernateHelper addLikeConditions(String likeCondition, String likeConditionValue) {
        if (pList == null) {
            pList = Projections.projectionList();
        }
        dc.add(Restrictions.like(likeCondition, "%" + likeConditionValue + "%"));
        return this;
    }

    /**
     * 添加是否为null查询条件
     *
     * @param conditions
     * @return
     */
    public HibernateHelper addIsNullConditions(String... conditions) {
        if (pList == null) {
            pList = Projections.projectionList();
        }
        //指定查询条件
        for (String condition : conditions) {
            dc.add(Restrictions.isNull(condition));
        }
        return this;
    }

    /**
     * 添加是否不为null的查询条件
     *
     * @param conditions
     * @return
     */
    public HibernateHelper addIsNotNullConditions(String... conditions) {
        if (pList == null) {
            pList = Projections.projectionList();
        }
        //指定查询条件
        for (String condition : conditions) {
            dc.add(Restrictions.isNotNull(condition));
        }
        return this;
    }

    /**
     * 添加查询顺序条件
     *
     * @param order
     * @return
     */
    public HibernateHelper addOrder(Order order) {
        dc.addOrder(order);
        return this;
    }

    /**
     * 为指定的属性创建别名，一般是为需要链表查询的子表设置别名。
     * 子表设置表名后，添加查询条件时，可以写成：[别名].[属性名]，sql语句会变成join in子表。
     *
     * @param properties
     * @param propertiesAlias
     * @return
     */
    public HibernateHelper createAlias(String properties, String propertiesAlias) {
        dc.createAlias(properties, propertiesAlias);
        return this;
    }

    /**
     * 获取一个可以查询指定字段的 高效离线查询的DetachedCriteria对象
     *
     * @param clazz      指定查询的表名对应的实体类的class
     * @param properties 指定需要查询的字段名
     * @return 返回一个封装了查询条件的DetachedCriteria实例
     */
    public static DetachedCriteria buildEfficientDetachedCriteria(Class clazz, String... properties) {
        String alias = Series.class.getSimpleName() + "_";
        //不指定查询字段时，默认查询全部
        DetachedCriteria dc = DetachedCriteria.forClass(clazz, alias);
        if (properties == null || properties.length == 0) {
            return dc;
        }
        //Projections是Projection的实例工厂，Projection的实例是各种限定统计动作（求均值，总记录数，某属性数量，某属性不同值数量等等）
        ProjectionList pList = Projections.projectionList();
        for (String property : properties) {
            pList.add(Projections.property(alias + "." + property).as(property));
        }
        dc.setProjection(pList);
        /*设置结果转换器，将实体字段名和查询别名（alias）对应起来，进行赋值操作，那么查询到的数据将会封装到指定class的实体类中。
         否则封装数据时，数据将不会封装到指定class的实体中，而是将其先上转型为一个object，其字段名为1，2,3,4...，查询到的数据不变。*/
        dc.setResultTransformer(Transformers.aliasToBean(clazz));
        return dc;
    }

    public DetachedCriteria getDc() {
        return dc;
    }

    public void setDc(DetachedCriteria dc) {
        this.dc = dc;
    }

    /**
     * 获取一个可以查询指定字段和指定查询条件的高效离线查询的DetachedCriteria对象
     *
     * @param clazz               需要查询的表
     * @param conditionProperties 查询条件的属性
     * @param conditionValue      查询条件的属性对应的值
     * @param queryProperties     需要查询的字段
     * @return 指定字段和指定查询条件的高效离线查询的DetachedCriteria对象
     */
    public static DetachedCriteria buildEfficientDetachedCriteria(Class clazz, String[] conditionProperties, Object[] conditionValue, String[] queryProperties) {
        String alias = Series.class.getSimpleName() + "_";
        DetachedCriteria dc = DetachedCriteria.forClass(clazz, alias);
        //指定查询字段
        if (queryProperties != null && queryProperties.length > 0) {
            //Projections是Projection的实例工厂，Projection的实例是各种限定统计动作（求均值，总记录数，某属性数量，某属性不同值数量等等）
            ProjectionList pList = Projections.projectionList();
            for (String property : queryProperties) {
                pList.add(Projections.property(alias + "." + property).as(property));
            }
            dc.setProjection(pList);
            /*设置结果转换器，将实体字段名和查询别名（alias）对应起来，进行赋值操作，那么查询到的数据将会封装到指定class的实体类中。
             否则封装数据时，数据将不会封装到指定class的实体中，而是将其先上转型为一个object，其字段名为1，2,3,4...，查询到的数据不变。*/
            dc.setResultTransformer(Transformers.aliasToBean(clazz));
        }
        //指定查询条件
        if (conditionProperties != null && conditionProperties.length > 0) {
            for (int i = 0; i < conditionProperties.length; i++) {
                dc.add(Restrictions.eq(conditionProperties[i], conditionValue[i]));
            }
        }
        return dc;
    }

    /**
     * 获取一个可以查询指定字段的 高效查询的Criteria对象
     *
     * @param clazz      指定查询的表名对应的实体类的class
     * @param session    hibernate的session实例
     * @param properties 指定需要查询的字段名
     * @return 返回一个封装了查询条件的Criteria实例
     */
    public static Criteria buildEfficientCriteria(Session session, Class clazz, String... properties) {
        String alias = Series.class.getSimpleName() + "_";
        //不指定查询字段时，默认查询全部
        Criteria dc = session.createCriteria(clazz, alias);
        if (properties == null || properties.length == 0) {
            return dc;
        }
        //Projections是Projection的实例工厂，Projection的实例是各种限定统计动作（求均值，总记录数，某属性数量，某属性不同值数量等等）
        ProjectionList pList = Projections.projectionList();
        for (String property : properties) {
            pList.add(Projections.property(alias + "." + property).as(property));
        }
        dc.setProjection(pList);
        /*设置结果转换器，将实体字段名和查询别名（alias）对应起来，进行赋值操作，那么查询到的数据将会封装到指定class的实体类中。
         否则封装数据时，数据将不会封装到指定class的实体中，而是将其先上转型为一个object，其字段名为1，2,3,4...，查询到的数据不变。*/
        dc.setResultTransformer(Transformers.aliasToBean(clazz));
        return dc;
    }

    /**
     * 获取一个可以查询指定字段和指定查询条件的高效离线查询的DetachedCriteria对象
     *
     * @param session             hibernate的session实例
     * @param clazz               需要查询的表
     * @param conditionProperties 查询条件的属性
     * @param conditionValue      查询条件的属性对应的值
     * @param queryProperties     需要查询的字段
     * @return 指定字段和指定查询条件的高效离线查询的DetachedCriteria对象
     */
    public static Criteria buildEfficientCriteria(Session session, Class clazz, String[] conditionProperties, Object[] conditionValue, String... queryProperties) {
        String alias = Series.class.getSimpleName() + "_";
        //不指定查询字段时，默认查询全部
        Criteria c = session.createCriteria(clazz, alias);
        //指定查询字段
        if (queryProperties != null && queryProperties.length > 0) {
            //Projections是Projection的实例工厂，Projection的实例是各种限定统计动作（求均值，总记录数，某属性数量，某属性不同值数量等等）
            ProjectionList pList = Projections.projectionList();
            for (String property : queryProperties) {
                pList.add(Projections.property(alias + "." + property).as(property));
            }
            c.setProjection(pList);
            /*设置结果转换器，将实体字段名和查询别名（alias）对应起来，进行赋值操作，那么查询到的数据将会封装到指定class的实体类中。
             否则封装数据时，数据将不会封装到指定class的实体中，而是将其先上转型为一个object，其字段名为1，2,3,4...，查询到的数据不变。*/
            c.setResultTransformer(Transformers.aliasToBean(clazz));
        }
        //指定查询条件
        if (conditionProperties != null && conditionProperties.length > 0) {
            for (int i = 0; i < conditionProperties.length; i++) {
                c.add(Restrictions.eq(conditionProperties[i], conditionValue[i]));
            }
        }
        return c;
    }

    /**
     * 获取多表连接，包括条件查询和查询指定的字段的Criteria
     *
     * @param session hibernate的session实例
     * @param tables  包含需要查询的表名（或者子表的字段名），条件字段名，条件字段的值，需要查询的字段名
     * @return 多表连接，包括条件查询和查询指定的字段的Criteria
     */
    public static Criteria buildEfficientCriteria(Session session, Object[]... tables) {
        //判断有几张表需要联查
        Class tableClass = null;
        String[] conditionProperties;
        Object[] conditionValue;
        String[] queryProperties;
        Criteria criteria = null;
        String alias;
        String subCriteria = null;
        ProjectionList pList = null;
        for (Object[] table : tables) {
            if (table == tables[0]) {
                tableClass = (Class) table[0];
                alias = tableClass.getSimpleName() + "_";
            } else {
                subCriteria = (String) table[0];
                alias = subCriteria + "_";
            }
            conditionProperties = (String[]) table[1];
            conditionValue = (Object[]) table[2];
            queryProperties = (String[]) table[3];

            if (criteria == null) {
                criteria = session.createCriteria(tableClass, alias);
            } else {
                criteria = criteria.createCriteria(subCriteria, alias);
            }

            //指定查询字段
            if (queryProperties != null && queryProperties.length > 0) {
                //Projections是Projection的实例工厂，Projection的实例是各种限定统计动作（求均值，总记录数，某属性数量，某属性不同值数量等等）
                if (pList == null) {
                    pList = Projections.projectionList();
                }
                for (String property : queryProperties) {
                    pList.add(Projections.property(alias + "." + property).as(property));
                }
                criteria.setProjection(pList);
                if (table == tables[0] && tableClass != null) {
                    criteria.setResultTransformer(Transformers.aliasToBean(tableClass));
                }
            }
            //指定查询条件
            if (conditionProperties != null && conditionProperties.length > 0) {
                for (int i = 0; i < conditionProperties.length; i++) {
                    criteria.add(Restrictions.eq(conditionProperties[i], conditionValue[i]));
                }
            }
        }

        //TODO 实现直接执行Criteria，获取数据后直接封装到指定的是实体中返回
        return criteria;
    }


    /**
     * 根据列表内的数组，给数组的每一个元素指定一个field，封装成一个map，然后放到列表中返回。
     * 一般用于将hibernate查询指定字段时返回的列表数组转换成一个有键值对的map list，然后通过工具转换成json数据。
     *
     * @param list   数组列表
     * @param fields 数组中每个元素对应的键
     * @return 带有键值对的列表
     * @throws Exception 当列表中的数组长度和fields的长度不一致时，抛出该异常。
     */
    public static List<HashMap<String, String>> toMapList(List<String[]> list, String... fields) throws Exception {
        HashMap<String, String> map;
        ArrayList<HashMap<String, String>> mapList = new ArrayList<>();
        for (String[] strings : list) {
            if (strings.length != fields.length) {
                throw new Exception("List内的数组长度必须跟字段数组的长度相等！");
            }
            map = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                map.put(fields[i], strings[i]);
            }
            mapList.add(map);
        }
        return mapList;
    }
}
