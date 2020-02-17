package com.xiaoke1256.spring.mytest.base;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class BaseDao {
	@Autowired
	private SessionFactory sessionFactory;
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	/**
     * 根据对象的Class类型以及主键获取记录
     * @param clazz 对象的Class
     * @param id 记录主键
     * @return 查询的对象

     */
    public Object get(Class<?> clazz, Serializable id) {
    	return getSession().get(clazz, id);
	}
    
    public int count(Class<?> clazz) {
    	Query q = getSession().createQuery("select count(*) from "+clazz.getSimpleName());
    	List<?> list = q.list();
    	if(list!=null && list.size()>0) {
    		return ((Number)list.get(0)).intValue();
    	}
    	return 0;
    }

	/**
     * 新增数据到数据库。

     * @param model 包含数据的数据库对象
     */
    public void save(Object model) {
    	getSession().save(model);
    }

}
