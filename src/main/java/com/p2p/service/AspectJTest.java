package com.p2p.service;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;


@Aspect
public class AspectJTest {
	
	@Pointcut("execution(* com.p2p.service..*.*(..))")
	private void serviceInvoke(){}
	@After("serviceInvoke()")
	   public void test(){
		   System.out.println("dkskkdkkksk---");
	   }
}
