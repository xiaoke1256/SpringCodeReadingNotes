package com.xiaoke1256.spring.mytest.controller;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.xiaoke1256.spring.mytest.bo.TestTable;
import com.xiaoke1256.spring.mytest.service.TestService;

@Controller
public class TestController {
	@Autowired
	private TestService testService;
	
	public void doTest() {
		System.out.print("TEstTestTest");
		System.out.println("testService's class:"+testService.getClass());
		TestTable test = new TestTable();
		test.setAa(String.valueOf(new Random().nextInt(100000)));
		test.setBb(String.valueOf(new Random().nextInt(100000)));
		testService.saveTest(test );
		System.out.print("Saved!");
	}
	
	public void doTestWithRollback() {
		System.out.print("TEstTestTest222");
		System.out.println("testService's class:"+testService.getClass());
		TestTable test = new TestTable();
		test.setAa(String.valueOf(new Random().nextInt(100000)));
		test.setBb(String.valueOf(new Random().nextInt(100000)));
		int count = testService.count();
		System.out.println("Count before Save: "+count);
		try {
			testService.saveTest(test ,true);
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		count = testService.count();
		System.out.println("Count After Save: "+count);
		System.out.print("Saved!");
	}

}
