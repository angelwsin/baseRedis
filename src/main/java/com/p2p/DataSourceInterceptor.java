package com.p2p;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import com.p2p.annotation.DataSource;

@Aspect
@Component
public class DataSourceInterceptor {
	
	
	@Pointcut("execution(* com.p2p.dao..*.*(..))")
	private void serviceInvoke(){}
	
	
	@Before("serviceInvoke()")
	public void beforeDaoMethod(JoinPoint point)throws Throwable 
	{
		//dao方法上配置的注解
		 MethodSignature methodSignature = (MethodSignature) point.getSignature();
		    Method method = methodSignature.getMethod();
		DataSource datasource = method.getAnnotation(DataSource.class);
		if(datasource==null){
			DatabaseContextHolder.setCustomerType(DatabaseContextHolder.DEFLUATDataSOURCE); 
			return ;
		}
		 DatabaseContextHolder.setCustomerType(datasource.value()); 
	} 
}
