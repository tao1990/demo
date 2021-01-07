
package com.example.demo.spring.springAopJDKDynamic;

public class ProxyTest {
	public static void main(String[] args) {  
        // 我们要代理的真实对象  
		TestServiceImpl test = new TestServiceImpl();  
		test.add();// 不是用代理  
		
		
		
        System.out.println("===================================");  
        JDKDynamicProxy JDKDynamicProxyTarget = new JDKDynamicProxy();  
        TestI testServiceProxy = (TestI) JDKDynamicProxyTarget.newProxy(new TestServiceImpl());  
        // 执行代理类的方法 
        testServiceProxy.add();  
    }  
}
