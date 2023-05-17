package com.spring.finproj.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;

public class UserRefreshAspect {
	public void before(JoinPoint joinPoint) {
		HttpServletRequest request=null;
		HttpServletResponse response=null;
		
		for(Object obj:joinPoint.getArgs()) {
			if(obj instanceof HttpServletResponse) {
				response=(HttpServletResponse) obj;
			}
			if(obj instanceof HttpServletRequest) {
				request=(HttpServletRequest) obj;
			}
		}
		
		HttpSession s = request.getSession();
		System.out.println(s.getAttributeNames());
		System.out.println("before");
	}
	
	public void after() {
		System.out.println("after");
	}
	
	public void after_returning() {
		System.out.println("after_returning");
	}
	
	public void after_throwing() {
		System.out.println("after_throwing");
	}
}