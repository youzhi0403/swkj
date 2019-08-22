package com.zakj.swkj.dao.base;

import com.zakj.swkj.utils.PageBean;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 持久层通用接口
 * @author Administrator
 *
 * @param <T>
 */
public interface IBaseDao<T> {

	public void save(T t);

	public void update(T t);

	public void delete(T t);

	public void deleteAll(Collection<T> list);

	public T findById(Serializable id);

	public List<T> findAll();

	public void saveOrUpdate(T entity);

	public List<T> findByCriteria(DetachedCriteria detachedCriteria);

	public void pageQuery(PageBean pageBean);

}
