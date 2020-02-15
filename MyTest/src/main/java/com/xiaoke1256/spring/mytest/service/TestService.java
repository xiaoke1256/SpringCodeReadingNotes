package com.xiaoke1256.spring.mytest.service;

import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xiaoke1256.spring.mytest.base.BaseDao;
import com.xiaoke1256.spring.mytest.bo.TestTable;

@Service
@Transactional
public class TestService {
	
	@Autowired
	private BaseDao baseDao;
	
	public void saveTest(TestTable test) {
		test.setId(UUID.randomUUID().toString().replace("-", ""));
		baseDao.save(test);
	}
}
