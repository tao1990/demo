package com.example.demo.spring;

public  class Test  {  
  
    public static void main(String[] args) throws InterruptedException {

    	Thread t1 = new Thread(()->{
    		System.out.println("t1");
    	}) ;
    	Thread t2 = new Thread(()->{
    		try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("t2");
    	}) ;
    	Thread t3 = new Thread(()->{
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		System.out.println("t3");
    	}) ;
    	t1.start();
    	t1.join();
    	t2.start();
    	t2.join();
    	t3.start();
    }  
    
}  