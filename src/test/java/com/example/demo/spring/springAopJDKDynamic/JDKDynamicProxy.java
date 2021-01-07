package com.example.demo.spring.springAopJDKDynamic;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class JDKDynamicProxy implements InvocationHandler {
	 //被代理的目标对象  
    private Object proxyObj; 
    
    public Object newProxy(Object proxyObj){    
        this.proxyObj = proxyObj;  
        //返回一个代理对象    
        return Proxy.newProxyInstance(proxyObj.getClass().getClassLoader(), proxyObj.getClass().getInterfaces(), this);    
    } 
    
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		before();  
        Object object = method.invoke(this.proxyObj, args);  // 通过反射机制调用目标对象的方法  
        after();        
        return object;    
    }  
	
    public void before(){
         System.out.println("开始执行目标对象之前...");  
    }
    
    public void after(){  
        System.out.println("开始执行目标对象之后...");  
    }   
     

}
