package com.example.demo.spring.springAopCglib;

public class ProxyTest {
	public static void main(String[] args) {  
        //我们要代理的真实对象  
        TestCGLIBServiceImpl testCGLIB = new TestCGLIBServiceImpl();  
        testCGLIB.add();  
        
        System.out.println("======================================");  
        CGLIBProxy CGLIBproxy = new CGLIBProxy();  
        TestCGLIBServiceImpl testCGLIBProxy = (TestCGLIBServiceImpl) CGLIBproxy.createProxyInstance(new TestCGLIBServiceImpl());  
        testCGLIBProxy.add();
     } 
}
