package com.xiaoke1256.spring.mytest;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.xiaoke1256.spring.mytest.controller.TestController;

public class TestApp {
	public static void main(String[] args) {
       ApplicationContext cxt = new ClassPathXmlApplicationContext("applicationContext.xml");
       
       TestController testController = (TestController)cxt.getBean("testController");
       testController.doTest();
    }
}
