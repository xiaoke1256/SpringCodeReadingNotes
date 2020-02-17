package com.xiaoke1256.spring.mytest.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoke1256.spring.mytest.base.BaseDao;
import com.xiaoke1256.spring.mytest.bo.TestTable;

public class Test2Servce {
	@Autowired
	private BaseDao baseDao;

	public void saveTest(TestTable test,boolean isRollback) {
		test.setId(UUID.randomUUID().toString().replace("-", ""));
		baseDao.save(test);
		System.out.print("saved");
		if(isRollback)
			throw new RuntimeException("Rollback!");
	}
	
	public int count() {
		return baseDao.count(TestTable.class);
	}
}
