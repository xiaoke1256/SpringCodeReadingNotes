package com.xiaoke1256.spring.mytest.base;

import java.io.Serializable;

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

	/**
     * 新增数据到数据库。

     * @param model 包含数据的数据库对象
     */
    public void save(Object model) {
    	getSession().save(model);
    }

}
