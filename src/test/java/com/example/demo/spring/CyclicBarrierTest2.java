package com.example.demo.spring;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest2 {
	 private static int num = 3;
	    private static CyclicBarrier cyclicBarrier = new CyclicBarrier(num, () -> {
	        System.out.println("-------------------");
	    });
	    private static ExecutorService executorService = Executors.newFixedThreadPool(num);

	    public static void main(String[] args) throws Exception {
	        executorService.submit(() -> {
	            System.out.println("A在上厕所");
	            try {
	                Thread.sleep(4000);
	                System.out.println("A上完了");
	                cyclicBarrier.await();
	                System.out.println("会议结束，A退出，开始撸代码");
	                cyclicBarrier.await();
	                System.out.println("C工作结束，下班回家");
	                cyclicBarrier.await();
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {

	            }
	        });
	        executorService.submit(() -> {
	            System.out.println("B在上厕所");
	            try {
	                Thread.sleep(2000);
	                System.out.println("B上完了");
	                cyclicBarrier.await();
	                System.out.println("会议结束，B退出，开始摸鱼");
	                cyclicBarrier.await();
	                System.out.println("B摸鱼结束，下班回家");
	                cyclicBarrier.await();
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {

	            }
	        });
	        executorService.submit(() -> {
	            System.out.println("C在上厕所");
	            try {
	                Thread.sleep(3000);
	                System.out.println("C上完了");
	                cyclicBarrier.await();
	                System.out.println("会议结束，C退出，开始摸鱼");
	                cyclicBarrier.await();
	                System.out.println("C摸鱼结束，下班回家");
	                cyclicBarrier.await();
	            } catch (Exception e) {
	                e.printStackTrace();
	            } finally {

	            }
	        });

	        executorService.shutdown();

	    }	
}
