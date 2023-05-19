package com.spring.finproj.aop;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.spring.finproj.model.user.UserDAO;

public class UserRefreshAspect {
	@Autowired
	private UserDAO userDAO;
	
	public void before(JoinPoint joinPoint) {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
		System.out.println("before");
	}
	
	public void after() {
		HttpSession session = ((ServletRequestAttributes) (RequestContextHolder.currentRequestAttributes())).getRequest().getSession();
		System.out.println("after");
		
		
	}
	
	public void after_returning() {
		System.out.println("after_returning");
	}
	
	public void after_throwing() {
		System.out.println("after_throwing");
	}
}